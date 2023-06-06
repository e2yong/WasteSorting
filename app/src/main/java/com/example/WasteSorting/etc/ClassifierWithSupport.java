// 모델 관련 작업
// 모델 파일을 로드하고 이미지를 입력하면 추론하여 결과 값을 해석
package com.example.WasteSorting.etc;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.util.Pair;

import androidx.annotation.NonNull;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.SupportPreconditions;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

public class ClassifierWithSupport {
    private static final String MODEL_NAME = "mobilenet_imagenet_model.tflite";
    private static final String LABEL_FILE = "labels.txt";

    Context context;
    Interpreter interpreter;
    int modelInputWidth, modelInputHeight, modelInputChannel;
    TensorImage inputImage;
    TensorBuffer outputBuffer;
    private List<String> labels;    // labels.txt 파일을 읽어 저장

    // 생성자
    public ClassifierWithSupport(Context context) {
        this.context = context;
    }

    // tflite 파일 로드
    // tflite 파일을 입력받아 xx 클래스로 모델을 반환
    @NonNull
    public static MappedByteBuffer loadMappedFile(
            @NonNull Context context, @NonNull String filePath) throws IOException {
        SupportPreconditions.checkNotNull(context, "Context shoud not be null.");
        SupportPreconditions.checkNotNull(filePath, "File path cannot be null.");
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(filePath);

        MappedByteBuffer var9;
        try {
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

            try {
                FileChannel fileChannel = inputStream.getChannel();
                long startOffset = fileDescriptor.getStartOffset();
                long declaredLength = fileDescriptor.getDeclaredLength();
                var9 = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
            } catch (Throwable var12) {
                try {
                    inputStream.close();
                } catch (Throwable var11) {
                    var12.addSuppressed(var11);
                }

                throw var12;
            }

            inputStream.close();
        } catch (Throwable var13) {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (Throwable var10) {
                    var13.addSuppressed(var10);
                }
            }

            throw  var13;
        }

        if (fileDescriptor != null) {
            fileDescriptor.close();
        }

        return var9;
    }

    // loadImage를 위해 Bitmap을 ARGB_8888로 변환
    private Bitmap convertBitmapToARGB8888(Bitmap bitmap) {
        return bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
    // bitmap에 이미지 입력 및 이미지 전처리
    private TensorImage loadImage(final Bitmap bitmap) {
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            inputImage.load(convertBitmapToARGB8888(bitmap));
        } else {
            inputImage.load(bitmap);
        }

        // ResizeOp - 이미지 크기 변경하는 연산
        // NormalizeOp - 이미지를 정규화하는 연산
        ImageProcessor imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeOp(modelInputWidth, modelInputHeight, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(new NormalizeOp(0.0f, 255.0f))
                .build();

        return imageProcessor.process(inputImage);
    }

    // 입력 이미지 전처리
    private void initModelShape() {
        Tensor inputTensor = interpreter.getInputTensor(0);
        int[] shape = inputTensor.shape();
        modelInputChannel = shape[0];
        modelInputWidth = shape[1];
        modelInputHeight = shape[2];

        inputImage = new TensorImage(inputTensor.dataType());

        // TensorBuffer 생성
        Tensor outputTensor = interpreter.getOutputTensor(0);
        outputBuffer = TensorBuffer
                .createFixedSize(outputTensor.shape(), outputTensor.dataType());
    }

    // 인터프리터 - 데이터를 입력하면 추론 결과 반환
    public void init() throws IOException {
        ByteBuffer model = FileUtil.loadMappedFile(context, MODEL_NAME);
        model.order(ByteOrder.nativeOrder());
        interpreter = new Interpreter(model);

        initModelShape();
        // label 파일 로드
        labels = FileUtil.loadLabels(context, LABEL_FILE);
        // labels.txt 파일 첫번째 줄이 background면
        // labels.remove(0);
    }

    // 모델 출력과 label 매핑
    public Pair<String, Float> classify(Bitmap image) {
        inputImage = loadImage(image);
        interpreter.run(inputImage.getBuffer(), outputBuffer.getBuffer().rewind());

        Map<String, Float> output = new TensorLabel(labels, outputBuffer)
                .getMapWithFloatValue();

        return argmax(output);
    }

    // Map에서 속할 확률이 가장 높은 클래스명과 확률 쌍을 반환
    private Pair<String, Float> argmax(Map<String, Float> map) {
        String maxKey = "";
        float maxVal = -1;

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            float f = entry.getValue();
            if (f > maxVal) {
                maxKey = entry.getKey();
                maxVal = f;
            }
        }

        return new Pair<>(maxKey, maxVal);
    }
}


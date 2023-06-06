// TensorFlow Lite 서포트 라이브러리의 Model 클래스를 이용한 Classifier

package com.example.WasteSorting;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Pair;

import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.model.Model;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassifierWithModel {

    private static final String MODEL_NAME = "mobilenet_imagenet_model.tflite";
    private static final String LABEL_FILE = "labels.txt";

    Context context;
    Model model;
    int modelInputWidth, modelInputHeight, modelInputChannel;
    TensorImage inputImage;
    TensorBuffer outputBuffer;
    private List<String> labels;    // labels.txt 파일을 읽어 저장

    // 생성자
    public ClassifierWithModel(Context context) {
        this.context = context;
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

    // 입력 이미지 전처리, 입출력 텐서 얻기
    private void initModelShape() {
        Tensor inputTensor = model.getInputTensor(0);
        int[] shape = inputTensor.shape();
        modelInputChannel = shape[0];
        modelInputWidth = shape[1];
        modelInputHeight = shape[2];

        inputImage = new TensorImage(inputTensor.dataType());

        // TensorBuffer 생성
        Tensor outputTensor = model.getOutputTensor(0);
        outputBuffer = TensorBuffer
                .createFixedSize(outputTensor.shape(), outputTensor.dataType());
    }

    // 인터프리터 - 데이터를 입력하면 추론 결과 반환
    // Model 클래스는 Interpreter가 하던 추론 관련 동작을 내부에서 처리
    public void init() throws IOException {
        model = Model.createModel(context, MODEL_NAME);

        initModelShape();
        // label 파일 로드
        labels = FileUtil.loadLabels(context, LABEL_FILE);
        // labels.remove(0);    // labels.txt 파일 첫번째 줄이 background면
    }

    // 모델 출력과 label 매핑
    public Pair<String, Float> classify(Bitmap image) {
        inputImage = loadImage(image);

        Object[] inputs = new Object[] { inputImage.getBuffer() };
        Map<Integer, Object> outputs = new HashMap();
        outputs.put(0, outputBuffer.getBuffer().rewind());

        model.run(inputs, outputs);

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

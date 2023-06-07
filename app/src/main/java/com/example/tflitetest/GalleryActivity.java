// 갤러리에서 이미지를 선택
package com.example.tflitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "[IC]GalleryActivity";

    public static final int GALLERY_IMAGE_REQUEST_CODE = 1;

    private ClassifierWithModel cls;
    private ImageView imageView;
    private Button selectBtn, nextBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        selectBtn = (Button) findViewById(R.id.selectBtn);
        selectBtn.setOnClickListener(v -> getImageFromGallery());

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);

        // Classifier 초기화
        cls = new ClassifierWithModel(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        nextBtn.setOnClickListener(view -> {
            // 이미지를 SortActivity로 전달
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            float scale = (float) (1024/(float)bitmap.getWidth());
            int image_w = (int) (bitmap.getWidth() * scale);
            int image_h = (int) (bitmap.getHeight() * scale);
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
            resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // 대분류를 SortActivity로 전달
            String large = textView.getText().toString();
            large = large.replaceAll("class: ", "");

            Intent i = new Intent(GalleryActivity.this, SortActivity.class);
            i.putExtra("Large", large);  // 대분류 전달
            i.putExtra("Image", byteArray);     // 이미지 전달
            startActivity(i);
        });
    }

    // 이미지 선택 - 둘이 세트
    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");

        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            if (data == null) {
                return;
            }

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src =
                            ImageDecoder.createSource(getContentResolver(), selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(src);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }

            // 결과 출력
            if (bitmap != null) {
                Pair<String, Float> output = cls.classify(bitmap);
                String resultStr = String.format(Locale.ENGLISH,
                        "class: %s", output.first);

                imageView.setImageBitmap(bitmap);
                textView.setText(resultStr);
                // next 버튼 나타나게
                nextBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
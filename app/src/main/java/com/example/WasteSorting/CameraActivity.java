package com.example.WasteSorting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    public static final String TAG = "[IC]CameraActivity";
    public static final int CAMERA_IMAGE_REQUEST_CODE = 1;
    private static final String KEY_SELECTED_URI = "KEY_SELECTED_URI";

    private ClassifierWithModel cls;
    private ImageView imageView;
    private Button takeBtn, nextBtn;
    private TextView textView;

    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        takeBtn = (Button) findViewById(R.id.takeBtn);
        takeBtn.setOnClickListener(v -> getImageFromCamera());

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        nextBtn = (Button) findViewById(R.id.nextBtn);

        // Classifier 초기화
        cls = new ClassifierWithModel(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        nextBtn.setOnClickListener(view -> {
            String large = textView.getText().toString();
            large = large.replaceAll("class: ", "");

            Intent i = new Intent(CameraActivity.this, SortActivity.class);
            i.putExtra("Large", large);  // 대분류 전달
            startActivity(i);
        });

        // 카메라 앱 실행 중 메모리 부족으로 CameraActivity가 종료될 경우
        if (savedInstanceState != null) {
            Uri uri = savedInstanceState.getParcelable(KEY_SELECTED_URI);
            if (uri != null)
                selectedImageUri = uri;
        }
    }

    // 카메라 앱 실행
    private void getImageFromCamera() {
        File file = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES), "picture.jpg");
        if (file.exists())
            file.delete();
        selectedImageUri = FileProvider.getUriForFile(this, getPackageName(), file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        startActivityForResult(intent, CAMERA_IMAGE_REQUEST_CODE);
    }

    // 액티비티가 종료될 때 호출
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState((outState));

        outState.putParcelable(KEY_SELECTED_URI, selectedImageUri);
    }

    // 결과
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_IMAGE_REQUEST_CODE) {
            Bitmap bitmap = null;

            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src = ImageDecoder
                            .createSource(getContentResolver(), selectedImageUri);
                    bitmap = ImageDecoder.decodeBitmap(src);
                } else {
                    bitmap = MediaStore
                            .Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }

            if (bitmap != null) {
                Pair<String, Float> output = cls.classify(bitmap);
                String resultStr = String.format(Locale.ENGLISH,
                        "class: %s", output.first);

                imageView.setImageBitmap(bitmap);
                textView.setText(resultStr);
            }
        }
    }
}
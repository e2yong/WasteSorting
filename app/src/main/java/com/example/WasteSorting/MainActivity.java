package com.example.WasteSorting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView treeImg;
    private Button galleryBtn, cameraBtn;
    private TextView pointText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        treeImg = (ImageView) findViewById(R.id.treeImg);
        galleryBtn = (Button) findViewById(R.id.galleryBtn);
        cameraBtn = (Button) findViewById(R.id.cameraBtn);
        pointText = (TextView) findViewById(R.id.pointText);

        galleryBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(i);
        });

        cameraBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(i);
        });
    }
}
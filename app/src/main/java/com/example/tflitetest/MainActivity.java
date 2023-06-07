package com.example.tflitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView treeImg;
    private Button galleryBtn, cameraBtn;
    private TextView pointText;

    // 포인트 관련
    SharedPreferences pref;
    private int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        treeImg = (ImageView) findViewById(R.id.treeImg);
        galleryBtn = (Button) findViewById(R.id.galleryBtn);
        cameraBtn = (Button) findViewById(R.id.cameraBtn);
        pointText = (TextView) findViewById(R.id.pointText);

        // 포인트 불러오기
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        point = pref.getInt("Point", 0);
        pointText.setText("point: " + String.valueOf(point));

        // 이미지 변경
        if(point<=20){
            treeImg.setImageResource(R.drawable.tree1_);
        }
        else if(point <= 100){
            //나무 이미지가 지금 제일 애매해서 좀 더 찾아보고 진행할 예정
            treeImg.setImageResource(R.drawable.image);
        }
        else if(point <= 200){

        }
        else if (point <= 300){

        }
        else{
            treeImg.setImageResource(R.drawable.image);
        }
        // 갤러리에서 이미지 선택
        galleryBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(i);
        });

        // 카메라로 이미지 촬영
        cameraBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(i);
        });
    }
}
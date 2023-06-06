package com.example.tflitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Edu2Activity extends AppCompatActivity {
    private ImageView edu2Img;
    private TextView smallText, edu2Text;
    private Button ttsBtn, endBtn;

    private String small, how;
    public TextToSpeech tts;    // TTS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu2);

        edu2Img = (ImageView) findViewById(R.id.edu2Img);
        smallText = (TextView) findViewById(R.id.smallText);
        edu2Text = (TextView) findViewById(R.id.edu2text);
        ttsBtn = (Button) findViewById(R.id.ttsBtn);
        endBtn = (Button) findViewById(R.id.endBtn);

        // 소분류 가져오기
        Intent intent = getIntent();
        small = intent.getStringExtra("Small");  // 데이터 받기
        smallText.setText(small + "의 재활용과정");

        // edu1Img 이미지 변경

        // edu1Text 분리배출방법 변경
        how = "재활용과정은 이렇습니다.";
        edu2Text.setText(how);

        // TTS 초기화
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit ( int status){
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREAN);
                }
                // 경고메시지 추가?
            }
        });

        // TTS 실행
        ttsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(edu2Text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // 다음 화면
        endBtn.setOnClickListener(view -> {
            Intent i = new Intent(Edu2Activity.this, MainActivity.class);
            startActivity(i);
        });

    }
}
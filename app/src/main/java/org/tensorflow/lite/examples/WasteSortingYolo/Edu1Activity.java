package org.tensorflow.lite.examples.WasteSortingYolo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Edu1Activity extends AppCompatActivity {

    private ImageView edu1Img;
    private TextView smallText, edu1Text;
    private Button ttsBtn, nextBtn;

    private String small, how;
    public TextToSpeech tts;    // TTS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu1);

        edu1Img = (ImageView) findViewById(R.id.edu1Img);
        smallText = (TextView) findViewById(R.id.smallText);
        edu1Text = (TextView) findViewById(R.id.edu1text);
        ttsBtn = (Button) findViewById(R.id.ttsBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);

        // 소분류 가져오기
        Intent intent = getIntent();
        small = intent.getStringExtra("Small");  // 데이터 받기
        smallText.setText(small + "의 분리배출방법");

        // edu1Img 이미지 변경

        // edu1Text 분리배출방법 변경
        how = "분리배출방법은 이렇습니다.";
        edu1Text.setText(how);

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
                tts.speak(edu1Text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // 다음 화면
        nextBtn.setOnClickListener(view -> {
            Intent i = new Intent(Edu1Activity.this, Edu2Activity.class);
            i.putExtra("Small", small);  // 소분류 전달
            startActivity(i);
        });

    }
}
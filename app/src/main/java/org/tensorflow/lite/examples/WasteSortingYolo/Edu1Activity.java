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
        // edu1Text 분리배출방법 변경 두작업을 case구문 안에서 동시에 진행하였음
        switch(small){
            case "보증금병":
                //보증긍병의 경우
                edu1Img.setImageResource(R.drawable.depositbottle);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용몰을 비우고, 색상별로 분류합니다\n.재사용 표시가 있는 경우 환불이 가능합니다\n";
                edu1Text.setText(how);
                break;
            case "불연성쓰레기":
                //불연성 쓰레기의 경우
                edu1Img.setImageResource(R.drawable.ncwaste);
                smallText.setText(small+"기의 분리배출방법\n");
                how = "규격 봉투에 넣어서 버리며, 해당하는 쓰레기의 종류는 유리,도자기,화분등 타지 않는 쓰레기입니다\n";
                edu1Text.setText(how);
                break;
            case "잡병":
                //유리-잡병일 경우
                edu1Img.setImageResource(R.drawable.glassbottle);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용몰을 비우고, 물로헹구는 등 이물질을 제거하여 배출합니다\n.깨지지 않게 주의 하여 배출합니다\n";
                edu1Text.setText(how);
                break;
            case "투명페트병":
                //투명페트병의 경우
                edu1Img.setImageResource(R.drawable.petbottle);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용몰을 비우고, 물로헹구는 등 이물질을 제거하여 배출합니다\n.라벨도 추가적으로 제거합니다\n";
                how += "찌그러 뜨린 후에 뚜껑을 닫고 투명페트병 전용수거함에 배출합니다";
                edu1Text.setText(how);
                break;
            case "일반플라스틱":
                edu1Img.setImageResource(R.drawable.generalplastic);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용몰을 비우고, 물로헹구는 등 이물질을 제거하여 배출한다\n.라벨도 추가적으로 제거합니다\n";
                how += "찌그러 뜨린 후에 뚜껑을 닫고 일반플라스틱 수거함에 배출합니다";
                edu1Text.setText(how);
                break;
            case "골판지상자":
                edu1Img.setImageResource(R.drawable.paperbox);
                smallText.setText(small+"의 분리배출방법\n");
                how = "택배송장,테이프등을 깨끗이 제거합니다\n.이물질이 묻지 않게하며 구겨지지 않게 배출합니다\n";
                how += "부피를 줄이기 위해 접어서 배출하는 것이 좋습니다";
                edu1Text.setText(how);
                break;
            case "스티로폼상자":
                edu1Img.setImageResource(R.drawable.styrofoam);
                smallText.setText(small+"의 분리배출방법\n");
                how = "상자안의 내용물을 모두 비우고, 이물질이 묻어있는 경우 물로 세척 후에 분리배출 합니다";
                how += "붙어있는 택배송장이나 스티커는 완벽하게 제거 후에 배출합니다\n";
                how += "여러 개의 스티로폼 상자를 버리는 경우에는 날라가지 않게 큰 비닐에 담아서 배출합니다";
                edu1Text.setText(how);
                break;

            case "일반쓰레기":
                edu1Img.setImageResource(R.drawable.generalwaste);
                smallText.setText(small+"의 분리배출방법\n");
                how = "일반 쓰레기의 경우에는 종량제봉투에 넣어서 쓰레기를 배출합니다";
                edu1Text.setText(how);
                break;
            case "금속캔":
                edu1Img.setImageResource(R.drawable.can);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용물을 비운 뒤,물로 깨끗이 헹굽니다\n";
                how += "라벨이나 스티커는 제거한 후 플라스틱 뚜껑과 같은 다른 재질이 분리해서 배출합니다\n";
                how += "부피를 최대한 줄여서 배출합니다";
                edu1Text.setText(how);
                break;
            case "기타캔":
                edu1Img.setImageResource(R.drawable.othercan);
                smallText.setText(small+"의 분리배출방법\n");
                how = "내용물을 제거한 후에, 배출합니다.\n";
                how += "뚜껑 등의 다른 재질은 분리해서 배출하며, 가스제거기를 사용하여 배출합니다.\n";
                how += "가스 배출시에 통풍이 잘되는 곳이나 실외에서 배출을 진행합니다";
                edu1Text.setText(how);
                break;
            case "비닐봉투":
                edu1Img.setImageResource(R.drawable.vinyl);
                smallText.setText(small+"의 분리배출방법\n");
                how = "이물질을 물로 세척한 후 배출합니다\n";
                how += "지퍼백의 경우에는 지퍼부분을 따로 잘라내지 않고 배출하며,슬라이딩 지퍼백의 닫개는\n";
                how += "떼어내어 일반쓰레기로 배출합니다";
                edu1Text.setText(how);
                break;
            case "비닐포장재":
                edu1Img.setImageResource(R.drawable.vinyl_2);
                smallText.setText(small+"의 분리배출방법\n");
                how = "비닐 안쪽의 기름기와 이물질을 물로 세척한 후 분리배출을 진행합니다\n";
                how += "라벨이나 스티커는 제거하여 배출하며, 빵봊지,과자봉지등은 딱지 모양으로 접지 않고 배출합니다\n";
                edu1Text.setText(how);
                break;

        }
        how = null;

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
            edu1Img.setImageResource(0);
            Intent i = new Intent(Edu1Activity.this, Edu2Activity.class);
            i.putExtra("Small", small);  // 소분류 전달
            startActivity(i);
        });

    }
}
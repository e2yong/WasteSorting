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
        switch(small){
            case "보증금병":
                //보증금병의 경우
                edu2Img.setImageResource(R.drawable.deposit_recycle);
                smallText.setText(small+"의 재활용과정");
                how = "빈병을 회수 후에 세척을 진행하며 다시 사용됩니다\n 무서진 경우에는 파쇄하여 용해로를 통해 제제조 후에 사용됩니다";
                edu2Text.setText(how);
                break;

            case "불연성쓰레기":
                edu2Img.setImageResource(R.drawable.ncwaste);
                smallText.setText("불연성 쓰레기의 경우");
                how = "일반쓰레기이기 떄문에 재활용 과정은 없습니다 \n";
                edu2Text.setText(how);
                break;

            case "음료수병":
                //유리-잡병의 경우
                edu2Img.setImageResource(R.drawable.glass_recycling);
                smallText.setText(small+"의 재활용과정");
                how = "이물질 선별 과정을 거친 후에 색깔별로 나뉘어 파쇄 가공 작업이 진행됩니다.\n";
                how += "앞의 과정을 통하여 만들어진 재생원료는 다시 유리공정을 통해 다양한 유리 제품으로 재탄생하게된다.\n";
                how += "내열유리는 재활용 과정시에 온도가 너무 높아 제대로 재활용 되지 않는다";
                edu2Text.setText(how);
                break;

            case "투명페트병":
                //투명 페트병의 경우
                edu2Img.setImageResource(R.drawable.petbottle_recycle);
                smallText.setText(small+"의 재활용과정");
                how = "페트병 수거함에서 수거한 이후 재활용 업체가 회수 및 선별을 진행합니다.\n";
                how += "선별된 페트병은 압축후에 파쇄하여 원료처럼 만들어 진행됩니다.\n";
                how += "정제를 통하여 플라스틱 칩을 만들어 새로운 제품으로 탈바꿈하게 됩니다.";
                edu2Text.setText(how);
                break;
            case "플라스틱용기":
                //일반플라스틱의 경우
                //일반 플라스틱은 분리배출은 진행하나 재활용은 하지 않아서 과정에 대해서만 설명
                //그림을 바꾸지 않았음
                edu2Img.setImageResource(R.drawable.generalplastic);
                smallText.setText(small+"의 재활용과정");
                how = "플라스틱 용기의 경우에는 색을 내기 위해 사용한 색소 뿐만 아니라,\n";
                how += "나일론,철등과 같은 불순물이 함유되어 있어서 재활용을 진행하지는 않습니다.\n";
                edu2Text.setText(how);
                break;
            case "골판지상자":
                //골판지상자의 경우
                edu2Img.setImageResource(R.drawable.paperbox);
                smallText.setText(small+"의 재활용과정");
                how = "골판지 상자는 폐골판지들을 모아서 만들어내는 대표적인 재활용 형태입니다.\n";
                how += "수거된 골판지 상자는 폐골판지 상태에서 다시 골판지 상자로 변하는 과정을 통해 재활용이 진행됩니다. .\n";
                edu2Text.setText(how);
                break;
            case "스티로폼상자":
                //스티로폼 상자의 경우
                edu2Img.setImageResource(R.drawable.styrofoam_recycle);
                smallText.setText(small+"의 재활용과정");
                how = "스티로폼은 폐기물 업체에서 수거 후,분쇄와 압축 등의 공정을 거쳐 산업재로 재활용 됩니다.\n";
                how += "하지만 쓰고 난 스티로폼 포장재가 다시 포장재로 재생산 되는 것은 불가능합니다.\n";
                edu2Text.setText(how);
                break;

            case "일반쓰레기":
                edu2Img.setImageResource(R.drawable.generalwaste);
                smallText.setText(small+"의 처리과정");
                how = "일반 쓰레기는 재활용 되지 않고 소각장이나 매립장을 통하여 처리됩니다.\n";
                how += ".\n";
                edu2Text.setText(how);
                break;

            case "금속캔":
                edu2Img.setImageResource(R.drawable.can_recycle);
                smallText.setText(small+"의 재활용 과정");
                how = "분리배출 된 캔들은 선별을 통하여 철캔과 알루미늄 캔으로 나누어 압축을 진행하게 됩니다.\n";
                how += "압축물을 통하여 철근과 강판 같은 제품을 생산하고, 용광로에서 철과 알루미늄으로 재탄생하게 됩니다.\n";
                edu2Text.setText(how);
                break;
            case "기타캔":
                edu2Img.setImageResource(R.drawable.can_recycle);
                smallText.setText(small+"의 재활용 과정");
                how = "분리배출 된 캔들은 선별을 통하여 철캔과 알루미늄 캔으로 나누어 압축을 진행하게 됩니다.\n";
                how += "압축물을 통하여 철근과 강판 같은 제품을 생산하고, 용광로에서 철과 알루미늄으로 재탄생하게 됩니다.\n";
                edu2Text.setText(how);
                break;
            case "비닐봉투":
                edu2Img.setImageResource(R.drawable.vinyl_recycle);
                smallText.setText(small+"의 재활용 과정");
                how = "분리배출 된 비닐들은 재황용업체가 수거하여 파쇄,세정,건조 과정을 거쳐 작은 알갱이로 만들어집니다.\n";
                how += "이렇게 만들어진 알갱이는 다른 혼합물과 섞여 새로운 플라스틱으로 탄생되거나 도로 방지턱 등을 만드는데 사용됩니다.\n";
                edu2Text.setText(how);
                break;
            case "비닐포장재":
                edu2Img.setImageResource(R.drawable.vinyl_recycle);
                smallText.setText(small+"의 재활용 과정");
                how = "분리배출 된 비닐들은 재황용업체가 수거하여 파쇄,세정,건조 과정을 거쳐 작은 알갱이로 만들어집니다.\n";
                how += "이렇게 만들어진 알갱이는 다른 혼합물과 섞여 새로운 플라스틱으로 탄생되거나 도로 방지턱 등을 만드는데 사용됩니다.\n";
                edu2Text.setText(how);
                break;
        }
        small = null;
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
                tts.speak(edu2Text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // 다음 화면
        endBtn.setOnClickListener(view -> {
            edu2Img.setImageResource(0);
            Intent i = new Intent(Edu2Activity.this, DetectorActivity.class);
            startActivity(i);
        });

    }
}
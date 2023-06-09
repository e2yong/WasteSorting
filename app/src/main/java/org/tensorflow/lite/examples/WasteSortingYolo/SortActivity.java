package org.tensorflow.lite.examples.WasteSortingYolo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SortActivity extends AppCompatActivity {

    private TextView largeText, smallText;
    private Button yesBtn, noBtn;

    // 소분류 관련
    public String large, small;
    private WasteQuestion q;    // 질문

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        largeText = (TextView) findViewById(R.id.largeText);
        smallText = (TextView) findViewById(R.id.smallText);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        noBtn = (Button) findViewById(R.id.noBtn);

        // 대분류 가져오기
        Intent intent = getIntent();
        large = intent.getStringExtra("large");  // 데이터 받기
        // large = "bottle";           // 테스트: 만약 대분류 결과가 병이라면
        largeText.setText(large);

        q = new WasteQuestion(smallText);
        makeQuestion(large);

        yesBtn.setOnClickListener(view -> {
            q.setAnswer(1);
            // 소분류 대답
            if (q.getEnd() == 0) {
                makeQuestion(large);
                q.setA();
            }
            // 메인화면으로 이동
            else {
                Intent i = new Intent(SortActivity.this, DetectorActivity.class);
                startActivity(i);
            }
        });

        noBtn.setOnClickListener(view -> {
            q.setAnswer(0);
            // 소분류 대답
            if (q.getEnd() == 0) {
                makeQuestion(large);
                q.setA();
            }
            // 다음화면으로 이동
            else {
                Intent i = new Intent(SortActivity.this, Edu1Activity.class);
                i.putExtra("Small", small);  // 소분류 전달
                startActivity(i);
            }
        });
    }

    public void makeQuestion(String large) {
        switch (large) {
            case "can":
                q.isCan();
                break;
            case "box":
                q.isBox();
                break;
            case "bottle":
                q.isBottle();
                break;
            case "vinyl":
                q.isVinyl();
                break;
            default:
                break;
        }

        // 소분류 끝 버튼 변경
        if (q.getEnd() == 1 ) {
            small = q.getSmall();
            yesBtn.setText("END");
            noBtn.setText("NEXT");
        }
    }
}
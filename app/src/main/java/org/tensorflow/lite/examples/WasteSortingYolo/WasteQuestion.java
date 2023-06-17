package org.tensorflow.lite.examples.WasteSortingYolo;

import android.widget.TextView;

import java.util.Arrays;

public class WasteQuestion {
    // 소분류를 위한 질문들

    private TextView text;
    private String small;
    private int[] answer = new int[10]; // 대답 저장
    private int a;      // 대답 순서
    private int end;    // 분류가 끝난지 확인

    WasteQuestion(TextView questionText) {
        this.text = questionText;
        Arrays.fill(answer, -1);
        a = 0;
        end = 0;
    }

    public int getEnd() { return end; }
    public void setAnswer(int i) { answer[a] = i; }
    public void setA() { a++; }
    public String getSmall() { return small; }
    private void setSmall(String name) { small = name; end++; }

    // 소분류에 필요한 질문들
    public void isBox() {
        text.setText("골판지인가?");
        //골판지일 경우
        if(answer[0] == 1){
            text.setText("골판지 상자 입니다");
            setSmall("골판지상자");
        }
        else if(answer[0] == 0){
            //골판지가 아닐 경우
            text.setText("스티로폼 인가?");
            if(answer[1] == 1) {
                //스티로폼 상자인 경우
                text.setText("스티로폼 상자 입니다");
                setSmall("스티로폼상자");
            }
            else if(answer[1] == 0){
                //스티로폼 상자가 아닌경우  -> 일반쓰레기
                //일단 일반까지 넣긴 했는데 상자의 경우에는 좀 더 고려해야할 부분이 필요할 수 있음
                //소분류랑 대분류 바뀌면 그에 따라서 추가적인 수정이 필요할 듯함
                text.setText("일반쓰레기 입니다");
                setSmall("일반쓰레기");
            }
        }
    }

    public void isCan() {
        text.setText("가스를 이용한 제품인가?");
        //기타캔은 가스를 이용한 제품이다
        if(answer[0] == 1){
            //기타 캔인 경우
            text.setText("기타캔 입니다");
            setSmall("기타캔");
        }
        else if(answer[0] == 0){
            //가스를 이용하지 않은 경우 -> 금속캔인 경우
            text.setText("금속캔 입니다");
            setSmall("금속캔");

        }

    }
    public void isVinyl() {
        text.setText("물건을 담는 용도인가?");
        if(answer[0] == 1){
            //비닐봉투인 경우
            text.setText("비닐봉투 입니다");
            setSmall("비닐봉투");
        }
        else if(answer[0] == 0){
            //비닐봉투가 아닌경우
            //비닐 포장재라고 고려 -> 소분류 대분류 수정하면 변경될 수 있음

            text.setText("비닐포장재 입니다");
            setSmall("비닐포장재");
        }
    }

    public void isBottle() {
        text.setText("유리인가?");
        // 유리일 경우
        if (answer[0] == 1) {
            text.setText("도자기인가?");
            // 유리-도자기일 경우
            if (answer[1] == 1) {
                text.setText("불연성쓰레기입니다.");
                setSmall("불연성쓰레기");
            }
            // 유리-도자기가 아닐 경우
            else if (answer[1] == 0) {
                text.setText("소주병 또는 맥주병인가?");
                // 유리-보증금병일 경우
                if (answer[2] == 1) {
                    text.setText("보증금병입니다.");
                    setSmall("보증금병");
                }
                // 유리-잡병일 경우
                else if (answer[2] == 0) {
                    text.setText("잡병입니다.");
                    setSmall("잡병");
                }
            }
        }
        // 유리가 아닐 경우
        else if (answer[0] == 0) {
            text.setText("플라스틱인가?");
            // 플라스틱일 경우
            if (answer[1] == 1) {
                text.setText("투명한가?");
                // 플라스틱-투명일 경우
                if (answer[2] == 1) {
                    text.setText("투명페트병입니다.");
                    setSmall("투명페트병");
                }
                // 투명하지 않을 경우
                else if (answer[2] == 0) {
                    text.setText("일반플라스틱입니다.");
                    setSmall("일반플라스틱");
                }
            }
            // 플라스틱이 아닐 경우
            else if (answer[1] == 0) {
                text.setText("일반쓰레기입니다.");
                setSmall("일반쓰레기");
            }
        }
    }

}
package com.example.WasteSorting;

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

    }

    public void isCan() {

    }
    public void isVinyl() {

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
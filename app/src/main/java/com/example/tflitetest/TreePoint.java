package com.example.tflitetest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class TreePoint {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int treePoint;

    // 생성자
    public TreePoint(Context context) {
        // SharedPreference 초기화
        pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        // 저장한 값 불러오기
        treePoint = pref.getInt("Point", 0);
    }

    public int getPoint() {
        return treePoint;
    }

    public void addPoint(int point) {
        treePoint += point;
        saveTreePoint(treePoint);
    }

    // 포인트 저장
    public void saveTreePoint(int point) {
        editor.putInt("Point", point);
        editor.apply();
    }
}

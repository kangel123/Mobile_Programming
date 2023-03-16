package com.example.project11_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스피너 테스트");

        Integer[] posterID={
                R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04,
                R.drawable.mov05, R.drawable.mov06, R.drawable.mov07, R.drawable.mov08,
                R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12,
                R.drawable.mov13, R.drawable.mov14, R.drawable.mov15, R.drawable.mov16,
                R.drawable.mov17, R.drawable.mov18, R.drawable.mov19, R.drawable.mov20
        };
        final String[] posterTitle = {
                "토이4", "호빗", "제이슨 본", "반지의 제왕", "정직한 후보", "나쁜 녀석들", "겨울왕국2",
                "알라딘", "극한직업", "스파이더맨", "레옹", "주먹왕 랄프2", "타짜:원 아이드 잭", "걸캅스",
                "도굴", "어벤져스:엔드게임", "엑시트", "캡틴 마블", "봉오동 전투", "분노의 질주"
        };

        Spinner spinner = findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, posterTitle);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView ivPoster = findViewById(R.id.ivPoster);
                ivPoster.setImageResource(posterID[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adapter);
    }
}
package com.example.project11_ex1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Integer[] posterID={
            R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04,
            R.drawable.mov05, R.drawable.mov06, R.drawable.mov07, R.drawable.mov08,
            R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12,
            R.drawable.mov13, R.drawable.mov14, R.drawable.mov15, R.drawable.mov16,
            R.drawable.mov17, R.drawable.mov18, R.drawable.mov19, R.drawable.mov20
    };
    String[] posterTitle={
            "토이4", "호빗", "제이슨 본", "반지의 제왕", "정직한 후보", "나쁜 녀석들", "겨울왕국2", "알라딘",
            "극한직업", "스파이더맨", "레옹", "주먹왕 랄프2", "타짜:원 아이드 잭", "걸캅스", "도굴",
            "어벤져스:엔드게임", "엑시트", "캡틴 마블", "봉오동 전투", "분노의 질주"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gv = findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        public MyGridAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(new GridView.LayoutParams(200, 300));
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setPadding(5, 5, 5, 5);
            iv.setImageResource(posterID[i]);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dialogView = View.inflate(MainActivity.this, R.layout.dialog,null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    ImageView ivPoster = dialogView.findViewById(R.id.ivPoster);
                    ivPoster.setImageResource(posterID[i]);
                    dlg.setIcon(R.drawable.movie_icon);
                    dlg.setTitle(posterTitle[i]);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return iv;
        }
    }
}
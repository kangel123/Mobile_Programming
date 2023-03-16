package com.example.calendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    Chronometer chrono;
    RadioGroup rdoGroup;
    RadioButton rdoDate, rdoTime;
    DatePicker dPicker;
    TimePicker tPicker;
    TextView tvResult;
    int selectYear, selectMonth, selectDay;
    int selectHour, selectMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("시간 예약");

        chrono = findViewById(R.id.chronometer1);
        dPicker = findViewById(R.id.datePicker1);
        tPicker = findViewById(R.id.timePicker1);
        rdoGroup = findViewById(R.id.rdoGroup);
        rdoTime = findViewById(R.id.rdoTime);
        rdoDate = findViewById(R.id.rdoDate);
        tvResult = findViewById(R.id.tvResult);
        rdoGroup.setVisibility(View.INVISIBLE);
        tPicker.setVisibility(View.INVISIBLE);
        dPicker.setVisibility(View.INVISIBLE);

        rdoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPicker.setVisibility(View.INVISIBLE);
                dPicker.setVisibility(View.VISIBLE);
            }
        });
        rdoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPicker.setVisibility(View.VISIBLE);
                dPicker.setVisibility(View.INVISIBLE);
            }
        });
        
        chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.RED);
                rdoGroup.setVisibility(View.VISIBLE);
            }
        });
        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.stop();
                chrono.setTextColor(Color.BLUE);
                tvResult.setText(selectYear + "년 " + selectMonth + "월 " + selectDay + "일 " +
                        selectHour + "시 " + selectMinute + "분 예약됨");
            }
        });
        tvResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                rdoGroup.setVisibility(View.INVISIBLE);
                tPicker.setVisibility(View.INVISIBLE);
                dPicker.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        dPicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayofMonth) {
                selectYear = year;
                selectMonth = month + 1;
                selectDay = dayofMonth;
            }
        });
        tPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                selectHour=hour;
                selectMinute=minute;
            }
        });
    }
}

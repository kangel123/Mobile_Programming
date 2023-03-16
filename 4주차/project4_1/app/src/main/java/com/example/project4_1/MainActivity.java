package com.example.project4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edit1, edit2;
    Button btnAdd, btnSub, btnMul, btnDiv;
    TextView textResult;
    String value1, value2;
    double result;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("초간단 계산기");

        edit1 = findViewById(R.id.editText1);
        edit2 = findViewById(R.id.editText2);

        btnAdd = findViewById(R.id.btnPlus);
        btnSub = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnProduct);
        btnDiv = findViewById(R.id.btnDivid);
        textResult = findViewById(R.id.tvResult);

        btnAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                value1 = edit1.getText().toString();
                value2 = edit2.getText().toString();
                result = Integer.parseInt(value1) + Integer.parseInt(value2);
                textResult.setText("계산 결과 : "+ result);
                return false;
            }
        });
        btnSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                value1 = edit1.getText().toString();
                value2 = edit2.getText().toString();
                result = Integer.parseInt(value1) - Integer.parseInt(value2);
                textResult.setText("계산 결과 : "+ result);
                return false;
            }
        });
        btnMul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                value1 = edit1.getText().toString();
                value2 = edit2.getText().toString();
                result = Integer.parseInt(value1) * Integer.parseInt(value2);
                textResult.setText("계산 결과 : "+ result);
                return false;
            }
        });
        btnDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                value1 = edit1.getText().toString();
                value2 = edit2.getText().toString();
                result = Integer.parseInt(value1) / Integer.parseInt(value2);
                textResult.setText("계산 결과 : "+ result);
                return false;
            }
        });
    }
}

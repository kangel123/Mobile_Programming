package com.example.project10_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg = (RadioGroup)findViewById(R.id.rdoGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) findViewById(i);
                switch (i){
                    case R.id.rdoSecond:
                        state=1;
                        break;
                    case R.id.rdoThird:
                        state=2;
                        break;
                }
            }
        });

        findViewById(R.id.btnNewActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (state){
                    case 1:{
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:{
                        Toast.makeText(getApplicationContext(),"둘 중 하나를 선택하세요",
                                Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }
}
package com.example.project4_4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2;
    Switch switchAgree;
    RadioGroup rGroup1;
    RadioButton rdoQ, rdoR, rdoS;
    ImageView imgVersion;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("안드로이드 사진 보기");

        text1 = (TextView) findViewById(R.id.Text1);
        switchAgree = (Switch) findViewById(R.id.SwitchAgree);
        text2 = (TextView) findViewById(R.id.Text2);
        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);
        rdoQ = (RadioButton) findViewById(R.id.RdoQ);
        rdoR = (RadioButton) findViewById(R.id.RdoR);
        rdoS = (RadioButton) findViewById(R.id.RdoS);
        imgVersion = (ImageView) findViewById(R.id.Imgversion);

        switchAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchAgree.isChecked() == true){
                    text2.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    imgVersion.setVisibility(View.VISIBLE);
                } else
                {
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    imgVersion.setVisibility(View.INVISIBLE);
                }
            }
        });
        rGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (rGroup1.getCheckedRadioButtonId()){
                    case R.id.RdoQ:
                        imgVersion.setImageResource(R.drawable.q10);
                        break;
                    case R.id.RdoR:
                        imgVersion.setImageResource(R.drawable.r11);
                        break;
                    case R.id.RdoS:
                        imgVersion.setImageResource(R.drawable.s12);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"버전을 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
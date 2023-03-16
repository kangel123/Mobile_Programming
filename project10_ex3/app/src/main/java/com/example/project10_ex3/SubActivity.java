package com.example.project10_ex3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity{
    int result = 0;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        setTitle("second 액티비티");

        Intent inIntent = getIntent();
        String name = inIntent.getStringExtra("name");
        int num1 = inIntent.getIntExtra("Num1", 0);
        int num2= inIntent.getIntExtra("Num2", 0);
        switch (name){
            case"+": result=num1+num2; break;
            case"-": result=num1-num2; break;
            case"*": result=num1*num2; break;
            case"/": result=num1/num2; break;
        }

        Button btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
                outIntent.putExtra("result", result);
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }
}
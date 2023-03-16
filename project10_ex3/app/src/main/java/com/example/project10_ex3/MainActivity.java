package com.example.project10_ex3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");

        RadioGroup rdoGroup = findViewById(R.id.rdoGroup);
        RadioButton rdoAdd = findViewById(R.id.rdoAdd);
        RadioButton rdoSub = findViewById(R.id.rdoSub);
        RadioButton rdoMul = findViewById(R.id.rdoMul);
        RadioButton rdoDiv = findViewById(R.id.rdoDiv);

        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (rdoGroup.getCheckedRadioButtonId()) {
                    case(R.id.rdoAdd):
                        name="+";
                        break;
                    case(R.id.rdoSub):
                        name="-";
                        break;
                    case(R.id.rdoMul):
                        name="*";
                        break;
                    case(R.id.rdoDiv):
                        name="/";
                        break;
                }
            }
        });

        Button btnNewActivity = findViewById(R.id.btnNewActivity);
        btnNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtNum1 = findViewById(R.id.edtNum1);
                EditText edtNum2 = findViewById(R.id.edtNum2);
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("Num1", Integer.parseInt(edtNum1.getText().toString()));
                intent.putExtra("Num2", Integer.parseInt(edtNum2.getText().toString()));
                intent.putExtra("name", name);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            Toast.makeText(getApplicationContext(), "결과값:" + result, Toast.LENGTH_SHORT).show();
        }
    }
}
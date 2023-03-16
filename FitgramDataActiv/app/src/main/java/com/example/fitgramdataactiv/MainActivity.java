package com.example.fitgramdataactiv;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    myDBHelper myHelper;
    Button btnStart, btnEnd, btnResult,getBtnWeight;
    TextView txtName, txtWeight;
    EditText edtName, edtWeight, edtWeightResult ,edtExerciseResult;
    LinearLayout dialogView;
    CheckBox chkAgree;
    EditText Edit1,Edit2,Edit3,Edit4,Edit5,Edit6,Edit7,Edit8,Edit9,Edit10,Edit11,Edit12,Edit13,Edit14,Edit15,
            Edit16,Edit17,Edit18,Edit19,Edit20,Edit21,Edit22,Edit23,Edit24;
    Chronometer chrono;
    SQLiteDatabase sqLiteDatabase;

    String exercise=null;
    int weight=0, set=0, num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Fitgram");
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnResult = (Button) findViewById(R.id.button5);
        Edit1 = findViewById(R.id.Edit1);
        Edit2 = findViewById(R.id.Edit2);
        Edit3 = findViewById(R.id.Edit3);
        Edit4 = findViewById(R.id.edt1);
        Edit5 = findViewById(R.id.edt2);
        Edit6 = findViewById(R.id.edt3);
        Edit7 = findViewById(R.id.edt4);
        Edit8 = findViewById(R.id.edt5);

        edtExerciseResult = findViewById(R.id.edtNameResult);

        chrono = (Chronometer) findViewById(R.id.chronometer1);

        myHelper = new myDBHelper(this);
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.RED);
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chrono.stop();
                chrono.setTextColor(Color.BLUE);
            }
        });
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int date = Integer.parseInt(Edit1.getText().toString()) * 10000 + Integer.parseInt(Edit2.getText().toString()) * 100 + Integer.parseInt(Edit3.getText().toString());
                    int volumn = weight * set * num;

                    sqLiteDatabase = myHelper.getWritableDatabase();
                    Cursor cursor;
                    cursor = sqLiteDatabase.rawQuery("select exercise, weight from recordTBL where date =" + date + ";", null);
                    int recordCount = cursor.getCount();
                    int id = date * 100 + recordCount;
                    for (int i=0; i < set; i++) {
                        id += 1;
                        sqLiteDatabase.execSQL("insert into recordTBL values(" + id + ", " + date + ", '" + exercise + "' ," + weight + "," +set+ ","+ num + ","+ volumn + ");");
                    }
                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                    sqLiteDatabase.close();

                    sqLiteDatabase = myHelper.getWritableDatabase();
                    cursor = sqLiteDatabase.rawQuery("select exercise, weight, n_set, num from recordTBL where date =" + date + ";", null);
                    recordCount = cursor.getCount();
                    cursor.moveToNext();
                    edtExerciseResult.setText("");
                    for (int i = 0; i < recordCount; ){
                        exercise = cursor.getString(0);
                        set = cursor.getInt(2);
                        edtExerciseResult.setText(edtExerciseResult.getText().toString()  + exercise + " : \n");
                        i+=set;
                        for (int j=0; j < set; j++) {
                            int k=j+1;
                            weight = cursor.getInt(1);
                            num = cursor.getInt(3);
                            edtExerciseResult.setText(edtExerciseResult.getText().toString() + k + "세트 : " + weight + "kg\t" + num + "회\n");
                            cursor.moveToNext();
                        }
                    }
                Edit8.setText("성공");
                cursor.close();
                sqLiteDatabase.close();
                } catch (Exception e) {
                    Edit8.setText("실패");
                    Toast.makeText(MainActivity.this, "날짜, 종목, 세트, 무게, 횟수를 다시 입력하시오 ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemMain:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.itemDay:
                Intent intent2 = new Intent(getApplicationContext(), DayActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.itemMonth:
                Intent intent3 = new Intent(getApplicationContext(), MonthActivity.class);
                startActivity(intent3);
                finish();
                return true;
        }
        return false;
    }
    public void CheckboxClick(View view) {
        int v=0;
        final ArrayList<String> selectedEvents = new ArrayList<String>(); //선택된 항목 저장할 리스트
        final String[] words = new String[]{"스쿼트", "데드리프트", "벤치프레스", "밀리터리프레스", "레그프레스", "해머컬"}; //DB로 가져올 리스트

        final AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setTitle("종목")
                .setSingleChoiceItems(words, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //각 리스트를 선택했을때
                        Edit4.setText(words[whichButton]);
                        exercise = words[whichButton];
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                Edit4.setText(null);
                                exercise=null;
                            }
                        });
        dialog.show();
    }
    public void CheckboxClick1(View view) {
        final ArrayList<String> selectedEvents = new ArrayList<String>(); //선택된 항목 저장할 리스트
        final String[] words = new String[]{"1 set", "2 set", "3 set", "4 set", "5 set", "6 set"}; //DB로 가져올 리스트

        final AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setTitle("세트")
                .setSingleChoiceItems(words, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Edit5.setText(words[whichButton]);
                        set=whichButton+1;
                        //각 리스트를 선택했을때
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
            }
                }).setNegativeButton("Cancel",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel 버튼 클릭시
                Edit5.setText(null);
                set=0;
            }
        });
        dialog.show();
    }
    public void CheckboxClick2(View view) {
        final ArrayList<String> selectedEvents = new ArrayList<String>(); //선택된 항목 저장할 리스트
        final String[] words = new String[]{"60kg", "70kg", "80kg", "90kg", "100kg", "110kg"}; //DB로 가져올 리스트

        final AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setTitle("무게")
                .setSingleChoiceItems(words, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Edit6.setText(words[whichButton]);
                        weight = 60 + 10 * whichButton;
                        //각 리스트를 선택했을때
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                Edit6.setText(null);
                                weight=0;
                            }
                        });
        dialog.show();
    }
    public void CheckboxClick3(View view) {

        final ArrayList<String> selectedEvents = new ArrayList<String>(); //선택된 항목 저장할 리스트
        final String[] words = new String[]{"1회", "3회", "5회", "10회", "15회", "20회"}; //DB로 가져올 리스트
        final AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.setTitle("횟수")
                .setSingleChoiceItems(words, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Edit7.setText(words[whichButton]);
                        num=2*whichButton+1;
                        //각 리스트를 선택했을때
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // OK 버튼 클릭시 , 여기서 선택한 값을 메인 Activity 로 넘기면 된다.
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                Edit7.setText(null);
                                num=0;
                            }
                        });
        dialog.show();
    }



    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "recordDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table recordTBL(record_id int PRIMARY KEY, date int(10),  exercise char(10), weight int(10), n_set int(10), num int(10), volume int(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS recordDB;");
            onCreate(sqLiteDatabase);
        }
    }

}



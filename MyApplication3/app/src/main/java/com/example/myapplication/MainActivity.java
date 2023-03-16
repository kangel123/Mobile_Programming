package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;0

public class MainActivity extends AppCompatActivity {
    EditText Edt1,Edt2, EdtDate, EdtExercise, EdtWeight, EdtSet, EdtNum, EdtYear, EdtMonth, EdtDay;
    TextView Tv1;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Edt1 = findViewById(R.id.edt1);
        Edt2 = findViewById(R.id.edt2);
        EdtDate = findViewById(R.id.EdtDate);
        EdtExercise = findViewById(R.id.EdtExercise);
        EdtWeight = findViewById(R.id.EdtWeight);
        EdtSet = findViewById(R.id.EdtSet);
        EdtNum = findViewById(R.id.EdtNum);
        Tv1 = findViewById(R.id.tv1);

        Button Btn1 = findViewById(R.id.btn1);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String databaseName = Edt1.getText().toString();
                createDatabase(databaseName);
            }
        });
        Button Btn2 = findViewById(R.id.btn2);
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tableName = Edt2.getText().toString();
                createTable(tableName);
            }
        });
        Button Btn3 = findViewById(R.id.btn3);
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Date = Integer.parseInt(EdtDate.getText().toString());
                String Exercise = EdtExercise.getText().toString();
                int Weight = Integer.parseInt(EdtWeight.getText().toString());
                int Num = Integer.parseInt(EdtNum.getText().toString());
                int Set = Integer.parseInt(EdtSet.getText().toString());
                int Volumn = Weight*Set*Num;
                insertRecord(Date,Exercise,Weight,Volumn);
            }
        });

        //일별
        Button Btn4 = findViewById(R.id.btn4);
        Btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Day=Integer.parseInt(EdtYear.getText().toString())*10000+Integer.parseInt(EdtMonth.getText().toString())*100+Integer.parseInt(EdtDay.getText().toString());
                executeQueryDay(Day);
            }
        });

        //월별
        Button Btn5 = findViewById(R.id.btn5);
        Btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Day=Integer.parseInt(EdtYear.getText().toString())*10000+Integer.parseInt(EdtMonth.getText().toString())*100;
                executeQueryMonth(Day);
            }
        });
    }
    public void createDatabase(String databaseName){
        println("createDatabase 호출됨");

        try {
            database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            println("데이터베이스 생성됨 : " + databaseName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void createTable(String tableName){
        println("createTable 호출됨");
        try {
            if (database == null) {
                println("데이터베이스를 먼저 열어주세요.");
                return;
            }
            String sql = "create table if not exists " + tableName + "(record_id int PRIMARY KEY autoincrement, date int(10),  exercise char(10), weight int(10), volume int(10))";
            database.execSQL(sql);
            println("테이블 생성됨 : " + tableName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertRecord(int Date,String Exercise, int Weight, int Volumn){
        println("insertRecord 호출됨");
        try {
            if (database == null) {
                println("데이터베이스를 먼저 열어주세요.");
                return;
            }

            String tableName = Edt2.getText().toString();
            if (tableName == null) {
                println("테이블 이름을 입력하세요");
                return;
            }

            String sql = "insert into " + tableName + "values("+ Date +","+ Exercise+","+ Weight+","+ Volumn+")";
            database.execSQL(sql);
            println("레코드 추가함");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //일별
    public void executeQueryDay(int Day){
        println("executeQueryDay 호출됨");

        try {
            if (database == null) {
                println("데이터 베이스를 먼저 열어주세요.");
                return;
            }

            String tableName = Edt2.getText().toString();
            if (tableName == null) {
                println("테이블 이름을 입력하세요.");
                return;
            }
            // 해당 날짜의 운동종목에 따른 최대무게, 총충량
            String sql = "select exercise, max(weight) ,sum(volumn) from " + tableName + "where date="+ Day +"group by exercise";
            Cursor cursor = database.rawQuery(sql, null);
            int recordCount = cursor.getCount();
            println("레코드 갯수 : " + recordCount);

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();
                String exercise = cursor.getString(1);      //종목
                int weight = cursor.getInt(2);                //최대 무게
                int volumn = cursor.getInt(3);         //총중량
                println(exercise + " , " + weight + ","+ volumn);
            }

            cursor.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //월별
    public void executeQueryMonth(int Day){
        println("executeQueryMonth 호출됨");

        try {
            if (database == null) {
                println("데이터 베이스를 먼저 열어주세요.");
                return;
            }

            String tableName = Edt2.getText().toString();
            if (tableName == null) {
                println("테이블 이름을 입력하세요.");
                return;
            }

            // 월별(1일부터 31일까지)
            for (int j = 0; j<31;j++) {
                Day += 1;
                println(Day + "일");
                // 해당 날짜의 운동종목에 따른 최대무게, 총충량
                String sql = "select exercise, max(weight) ,sum(volumn) from " + tableName + "where date=" + Day + "group by exercise";
                Cursor cursor = database.rawQuery(sql, null);
                int recordCount = cursor.getCount();

                for (int i = 0; i < recordCount; i++) {
                    cursor.moveToNext();
                    String exercise = cursor.getString(1);      //종목
                    int weight = cursor.getInt(2);                //최대 무게
                    int volumn = cursor.getInt(3);         //총중량
                    println(exercise + " , " + weight + "," + volumn);
                }
                cursor.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void println(String data){
        Tv1.append(data+"\n");
    }
}
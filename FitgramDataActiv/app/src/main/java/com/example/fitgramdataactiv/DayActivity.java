package com.example.fitgramdataactiv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DayActivity extends MainActivity {
    myDBHelper myHelper;
    ImageView imageView;
    Button btnPicture, btnDate;
    EditText edtDate, edtResult, edtYear, edtMonth, edtDay;
    LinearLayout dialogView;
    SQLiteDatabase sqLiteDatabase;


    int Year=0, Month=0, Day=0, Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_record);
        setTitle("Fitgram");

        edtDate = findViewById(R.id.edtdate);
        edtResult= findViewById(R.id.edtresult);
        myHelper = new myDBHelper(this);

        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView= (LinearLayout) View.inflate (DayActivity.this,R.layout.dialog,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(DayActivity.this);
                dlg.setTitle("날짜 입력");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edtYear = (EditText)dialogView.findViewById(R.id.edtYear);
                        edtMonth = (EditText)dialogView.findViewById(R.id.edtMonth);
                        edtDay = (EditText)dialogView.findViewById(R.id.edtDay);

                        Year=Integer.parseInt(edtYear.getText().toString());
                        Month=Integer.parseInt(edtMonth.getText().toString());
                        Day=Integer.parseInt(edtDay.getText().toString());
                        edtDate.setText(Year+"년 "+Month+"월 "+Day+"일");
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        imageView = findViewById(R.id.ivDay);
        btnPicture = findViewById(R.id.btnPicture);
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        final RelativeLayout capture_target_Layout = (RelativeLayout)findViewById(R.id.CaptureLayout);

        SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss"); //년,월,일,시간 포멧 설정
        Date time = new Date(); //파일명 중복 방지를 위해 사용될 현재시간
        String current_time = sdf.format(time); //String형 변수에 저장

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "저장!!!!!", Toast.LENGTH_SHORT).show();
                Request_Capture(capture_target_Layout,current_time + "_capture"); //지정한 Layout 영역 사진첩 저장 요청
            }
        });

    }

    public void CheckboxClick(View view) {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayList<String> selectedEvents = new ArrayList<String>(); //선택된 항목 저장할 리스트
        final String[] words = new String[]{};
        
        Date=Year*10000+Month*100+Day;
        sqLiteDatabase = myHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("select exercise from recordTBL where date =" + Date + "group by exercise;", null);
        int recordCount = cursor.getCount();
        cursor.moveToNext();
        for (int i = 0; i < recordCount; ){
            String exercise = cursor.getString(0);
            words[i]=exercise;
            }
        cursor.close();
        sqLiteDatabase.close();
        edtResult.setText("");
        builder.setTitle("EVENT");
        builder.setMultiChoiceItems(words, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int pos, boolean isChecked) {
                if (isChecked == true) {
                    selectedEvents.add(words[pos]);
                    if (selectedEvents.size() > 4) {
                        Toast toast = Toast.makeText(getApplicationContext(), "선택 가능한 종목은 최대 4개 입니다.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        ((AlertDialog) dialog).getListView().setItemChecked(pos, false);
                    }
                } else                  // Check 해제 되었을 때 제거
                    selectedEvents.remove(pos);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                for (String event : selectedEvents) {
                    sqLiteDatabase = myHelper.getWritableDatabase();
                    Cursor cursor;
                    cursor = sqLiteDatabase.rawQuery("select exercise, max(weight), sum(volumn) from recordTBL where date =" + Date + "and exercise = "+ event +"group by exercise;", null);
                    int recordCount = cursor.getCount();
                    for (int i = 0; i < recordCount; ){
                        String exercise = cursor.getString(0);
                        int max_weight = cursor.getInt(1);
                        int sum_volumn = cursor.getInt(2);
                        edtResult.setText(edtResult.getText().toString()+"종목 : "+exercise+"\t 최대무게 : " +max_weight+"\t 총중량 : "+sum_volumn+"\n");
                    }
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                edtResult.setText("");
                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void Request_Capture(View view, String title){
        if(view == null){ //Null Point Exception ERROR 방지
            System.out.println(":::ERROR:::: view == NULL");
            return;
        }

        /* 캡쳐 파일 저장 */
        view.buildDrawingCache(); //캐시 비트 맵 만들기
        Bitmap bitmap = view.getDrawingCache();
        FileOutputStream fos;

        /* 저장할 폴더 Setting */
        File uploadFolder = Environment.getExternalStoragePublicDirectory("/Pictures"); //저장 경로 (File Type형 변수)

        if (!uploadFolder.exists()) { //만약 경로에 폴더가 없다면
            uploadFolder.mkdir(); //폴더 생성
        }

        /* 파일 저장 */
        String Str_Path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures"; //저장 경로 (String Type 변수)

        try{
            fos = new FileOutputStream(Str_Path+title+".jpg"); // 경로 + 제목 + .jpg로 FileOutputStream Setting
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
        }catch (Exception e){
            e.printStackTrace();
        }

        //캡쳐 파일 미디어 스캔
        MediaScanner ms = MediaScanner.newInstance(getApplicationContext());

        try { // TODO : 미디어 스캔
            ms.mediaScanning(Str_Path + title + ".jpg");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("::::ERROR:::: "+e);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
                break;
        }
    }

}
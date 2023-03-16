package com.example.project10_ex2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");

        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");
        String[] imageName = intent.getStringArrayExtra("ImageName");

        TextView tv[] = new TextView[imageName.length];
        RatingBar rbar[] = new RatingBar[imageName.length];
        Integer tvID[] = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,
                R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9};
        Integer rbarID[] = {R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4,
                R.id.rbar5, R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9};
        TextView resulttv = findViewById(R.id.resulttv);
        ImageView resultpic = findViewById(R.id.resultpic);
        int max=-1;

        for (int i = 0; i < voteResult.length; i++){
            tv[i] = findViewById(tvID[i]);
            rbar[i] = findViewById(rbarID[i]);
            tv[i].setText(imageName[i]);
            rbar[i].setRating((float) voteResult[i]);
            if (voteResult[i] > max) {
                max = voteResult[i];
                if(i==0){resultpic.setImageResource(R.drawable.pic1);}
                else if(i==1){resultpic.setImageResource(R.drawable.pic2);}
                else if(i==2){resultpic.setImageResource(R.drawable.pic3);}
                else if(i==3){resultpic.setImageResource(R.drawable.pic4);}
                else if(i==4){resultpic.setImageResource(R.drawable.pic5);}
                else if(i==5){resultpic.setImageResource(R.drawable.pic6);}
                else if(i==6){resultpic.setImageResource(R.drawable.pic7);}
                else if(i==7){resultpic.setImageResource(R.drawable.pic8);}
                else if(i==8){resultpic.setImageResource(R.drawable.pic9);}
                resulttv.setText(imageName[i]);
            }
        }
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

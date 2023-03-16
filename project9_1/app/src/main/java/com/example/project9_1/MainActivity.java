package com.example.project9_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
    }

    private static class MyGraphicView extends View {

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            Rect rect1 = new Rect(50,50,50+500,50+50);
            canvas.drawRect(rect1,paint);

            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(50);
            canvas.drawLine(50, 200, 600, 200, paint);

            canvas.drawOval(100,300,500,450,paint);

            RectF rect2 = new RectF();
            rect2.set(50,400,250,600);
            canvas.drawArc(rect2, 45, 90,true,paint);

            paint.setColor(Color.argb(80,0,0,255));
            Rect rect3 = new Rect(50, 650, 250, 850);
            canvas.drawRect(rect3, paint);

            paint.setColor(Color.argb(100,255,0,0));
            Rect rect4 = new Rect(50+50, 650+50, 250+50, 850+50);
            canvas.drawRect(rect4, paint);
        }
    }
}
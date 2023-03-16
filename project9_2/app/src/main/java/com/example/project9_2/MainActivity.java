package com.example.project9_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ImageButton ibZoomin, ibZoomout, ibRotate, ibBright, ibDark, ibBlur;
    MyGraphicView graphicView;
    static float scaleX=1, scaleY=1, angle=0, color=1, blur=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("미니 포토샵");

        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView);
        clickIcons();
    }


    private void  clickIcons(){
        ibZoomin = (ImageButton) findViewById(R.id.ibZoomin);

        ibZoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                graphicView.invalidate();
            }
        });

        ibZoomout = (ImageButton) findViewById(R.id.ibZoomout);
        ibZoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate();
            }
        });

        ibRotate = (ImageButton) findViewById(R.id.ibRotate);
        ibRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle = angle + 20;
                graphicView.invalidate();
            }
        });

        ibBright = (ImageButton) findViewById(R.id.ibBright);
        ibBright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = color + 0.2f;
                graphicView.invalidate();
            }
        });

        ibDark = (ImageButton) findViewById(R.id.ibDark);
        ibDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = color - 0.2f;
                graphicView.invalidate();
            }
        });

        ibBlur = (ImageButton) findViewById(R.id.ibBlur);
        ibBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blur==1) blur=30;
                else blur=1;
                graphicView.invalidate();
            }
        });

    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected  void onDraw(Canvas canvas){
            super.onDraw(canvas);

            int cenX = this.getWidth()/2;
            int cenY = this.getHeight()/2;
            canvas.scale(scaleX,scaleY,cenX,cenY);
            canvas.rotate(angle, cenX, cenY);

            Paint paint = new Paint();
            float[] array = {
                    color, 0, 0, 0, 0,
                    0, color, 0, 0, 0,
                    0, 0, color, 0, 0,
                    0, 0, 0, color, 0};
            ColorMatrix cm = new ColorMatrix(array);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            BlurMaskFilter bMask;
            bMask = new BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL);
            paint.setMaskFilter(bMask);

            Bitmap picture = BitmapFactory.decodeResource(getResources(),R.drawable.lena256);
            int picX = (this.getWidth()-picture.getWidth())/2;
            int picY = (this.getHeight()-picture.getHeight())/2;

            canvas.drawBitmap(picture, picX, picY, paint);
            picture.recycle();
        }
    }
}
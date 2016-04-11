package com.example.gjun.t0411gesture;

import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
/**
 * 簡易的上下左右手勢辨識功能*/
public class GestureFilpActivity extends AppCompatActivity implements GestureDetector.OnGestureListener ,View.OnTouchListener{
    GestureDetector det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_filp);

        det = new GestureDetector(this);
        GestureOverlayView gov = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
        gov.setOnTouchListener(this);
    }

    //觸發手勢執行onFlig的動作
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return det.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 50) {
            Toast.makeText(GestureFilpActivity.this, "左", Toast.LENGTH_LONG).show();
            return true;
        } else if (e1.getX() - e2.getX() < -50) {
            Toast.makeText(GestureFilpActivity.this, "右", Toast.LENGTH_LONG).show();
            return true;
        } else if (e1.getY() - e2.getY() > 50) {
            Toast.makeText(GestureFilpActivity.this, "上", Toast.LENGTH_LONG).show();
            return true;
        } else if (e1.getY() - e2.getY() < -50) {
            Toast.makeText(GestureFilpActivity.this, "下", Toast.LENGTH_LONG).show();
            return true;
        }
            return false;
        }

}

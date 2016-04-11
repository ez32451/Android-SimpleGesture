package com.example.gjun.t0411gesture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;
/**
 * Created by Kerry on 2016/4/11.
 * 利用手勢功能實現圖片的滑動轉換
 */

public class SlideActivity extends AppCompatActivity {
    public ViewGroup container1, container2;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        container1 = (ViewGroup) findViewById(R.id.container1);
        container2 = (ViewGroup) findViewById(R.id.container2);
        SampleGesture gestureListener = new SampleGesture(this);
        gestureDetector = new GestureDetector(gestureListener);

    }

    // called automatically, any screen action will Triggered it
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(this, "touch", Toast.LENGTH_SHORT).show();
        return gestureDetector.onTouchEvent(event);
    }
}

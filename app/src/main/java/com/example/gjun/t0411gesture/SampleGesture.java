package com.example.gjun.t0411gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


/**
 * 利用手勢功能實現圖片的滑動轉換
 */
public class SampleGesture implements GestureDetector.OnGestureListener {
    SlideActivity slide;
    private static final int SWIPE_MAX_OFF_PATH = 100;
    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    public SampleGesture(SlideActivity slide) {
        this.slide = slide;
    }

    // 輕觸觸控式螢幕，由 1 個 MotionEvent ACTION_DOWN 觸發
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("TAG", "[onDown]");
        return false;
    }
    // 輕觸觸控式螢幕，不要鬆開或拖動，由一個 1 個 MotionEvent ACTION_DOWN 觸發
    // 注意和 onDown()的區別，強調的是沒有鬆開或者拖動的狀態
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("TAG", "[onShowPress]");
    }
    // （輕觸觸控式螢幕後）鬆開，由一個 1 個 MotionEvent ACTION_UP 觸發
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("TAG", "[onSingleTapUp]");
        return false;
    }
    // 按下觸控式螢幕，並拖曳，由 1 個 MotionEvent ACTION_DOWN, 多個 ACTION_MOVE 觸發
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("TAG", "[onScroll]");
        return false;
    }
    // 長按觸控式螢幕，由多個 MotionEvent ACTION_DOWN 觸發
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("TAG", "[onLongPress]");
    }

    // 用戶按下觸控式螢幕、快速移動後鬆開,由 1 個 MotionEvent ACTION_DOWN,
    // 多個 ACTION_MOVE, 1 個 ACTION_UP 觸發
    // e1：第 1 個 ACTION_DOWN MotionEvent
    // e2：最後一個 ACTION_MOVE MotionEvent
    // velocityX：X 軸上的移動速度，圖元/秒
    // velocityY：Y 軸上的移動速度，圖元/秒
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Toast.makeText(slide, "touch1", Toast.LENGTH_SHORT).show();
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) return false;
        if ((e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            slide.container1.setAnimation(AnimationUtils.loadAnimation(slide, R.anim.push_left_out));
            slide.container2.setVisibility(View.VISIBLE);
            slide.container2.setAnimation(AnimationUtils.loadAnimation(slide, R.anim.push_right_in));
            slide.container1.setVisibility(View.GONE);
        } else if ((e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            slide.container1.setVisibility(View.VISIBLE);
            slide.container1.setAnimation(AnimationUtils.loadAnimation(slide, R.anim.push_left_in));
            slide.container2.setAnimation(AnimationUtils.loadAnimation(slide, R.anim.push_right_out));
            slide.container2.setVisibility(View.GONE);
        }
        return true;
    }
}

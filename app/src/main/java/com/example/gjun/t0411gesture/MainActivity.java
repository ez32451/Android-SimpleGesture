package com.example.gjun.t0411gesture;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
/*讀取自訂手勢並顯示在畫面上*/
public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener{
    GestureLibrary mLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);
        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!mLibrary.load()) {
            finish();
        }
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList predictions = mLibrary.recognize(gesture);
        // We want at least one prediction
        if (predictions.size() > 0) {
            Prediction prediction = (Prediction) predictions.get(0);
            // We want at least some confidence in the result
            if (prediction.score > 1.0) {
                // Show the spell
                Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
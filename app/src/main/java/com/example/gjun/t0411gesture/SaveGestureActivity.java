package com.example.gjun.t0411gesture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
/*使用手勢功能,把手勢存成圖檔放入SD卡中*/
public class SaveGestureActivity extends AppCompatActivity {

    private void Save_to_SD(Bitmap bm, String image_name) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        String meteoDirectory_path = extStorageDirectory;
        OutputStream outStream = null;

        File file = new File(meteoDirectory_path, "/" + image_name + ".jpg");

        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Log.i("ImagesToSD:", "height = " + bm.getHeight() + ", width = " + bm.getWidth());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("Hub", "FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Hub", "IOException: " + e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_gesture);
        requestPermission();
        TextView textView = (TextView) findViewById(R.id.TextView01);
        textView.setText(R.string.app_description);
        //GestureLibrary-登錄Gesture的圖書庫
        // final GestureLibrary gl = GestureLibraries.fromPrivateFile(this, "gestures");

        final GestureLibrary gl = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gl.load()) {
            Toast.makeText(this, "Loading Faile", Toast.LENGTH_SHORT).show();
            finish();
        }
        //GestureOverlayView-Gesture輸入的透明性重疊層
        final GestureOverlayView gov = (GestureOverlayView) findViewById(R.id.GestureOverlayView01);
        //OnGestureListener監聽功能
        gov.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
            public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
                Log.v("Gesture", "onGestureStarted");
            }

            public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
                Log.v("Gesture", "onGestureEnded");
            }

            public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
                Log.v("Gesture", "onGestureCancelled");
            }

            public void onGesture(GestureOverlayView overlay, MotionEvent event) {
                Log.v("Gesture", "onGesture");
            }
        });
        //OnGesturePerformedListener監聽功能
        gov.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                int count=0;
                Bitmap bm = gesture.toBitmap(320, 480, 1, Color.RED);
                Save_to_SD(bm, "g"+count);
                count++;
                Log.v("Gesture", "onGesturePerformed");
                Log.v("Gesture", "id:" + gesture.getID());
                Log.v("Gesture", "length:" + gesture.getLength());
                Log.v("Gesture", "describeContents:" + gesture.describeContents());
                if (gl.getGestureEntries().size() == 0) {
                    gl.addGesture("First", gesture);
                    gl.save();
                } else {
                    //呼叫GestureLibrary.recognize(gesture)取得predictions
                    ArrayList<Prediction> predictions = gl.recognize(gesture);
                    Log.v("Gesture", "predictions.size:" + predictions.size());
                    for (Prediction p : predictions) {
                        Log.v("Gesture", "Prediction name:" + p.name + " score:" + p.score);
                        if (p.score > 1.0) {
                            Toast.makeText(SaveGestureActivity.this, "name:" + p.name, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        })
        ;   //OnGesturingListener監聽功能
        gov.addOnGesturingListener(new GestureOverlayView.OnGesturingListener() {
            public void onGesturingEnded(GestureOverlayView overlay) {
                Log.v("Gesture", "onGesturingEnded");
            }

            public void onGesturingStarted(GestureOverlayView overlay) {
                Log.v("Gesture", "onGesturingStarted");
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQ_CODE: {
                // 如果請求被拒絕，那麼通常grantResults陣列為空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 申請成功，進行相應操作
                    Log.e("external storage", "Success");
                    Toast.makeText(this, "external storage success", Toast.LENGTH_LONG).show();
                } else {
                    // 申請失敗，可以繼續向用戶解釋。
                    Log.e("external storage", "failed");
                    Toast.makeText(this, "external storage failed", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public final int EXTERNAL_STORAGE_REQ_CODE = 10;

    public void requestPermission() {
        // 判斷當前Activity是否已經獲得了該許可權
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // 如果App的許可權申請曾經被使用者拒絕過，就需要在這裡跟使用者做出解釋
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "you refued permission Requested ", Toast.LENGTH_SHORT).show();
            } else {
                // 進行許可權請求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_CODE);
            }
        }
    }
}

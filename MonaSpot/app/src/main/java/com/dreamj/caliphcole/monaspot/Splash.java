package com.dreamj.caliphcole.monaspot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class Splash extends Activity {


    Thread runTime;
    MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        final ImageView splashImageView = (ImageView) findViewById(R.id.ivSplash);
        splashImageView.setBackgroundResource(R.drawable.drawablesplash);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();

        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        //song = MediaPlayer.create(Splash1.this,R.raw.splash);
        //song.start();
        runTime = new Thread() {
            public void run() {
                try {
                    synchronized (this) {

                        wait(5000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent main = new Intent(
                            "com.dreamj.caliphcole.monaspot.MainActivity");
                    startActivity(main);
                }

            }
        };
        runTime.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(runTime){
                runTime.notifyAll();
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //song.release();
         finish();
    }
    @Override
    protected void onResume(){

        super.onResume();
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (!firstRun) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.d("TAG1", "firstRun(false): " + Boolean.valueOf(firstRun).toString());
        } else {
            Log.d("TAG1", "firstRun(true): " + Boolean.valueOf(firstRun).toString());
        }
    }
}

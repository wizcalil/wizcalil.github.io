package com.dreamj.caliphcole.monasocial;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by CaliphCole on 12/30/2014.
 */
public class Splash extends Activity {


    Thread runTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

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
                            "com.dreamj.caliphcole.monasocial.MainActivity");
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
    public void onBackPressed() {

    }

}

package com.semangatta.lontaraku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class SplashScreen extends Activity {

    ImageView splashscreen_permainan, splashscreen_logoif, splashscreen_logotelu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        GambarSP();
        SplashscreenDua();
        SplashscreenTiga();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(6000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }; timerThread.start();
    }

    public void SplashscreenDua(){
        Runnable r = new Runnable(){
            public void run(){
                splashscreen_permainan = (ImageView) findViewById(R.id.splashcreen_3);
                splashscreen_permainan.setVisibility(View.VISIBLE);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 3000);
    }

    public void SplashscreenTiga(){
        Runnable r = new Runnable(){
            public void run(){
                splashscreen_logoif = (ImageView) findViewById(R.id.splashcreen_2);
                splashscreen_logoif.setVisibility(View.VISIBLE);
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 1500);
    }

    public void GambarSP(){
        splashscreen_permainan = (ImageView) findViewById(R.id.splashcreen_3);
        Glide   .with(this)
                .load(R.drawable.ssaplikasi)
                .placeholder(R.drawable.ssaplikasi) //GANTI
                .dontAnimate()
                .centerCrop()
                .into(splashscreen_permainan);

        splashscreen_logoif = (ImageView) findViewById(R.id.splashcreen_2);
        Glide   .with(this)
                .load(R.drawable.splogoif)
                .placeholder(R.drawable.splogoif)
                .dontAnimate()
                .centerCrop()
                .into(splashscreen_logoif);

        splashscreen_logotelu = (ImageView) findViewById(R.id.splashcreen_1);
        Glide   .with(this)
                .load(R.drawable.splogotelu)
                .placeholder(R.drawable.splogotelu)
                .dontAnimate()
                .centerCrop()
                .into(splashscreen_logotelu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);
        finish();
    }
}

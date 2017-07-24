package com.semangatta.lontaraku;

import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DialogBantuan extends Activity {

    private Animation animScale;
    ImageButton btnKeluar, btnRight1, btnRight2, btnLeft1, btnLeft2;

    private boolean mIsBoundTombol = false;
    private MusicServiceTombol mServTombol;

    private ServiceConnection SconTombol =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
            mServTombol = ((MusicServiceTombol.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name){
            mServTombol = null;
        }
    };

    void doBindServiceTombol(){
        bindService(new Intent(this,MusicServiceTombol.class), SconTombol, Context.BIND_AUTO_CREATE);
        mIsBoundTombol = true;
    }

    void doUnbindServiceTombol(){
        if(mIsBoundTombol){
            unbindService(SconTombol);
            mIsBoundTombol = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindServiceTombol();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_bantuan);
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);

        Animasi();
        GambarDialogBantuan();
        TombolDialogBantuan();
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    public void TombolDialogBantuan(){
        btnKeluar = (ImageButton) findViewById(R.id.tombolkeluar);
        btnKeluar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        btnRight1 = (ImageButton) findViewById(R.id.btnRight1);
        btnRight1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView bantuanDua = (ImageView) findViewById(R.id.bantuanDua);
                bantuanDua.setVisibility(View.VISIBLE);

                ImageView bantuanSatu = (ImageView) findViewById(R.id.bantuanSatu);
                bantuanSatu.setVisibility(View.GONE);

                ImageView btnLeft1 = (ImageView) findViewById(R.id.btnLeft1);
                btnLeft1.setVisibility(View.VISIBLE);

                ImageView btnRight1 = (ImageView) findViewById(R.id.btnRight1);
                btnRight1.setVisibility(View.GONE);

                ImageView btnRight2 = (ImageView) findViewById(R.id.btnRight2);
                btnRight2.setVisibility(View.VISIBLE);

                mServTombol.startMusicSwitch();
                arg0.startAnimation(animScale);
            }
        });

        btnRight2 = (ImageButton) findViewById(R.id.btnRight2);
        btnRight2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView bantuanTiga = (ImageView) findViewById(R.id.bantuanTiga);
                bantuanTiga.setVisibility(View.VISIBLE);

                ImageView bantuanDua = (ImageView) findViewById(R.id.bantuanDua);
                bantuanDua.setVisibility(View.GONE);

                ImageView btnLeft2 = (ImageView) findViewById(R.id.btnLeft2);
                btnLeft2.setVisibility(View.VISIBLE);

                ImageView btnLeft1 = (ImageView) findViewById(R.id.btnLeft1);
                btnLeft1.setVisibility(View.GONE);

                ImageView btnRight1 = (ImageView) findViewById(R.id.btnRight1);
                btnRight1.setVisibility(View.GONE);

                ImageView btnRight2 = (ImageView) findViewById(R.id.btnRight2);
                btnRight2.setVisibility(View.GONE);

                mServTombol.startMusicSwitch();
                arg0.startAnimation(animScale);
            }
        });

        btnLeft2 = (ImageButton) findViewById(R.id.btnLeft2);
        btnLeft2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView bantuanTiga = (ImageView) findViewById(R.id.bantuanTiga);
                bantuanTiga.setVisibility(View.GONE);

                ImageView bantuanDua = (ImageView) findViewById(R.id.bantuanDua);
                bantuanDua.setVisibility(View.VISIBLE);

                ImageView btnLeft2 = (ImageView) findViewById(R.id.btnLeft2);
                btnLeft2.setVisibility(View.GONE);

                ImageView btnLeft1 = (ImageView) findViewById(R.id.btnLeft1);
                btnLeft1.setVisibility(View.VISIBLE);

                ImageView btnRight1 = (ImageView) findViewById(R.id.btnRight1);
                btnRight1.setVisibility(View.GONE);

                ImageView btnRight2 = (ImageView) findViewById(R.id.btnRight2);
                btnRight2.setVisibility(View.VISIBLE);

                mServTombol.startMusicSwitch();
                arg0.startAnimation(animScale);
            }
        });

        btnLeft1 = (ImageButton) findViewById(R.id.btnLeft1);
        btnLeft1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ImageView bantuanDua = (ImageView) findViewById(R.id.bantuanDua);
                bantuanDua.setVisibility(View.GONE);

                ImageView bantuanSatu = (ImageView) findViewById(R.id.bantuanSatu);
                bantuanSatu.setVisibility(View.VISIBLE);

                ImageView btnLeft1 = (ImageView) findViewById(R.id.btnLeft1);
                btnLeft1.setVisibility(View.GONE);

                ImageView btnRight1 = (ImageView) findViewById(R.id.btnRight1);
                btnRight1.setVisibility(View.VISIBLE);

                mServTombol.startMusicSwitch();
                arg0.startAnimation(animScale);
            }
        });
    }

    public void GambarDialogBantuan(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView background_bantuan = (ImageView) findViewById(R.id.background_bantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann)
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        //content image bantuan
        ImageView bantuanSatu = (ImageView) findViewById(R.id.bantuanSatu);
        Glide   .with(this)
                .load(R.drawable.bantuansatu)
                .into(bantuanSatu);

        ImageView bantuanDua = (ImageView) findViewById(R.id.bantuanDua);
        Glide   .with(this)
                .load(R.drawable.bantuandua)
                .into(bantuanDua);

        ImageView bantuanTiga = (ImageView) findViewById(R.id.bantuanTiga);
        Glide   .with(this)
                .load(R.drawable.bantuantiga)
                .into(bantuanTiga);

        //content button
        ImageView tombolbatal = (ImageView) findViewById(R.id.tombolkeluar);
        Glide   .with(this)
                .load(R.drawable.tombolbataldua) //150
                .into(tombolbatal);

        ImageButton left1 = (ImageButton) findViewById(R.id.btnLeft1);
        Glide   .with(this)
                .load(R.drawable.left)
                .into(left1);

        ImageButton right1 = (ImageButton) findViewById(R.id.btnRight1);
        Glide   .with(this)
                .load(R.drawable.right)
                .into(right1);

        ImageButton left2 = (ImageButton) findViewById(R.id.btnLeft2);
        Glide   .with(this)
                .load(R.drawable.left)
                .into(left2);

        ImageButton right2 = (ImageButton) findViewById(R.id.btnRight2);
        Glide   .with(this)
                .load(R.drawable.right)
                .into(right2);
    }

    @Override
    protected void onDestroy() {
        doUnbindServiceTombol();
        super.onDestroy();
    }

    @Override
    public final void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,0);
    }
}

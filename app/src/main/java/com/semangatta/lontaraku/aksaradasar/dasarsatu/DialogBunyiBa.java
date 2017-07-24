package com.semangatta.lontaraku.aksaradasar.dasarsatu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.R;

import static com.semangatta.lontaraku.R.id.background_bantuan;

public class DialogBunyiBa extends Activity {

    private MediaPlayer mp;
    private Animation animScale;
    ImageView tombolMusic, bgHitam, bgDialog, tombolbatal;
    ImageButton btnKeluar, btnDengar;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, MusicPause = false;
    private MusicService mServ;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class), Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if(mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogd_ba);
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);

        Animasi();
        GambarDialogKa();
        TombolDialogKa();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mServ.pauseMusic();
                MusicPause=true;
            }
        }; registerReceiver(sendBroadcastReceiver , new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        mp = MediaPlayer.create(this, R.raw.button);
        mp.setVolume(0.1f, 0.1f);
    }

    public void GambarDialogKa(){
        tombolMusic = (ImageView) findViewById(R.id.tomboldengar);
        Glide   .with(this)
                .load(R.drawable.button_music_on) //200x200 GANTI
                .into(tombolMusic);

        bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        bgDialog = (ImageView) findViewById(R.id.bgDialog);
        Glide   .with(this)
                .load(R.drawable.dialogaksara) //ganti
                .placeholder(R.drawable.dialogaksara)
                .dontAnimate()
                .into(bgDialog);

        //content button
        tombolbatal = (ImageView) findViewById(R.id.tombolkeluar);
        Glide   .with(this)
                .load(R.drawable.tombolbataldua) //150
                .into(tombolbatal);
    }

    public void TombolDialogKa(){
        btnKeluar = (ImageButton) findViewById(R.id.tombolkeluar);
        btnDengar = (ImageButton) findViewById(R.id.tomboldengar);

        btnKeluar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
                mServ.outMusic();
                overridePendingTransition(0,0);
            }
        });

        btnDengar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mp.start();
                arg0.startAnimation(animScale);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        unregisterReceiver(sendBroadcastReceiver);
        super.onDestroy();
        mp.release();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public final void onResume() {
        if(MusicPause) {
            mServ.resumeMusic();
        }super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        mServ.outMusic();
        overridePendingTransition(0,0);
    }
}

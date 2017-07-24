package com.semangatta.lontaraku.bermainaksara.hurufsulit;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceBermain;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.bermainaksara.TingkatPermainanHuruf;

public class DialogKalahHS extends Activity {

    private Animation animScale;
    ImageButton btnHome, btnTingkat, btnUlang;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, mIsBoundTombol, mIsBoundBermain, MusicPause = false;
    private MusicService mServ;
    private MusicServiceTombol mServTombol;
    private MusicServiceBermain mServBermain;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
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

    private ServiceConnection SconBermain =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServBermain = ((MusicServiceBermain.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            //mServBermain = null;
        }
    };

    void doBindServiceBermain(){
        bindService(new Intent(this,MusicServiceBermain.class), SconBermain, Context.BIND_AUTO_CREATE);
        mIsBoundBermain = true;
    }

    void doUnbindServiceBermain() {
        if(mIsBoundBermain) {
            unbindService(SconBermain);
            mIsBoundBermain = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();
        doBindServiceTombol();
        doBindServiceBermain();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_kalah);
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);

        Animasi();
        GambarDialogKalahSatu();
        TombolDialogKalahSatu();

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
    }

    public void GambarDialogKalahSatu(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView background_bantuan = (ImageView) findViewById(R.id.background_bantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann) //Ganti
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        ImageView tombolHome = (ImageView) findViewById(R.id.btnHome);
        Glide   .with(this)
                .load(R.drawable.tombolinfo) //200x200 GANTI
                .into(tombolHome);

        ImageView tombolTingkat = (ImageView) findViewById(R.id.btnTingkat);
        Glide   .with(this)
                .load(R.drawable.tombolmusik)
                .into(tombolTingkat);

        ImageView tombolLanjut = (ImageView) findViewById(R.id.btnUlang);
        Glide   .with(this)
                .load(R.drawable.tombolmusik)
                .into(tombolLanjut);
    }

    public void TombolDialogKalahSatu(){
        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnTingkat = (ImageButton) findViewById(R.id.btnTingkat);
        btnUlang = (ImageButton) findViewById(R.id.btnUlang);

        btnHome.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServTombol.startMusic();
                mServ.startMusic();
                finish();
                arg0.startAnimation(animScale);
                overridePendingTransition(0, 0);
            }
        });

        btnTingkat.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServTombol.startMusic();
                mServ.startMusic();
                //mServBermain.stopMusic();
                Intent intent = new Intent(DialogKalahHS.this,TingkatPermainanHuruf.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                arg0.startAnimation(animScale);
                overridePendingTransition(0, 0);
            }
        });

        btnUlang.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DialogKalahHS.this,HurufSulit.class);
                startActivity(intent);
                finish();
                mServTombol.startMusic();
                mServBermain.outMusic();
                arg0.startAnimation(animScale);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        doUnbindServiceTombol();
        doUnbindServiceBermain();
        unregisterReceiver(sendBroadcastReceiver);
        super.onDestroy();
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
    }
}

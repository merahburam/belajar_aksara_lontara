package com.semangatta.lontaraku.bermainaksara;

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
import com.semangatta.lontaraku.BermainAksara;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;

public class DialogMenang extends Activity {

    private Animation animScale;
    ImageView bgHitam, background_bantuan, tombolTingkat;
    ImageButton btnKembali;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, mIsBoundTombol, MusicPause = false;
    private MusicService mServ;
    private MusicServiceTombol mServTombol;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();
        doBindServiceTombol();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_menang);
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
        bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        background_bantuan = (ImageView) findViewById(R.id.bgBantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann) //Ganti
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);
        tombolTingkat = (ImageView) findViewById(R.id.btnKembali);
        Glide   .with(this)
                .load(R.drawable.tombolmusik)
                .into(tombolTingkat);
    }

    public void TombolDialogKalahSatu(){
        btnKembali = (ImageButton) findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServTombol.startMusic();
                mServ.startMusic();
                Intent intent = new Intent(DialogMenang.this,BermainAksara.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                arg0.startAnimation(animScale);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        doUnbindServiceTombol();
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
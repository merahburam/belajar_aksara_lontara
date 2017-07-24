package com.semangatta.lontaraku.bermainaksara;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.BermainAksara;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceBermain;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.bermainaksara.katabiasa.KataBiasa;
import com.semangatta.lontaraku.bermainaksara.katamudah.KataMudah;
import com.semangatta.lontaraku.bermainaksara.katasulit.KataSulit;

public class TingkatPermainanKata extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnKembali, btnMudah, btnBiasa, btnSulit;
    ImageView background_image, titleaplikasi, tombolkembali, panel1, tombolMudah, tombolBiasa, tombolSulit;
    private BroadcastReceiver sendBroadcastReceiver;

    private boolean mIsBound, mIsBoundTombol, mIsBoundBermain, MusicPause = false;
    private MusicService mServ;
    private MusicServiceBermain mServBermain;
    private MusicServiceTombol mServTombol;

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

    private ServiceConnection SconBermain =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServBermain = ((MusicServiceBermain.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name) { //mServBermain = null;
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
        doBindServiceBermain();
        doBindServiceTombol();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingkat_permainan_kata);

        Animasi();
        GambarTingkatPermainanKata();
        TombolTingkatPermainanKata();

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

    public void GambarTingkatPermainanKata(){
        background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.mm)
                /*.placeholder(R.drawable.mm)
                .dontAnimate()*/
                .centerCrop()
                .into(background_image);

        ImageView pJudul = (ImageView) findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tblevel)
                .into(Judul);

        //button menu belajar aksara
        tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        tombolMudah= (ImageView) findViewById(R.id.tombolmudah);
        Glide   .with(this)
                .load(R.drawable.tombolmulai) //GANTI IMAGE
                .into(tombolMudah);

        tombolBiasa = (ImageView) findViewById(R.id.tombolbiasa);
        Glide   .with(this)
                .load(R.drawable.tombolmulai) //GANTI IMAGE
                .into(tombolBiasa);

        tombolSulit = (ImageView) findViewById(R.id.tombolsulit);
        Glide   .with(this)
                .load(R.drawable.tombolmulai) //GANTI IMAGE
                .into(tombolSulit);
    }

    public void TombolTingkatPermainanKata(){
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnMudah = (ImageButton) findViewById(R.id.tombolmudah);
        btnBiasa = (ImageButton) findViewById(R.id.tombolbiasa);
        btnSulit = (ImageButton) findViewById(R.id.tombolsulit);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(TingkatPermainanKata.this,BermainAksara.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                arg0.startAnimation(animScale);
                finish();
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        btnMudah.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(TingkatPermainanKata.this, KataMudah.class);
                startActivity(intent);
                doBindService();
                if(mServ.isPlaying()) {
                    mServ.pauseMusic();
                    Intent music = new Intent(TingkatPermainanKata.this, MusicServiceBermain.class);
                    startService(music);
                } else {
                    mServ.stopMusic();
                    mServBermain.pauseMusic();
                    mServ=null;
                }
                finish();
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        btnBiasa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(TingkatPermainanKata.this, KataBiasa.class);
                startActivity(intent);
                doBindService();
                if(mServ.isPlaying()) {
                    mServ.pauseMusic();
                    Intent music = new Intent(TingkatPermainanKata.this, MusicServiceBermain.class);
                    startService(music);
                } else {
                    mServ.stopMusic();
                    mServBermain.pauseMusic();
                    mServ=null;
                }
                finish();
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        btnSulit.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(TingkatPermainanKata.this, KataSulit.class);
                startActivity(intent);
                doBindService();
                if(mServ.isPlaying()) {
                    mServ.pauseMusic();
                    Intent music = new Intent(TingkatPermainanKata.this, MusicServiceBermain.class);
                    startService(music);
                } else {
                    mServ.stopMusic();
                    mServBermain.pauseMusic();
                    mServ=null;
                }
                finish();
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        doUnbindServiceBermain();
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
        } super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TingkatPermainanKata.this,BermainAksara.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}

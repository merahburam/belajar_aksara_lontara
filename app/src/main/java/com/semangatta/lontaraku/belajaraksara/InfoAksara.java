package com.semangatta.lontaraku.belajaraksara;

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
import com.semangatta.lontaraku.BelajarAksara;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.infopenggunaan.InfoAksaraPenggunaan;
import com.semangatta.lontaraku.infosejarah.InfoAksaraSejarah;

public class InfoAksara extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnKembali, btnSejarah, btnPenggunaan;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, mIsBoundTombol, MusicPause = false;
    private MusicService mServ;
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

    void doUnbindService(){
        if(mIsBound){
            unbindService(Scon);
            mIsBound = false;
        }
    }

    private ServiceConnection SconTombol =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
            mServTombol = ((MusicServiceTombol.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name){
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
        setContentView(R.layout.activity_info_aksara);

        Animasi();
        GambarInfoAksara();
        TombolInfoAksara();

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

    public void GambarInfoAksara(){
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.mm) //Ganti
                .centerCrop()
                .into(background_image);

        ImageView pJudul = (ImageView) findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tbinfo)
                .into(Judul);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        ImageView tombolAksaraDasar = (ImageView) findViewById(R.id.tombolsejarah);
        Glide   .with(this)
                .load(R.drawable.tombolmulai) //GANTI IMAGE
                .into(tombolAksaraDasar);

        ImageView tombolAksaraSuara = (ImageView) findViewById(R.id.tombolpenggunaan);
        Glide   .with(this)
                .load(R.drawable.tombolmulai) //GANTI IMAGE
                .into(tombolAksaraSuara);

    }

    public void TombolInfoAksara(){
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnSejarah = (ImageButton) findViewById(R.id.tombolsejarah);
        btnPenggunaan = (ImageButton) findViewById(R.id.tombolpenggunaan);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InfoAksara.this,BelajarAksara.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                arg0.startAnimation(animScale);
                finish();
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        btnSejarah.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InfoAksara.this,InfoAksaraSejarah.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        btnPenggunaan.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InfoAksara.this,InfoAksaraPenggunaan.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
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
        Intent intent = new Intent(InfoAksara.this,BelajarAksara.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}

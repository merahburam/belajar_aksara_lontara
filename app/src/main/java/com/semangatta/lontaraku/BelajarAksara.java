package com.semangatta.lontaraku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.belajaraksara.DaftarHuruf;
import com.semangatta.lontaraku.belajaraksara.InfoAksara;

import static com.semangatta.lontaraku.R.drawable.panel;
import static com.semangatta.lontaraku.R.id.background_image;
import static com.semangatta.lontaraku.R.id.tombolinfoaksara;

public class BelajarAksara extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnkembali, btninfo, btndaftar;
    ImageView buttoninfo, buttondaftar;
    RelativeLayout infoAksara, daftarAksara;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, mIsBoundTombol, MusicPause = false;
    private MusicService mServ;
    private MusicServiceTombol mServTombol;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
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
        setContentView(R.layout.activity_belajar_aksara);

        Animasi();
        GambarBelajarAksara();
        TombolBelajarAksara();
        startAnimationButton();

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

    public void TombolBelajarAksara() {
        btnkembali = (ImageButton) findViewById(R.id.tombolkembali);
       /* btninfo = (ImageButton) findViewById(tombolinfoaksara);
        btndaftar = (ImageButton) findViewById(R.id.tomboldaftarhuruf);
       */
        infoAksara = (RelativeLayout) findViewById(R.id.infoaksara);
        daftarAksara = (RelativeLayout) findViewById(R.id.daftaraksara);

        btnkembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                finish();
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });

        infoAksara.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BelajarAksara.this, InfoAksara.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
                finish();
            }
        });

        daftarAksara.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BelajarAksara.this, DaftarHuruf.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private void startAnimationButton() {
        buttoninfo = (ImageView) findViewById(R.id.pjinfo);
        ObjectAnimator scaleYI = ObjectAnimator.ofFloat(buttoninfo, "scaleY", 0.8f);
        ObjectAnimator scaleYBackI = ObjectAnimator.ofFloat(buttoninfo, "scaleY", 1f);
        scaleYI.setDuration(200);
        scaleYBackI.setDuration(500);
        scaleYBackI.setInterpolator(new BounceInterpolator());

        buttondaftar = (ImageView) findViewById(R.id.pjaksara);
        ObjectAnimator scaleYD = ObjectAnimator.ofFloat(buttondaftar, "scaleY", 0.8f);
        ObjectAnimator scaleYBackD = ObjectAnimator.ofFloat(buttondaftar, "scaleY", 1f);
        scaleYD.setDuration(200);
        scaleYBackD.setDuration(500);
        scaleYBackD.setInterpolator(new BounceInterpolator());

        final AnimatorSet animatorI = new AnimatorSet();
        animatorI.setStartDelay(3000);
        animatorI.playSequentially(scaleYI, scaleYBackI);
        animatorI.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorI.start();
            }
        }); animatorI.start();

        final AnimatorSet animatorD = new AnimatorSet();
        animatorD.setStartDelay(2000);
        animatorD.playSequentially(scaleYD, scaleYBackD);
        animatorD.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorD.start();
            }
        }); animatorD.start();
    }

       /* btninfo.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BelajarAksara.this,InfoAksara.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btndaftar.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(BelajarAksara.this,DaftarHuruf.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
                finish();
            }
        });*/

    public void GambarBelajarAksara(){
        ImageView bgImage = (ImageView) findViewById(R.id.bgBelajar);
        Glide   .with(this)
                .load(R.drawable.mm)
                //.placeholder(R.drawable.menuutamadua)
                //.dontAnimate()
                .centerCrop()
                .into(bgImage);

        ImageView pJudul = (ImageView) findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tbaksara)
                .into(Judul);

        ImageView pinfo = (ImageView) findViewById(R.id.panelinfo);
        Glide   .with(this)
                .load(R.drawable.pbelajar)
                .into(pinfo);

        ImageView kertas = (ImageView) findViewById(R.id.kertas);
        Glide   .with(this)
                .load(R.drawable.iakertas)
                .into(kertas);

        ImageView aksara = (ImageView) findViewById(R.id.aksara);
        Glide   .with(this)
                .load(R.drawable.iaaksara)
                .into(aksara);

        ImageView panelJudulAksara = (ImageView) findViewById(R.id.pjaksara);
        Glide   .with(this)
                .load(R.drawable.bbutton)
                .into(panelJudulAksara);

        ImageView panelJudulInfo = (ImageView) findViewById(R.id.pjinfo);
        Glide   .with(this)
                .load(R.drawable.bbutton)
                .into(panelJudulInfo);

        ImageView pdaftar = (ImageView) findViewById(R.id.paneldaftar);
        Glide   .with(this)
                .load(R.drawable.pbelajar)
                .into(pdaftar);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        ImageView tombolInfo = (ImageView) findViewById(R.id.tombolinfo);
        Glide   .with(this)
                .load(R.drawable.infoaksara)
                .into(tombolInfo);

        ImageView tombolDaftar = (ImageView) findViewById(R.id.tomboldaftar);
        Glide   .with(this)
                .load(R.drawable.daftarhuruf)
                .into(tombolDaftar);

        /*ImageView tombolinfoaksara = (ImageView) findViewById(R.id.tombolinfoaksara);
        Glide   .with(this)
                .load(R.drawable.tombolmulai)
                .into(tombolinfoaksara);

        ImageView tomboldaftarhuruf = (ImageView) findViewById(R.id.tomboldaftarhuruf);
        Glide   .with(this)
                .load(R.drawable.tombolmulai)
                .into(tomboldaftarhuruf);*/
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
        } super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }
}

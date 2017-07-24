package com.semangatta.lontaraku.aksarasuara;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
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

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.belajaraksara.DaftarHuruf;

public class AksaraSuara extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnKembali, btnPenjelasan, btnKA, btnKI, btnKU, btnKE, btnKO, btnKNE;

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

    void doUnbindService() {
        if(mIsBound) {
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
        setContentView(R.layout.activity_aksara_suara);

        Animasi();
        GambarAksaraSuara();
        TombolAksaraSuara();
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

    public void GambarAksaraSuara(){
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.mm)
                .centerCrop()
                .into(background_image);

        ImageView pJudul = (ImageView) findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tbsuara)
                .into(Judul);

        ImageView panel = (ImageView) findViewById(R.id.Panel);
        Glide   .with(this)
                .load(R.drawable.panel) //Ganti
                .centerCrop()
                .into(panel);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        ImageView tombolpenjelasan = (ImageView) findViewById(R.id.tombolpenjelasan);
        Glide   .with(this)
                .load(R.drawable.tinfo) //GANTI
                .into(tombolpenjelasan);

        //tombol aksara suara
        ImageView tombolKA = (ImageView) findViewById(R.id.KA);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKA);
        ImageView tombolKI = (ImageView) findViewById(R.id.KI);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKI);
        ImageView tombolKU = (ImageView) findViewById(R.id.KU);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKU);
        ImageView tombolKE = (ImageView) findViewById(R.id.KE);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKE);
        ImageView tombolKO = (ImageView) findViewById(R.id.KO);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKO);
        ImageView tombolKNE = (ImageView) findViewById(R.id.KNE);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKNE);
    }

    private void startAnimationButton() {
        btnPenjelasan = (ImageButton) findViewById(R.id.tombolpenjelasan);
        ObjectAnimator scaleYBel = ObjectAnimator.ofFloat(btnPenjelasan, "scaleY", 0.8f);
        ObjectAnimator scaleYBackBel = ObjectAnimator.ofFloat(btnPenjelasan, "scaleY", 1f);
        scaleYBel.setDuration(200);
        scaleYBackBel.setDuration(500);
        scaleYBackBel.setInterpolator(new BounceInterpolator());

        final AnimatorSet animatorBel = new AnimatorSet();
        animatorBel.setStartDelay(3000);
        animatorBel.playSequentially(scaleYBel, scaleYBackBel);
        animatorBel.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorBel.start();
            }
        }); animatorBel.start();
    }

    public void TombolAksaraSuara(){
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnPenjelasan = (ImageButton) findViewById(R.id.tombolpenjelasan);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DaftarHuruf.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnPenjelasan.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogPenjelasanSuara.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
            }
        });

        //tombol aksara bunyi
        btnKA = (ImageButton) findViewById(R.id.KA);
        btnKI = (ImageButton) findViewById(R.id.KI);
        btnKU = (ImageButton) findViewById(R.id.KU);
        btnKE = (ImageButton) findViewById(R.id.KE);
        btnKO = (ImageButton) findViewById(R.id.KO);
        btnKNE = (ImageButton) findViewById(R.id.KNE);

        btnKA.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKaa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnKI.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKi.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnKU.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKu.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnKE.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKe.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnKO.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKo.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnKNE.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraSuara.this,DialogBunyiKne.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
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
        } super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AksaraSuara.this,DaftarHuruf.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}

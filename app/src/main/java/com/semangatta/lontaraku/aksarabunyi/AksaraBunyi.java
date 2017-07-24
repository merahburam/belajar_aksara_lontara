package com.semangatta.lontaraku.aksarabunyi;

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
import com.semangatta.lontaraku.belajaraksara.DaftarHuruf;
import com.semangatta.lontaraku.R;

public class AksaraBunyi extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnKembali, btnPenjelasan, btnA, btnI, btnU, btnE, btnO, btnEE, btnTitik;

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
        setContentView(R.layout.activity_aksara_bunyi);

        Animasi();
        GambarAksaraBunyi();
        TombolAksaraBunyi();
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

    public void GambarAksaraBunyi(){
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
                .load(R.drawable.tbbunyi)
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

        //tombol aksara bunyi
        ImageView tombolA = (ImageView) findViewById(R.id.a);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolA);
        ImageView tombolI = (ImageView) findViewById(R.id.i);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolI);
        ImageView tombolU = (ImageView) findViewById(R.id.u);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolU);
        ImageView tombolE = (ImageView) findViewById(R.id.e);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolE);
        ImageView tombolO = (ImageView) findViewById(R.id.o);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolO);
        ImageView tombolEE = (ImageView) findViewById(R.id.ee);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolEE);
        ImageView tombolTitik = (ImageView) findViewById(R.id.titik);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolTitik);
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

    public void TombolAksaraBunyi(){
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnPenjelasan = (ImageButton) findViewById(R.id.tombolpenjelasan);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DaftarHuruf.class);
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
                Intent intent = new Intent(AksaraBunyi.this,DialogPenjelasanBunyi.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
            }
        });

        //tombol aksara bunyi
        btnA = (ImageButton) findViewById(R.id.a);
        btnI = (ImageButton) findViewById(R.id.i);
        btnU = (ImageButton) findViewById(R.id.u);
        btnE = (ImageButton) findViewById(R.id.e);
        btnO = (ImageButton) findViewById(R.id.o);
        btnEE = (ImageButton) findViewById(R.id.ee);
        btnTitik = (ImageButton) findViewById(R.id.titik);

        btnA.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiA.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnI.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiI.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnU.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiU.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnE.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiE.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnO.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiO.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnEE.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiEe.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnTitik.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraBunyi.this,DialogBunyiTitik.class);
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
        }super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AksaraBunyi.this,DaftarHuruf.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
}

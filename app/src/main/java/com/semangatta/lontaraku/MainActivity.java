package com.semangatta.lontaraku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Animation animScale, animSlideUp, animSlideDown, animSlideDownFaster;
    TextView tv1,tv2;
    ImageButton btnBermain, btnBelajar, btnBantuan, btnPengaturan, btnInfo,
                btnMusic, btnEfek, Music, Efek, Info;
    RelativeLayout btn;
    private BroadcastReceiver sendBroadcastReceiver;

    private boolean mIsBound,mIsBoundTombol, MusicPause, flagPengaturan, flagMusic, flagEfek = false;
    private MusicService mServ;
    private MusicServiceTombol mServTombol;

    private ServiceConnection SconTombol =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
            mServTombol = ((MusicServiceTombol.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            mServTombol = null;
        }
    };

    public MainActivity() {
    }

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
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        final Intent music = new Intent(this,MusicService.class);
        startService(music);

        Intent tombol = new Intent(MainActivity.this,MusicServiceTombol.class);
        startService(tombol);

        startAnimationButton();

        doBindService();
        doBindServiceTombol();

        tv1=(TextView)findViewById(R.id.tekstombolbelajar);
        Typeface face1= Typeface.createFromAsset(getAssets(), "fonts/grobold.ttf");
        tv1.setTypeface(face1);

        tv2=(TextView)findViewById(R.id.tekstombolbermain);
        Typeface face2= Typeface.createFromAsset(getAssets(), "fonts/grobold.ttf");
        tv2.setTypeface(face2);

        Animasi();
        GambarMenuUtama();
        TombolMenuUtama();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               if(mServ.isPlaying()) {
                    mServ.pauseMusic();
                    MusicPause = true;
                } else {
                   mServ.stopMusic();
                   MusicPause = false;
               }}
        }; registerReceiver(sendBroadcastReceiver , new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

       registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mServ.pauseMusic();
                MusicPause=true;
            }
        }, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    public void GambarMenuUtama(){
        ImageView background = (ImageView) findViewById(R.id.background);
        Glide   .with(this)
                .load(R.drawable.mm)
                .placeholder(R.drawable.mm) //GANTI
                .dontAnimate()
                .centerCrop()
                .into(background);

        //button menu utama
        ImageView tombolbantuan = (ImageView) findViewById(R.id.tombolbantuan);
        Glide   .with(this)
                .load(R.drawable.tbantuan)
                .into(tombolbantuan);

        ImageView title = (ImageView) findViewById(R.id.title);
        Glide   .with(this)
                .load(R.drawable.title)
                .into(title);

        ImageView tombolpengaturan = (ImageView) findViewById(R.id.tombolpengaturan);
        Glide   .with(this)
                .load(R.drawable.tpengaturan)
                .into(tombolpengaturan);

        ImageView tombolplay = (ImageView) findViewById(R.id.tombolbelajar);
        Glide   .with(this)
                .load(R.drawable.tombolmulai)
                .into(tombolplay);

        ImageView tombolbermain = (ImageView) findViewById(R.id.tombolbermain);
        Glide   .with(this)
                .load(R.drawable.tombolmulai)
                .into(tombolbermain);

        //Pengaturan
        ImageView panelpengaturan = (ImageView) findViewById(R.id.panelbiru);
        Glide   .with(this)
                .load(R.drawable.ppengaturan)
                .into(panelpengaturan);

        ImageView btnMusic = (ImageView) findViewById(R.id.tombolMusic);
        Glide   .with(this)
                .load(R.drawable.music)
                .into(btnMusic);

        ImageView btnEfek = (ImageView) findViewById(R.id.tombolEfek);
        Glide   .with(this)
                .load(R.drawable.efek)
                .into(btnEfek);

        ImageView btnInfo = (ImageView) findViewById(R.id.tombolInfo);
        Glide   .with(this)
                .load(R.drawable.info)
                .into(btnInfo);

        ImageView sudut = (ImageView) findViewById(R.id.sudut);
        Glide   .with(this)
                .load(R.drawable.musudut)
                .into(sudut);
    }

    private void startAnimationButton() {
        btnBermain = (ImageButton) findViewById(R.id.tombolbermain);
        ObjectAnimator scaleYBel = ObjectAnimator.ofFloat(btnBermain, "scaleY", 0.8f);
        ObjectAnimator scaleYBackBel = ObjectAnimator.ofFloat(btnBermain, "scaleY", 1f);
        scaleYBel.setDuration(200);
        scaleYBackBel.setDuration(500);
        scaleYBackBel.setInterpolator(new BounceInterpolator());

        btnBelajar = (ImageButton) findViewById(R.id.tombolbelajar);
        ObjectAnimator scaleYBer = ObjectAnimator.ofFloat(btnBelajar, "scaleY", 0.8f);
        ObjectAnimator scaleYBackBer = ObjectAnimator.ofFloat(btnBelajar, "scaleY", 1f);
        scaleYBer.setDuration(200);
        scaleYBackBer.setDuration(500);
        scaleYBackBer.setInterpolator(new BounceInterpolator());

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

        final AnimatorSet animatorBer = new AnimatorSet();
        animatorBer.setStartDelay(2000);
        animatorBer.playSequentially(scaleYBer, scaleYBackBer);
        animatorBer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorBer.start();
            }
        }); animatorBer.start();
    }

    public void  Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        animSlideUp = AnimationUtils.loadAnimation(this, R.anim.sliding_up);
        animSlideDown = AnimationUtils.loadAnimation(this, R.anim.sliding_down);
        animSlideDownFaster = AnimationUtils.loadAnimation(this, R.anim.sliding_down_faster);
    }

    public void TombolMenuUtama(){
        btnBantuan = (ImageButton) findViewById(R.id.tombolbantuan);
        btnPengaturan = (ImageButton) findViewById(R.id.tombolpengaturan);
        btnBermain = (ImageButton) findViewById(R.id.tombolbermain);
        btnBelajar = (ImageButton) findViewById(R.id.tombolbelajar);
        btnMusic = (ImageButton) findViewById(R.id.tombolMusic);
        btnEfek = (ImageButton) findViewById(R.id.tombolEfek);
        btnInfo = (ImageButton) findViewById(R.id.tombolInfo);

        btnBantuan.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServTombol.startMusic();
                Intent intent = new Intent(MainActivity.this,DialogBantuan.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                if(!flagPengaturan) {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.setVisibility(View.GONE);
                } else {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.startAnimation(animSlideDown);
                    btn.setVisibility(View.GONE);

                    Music = (ImageButton) findViewById(R.id.tombolMusic);
                    Music.setVisibility(View.GONE);
                    Efek = (ImageButton) findViewById(R.id.tombolEfek);
                    Efek.setVisibility(View.GONE);
                    Info = (ImageButton) findViewById(R.id.tombolInfo);
                    Info.setVisibility(View.GONE);
                    flagPengaturan = false;
                }
            }
        });

        btnInfo.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,DialogInfoPengembang.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
            }
        });

        btnMusic.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animScale);
                mServTombol.startMusic();
                switch (v.getId()) {
                    case R.id.tombolMusic : if(!flagEfek){
                        mServ.pauseMusic();
                        MusicPause=false;
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cmusic );
                        btnMusic.setImageBitmap(bm);
                        flagEfek = true;
                    }else{
                        if(mServ!=null) {
                            Intent music = new Intent(MainActivity.this,MusicService.class);
                            startService(music);
                        } else {
                            mServ.pauseMusic();
                        }
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.music );
                        btnMusic.setImageBitmap(bm);
                        flagEfek = false;
                    }}
            }
        });

        btnEfek.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animScale);
                switch (v.getId()) {

                    case R.id.tombolEfek : if(!flagMusic){
                        mServTombol.stopMusic();
                        mServTombol.stopMusicSwitch();
                        mServTombol.stopMusicWrong();
                        mServTombol.stopMusicCorrect();
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cefek );
                        btnEfek.setImageBitmap(bm);
                        flagMusic = true;
                    }else{
                        mServTombol.playMusic();
                        mServTombol.playMusicSwitch();
                        mServTombol.playMusicWrong();
                        mServTombol.playMusicCorrect();
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.efek );
                        btnEfek.setImageBitmap(bm);
                        flagMusic = false;
                    }}}
        });

       btnPengaturan.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
               switch (v.getId()) {

                   case R.id.tombolpengaturan : if(!flagPengaturan){

                       v.startAnimation(animScale);
                       btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                       btn.setVisibility(View.VISIBLE);
                       btn.startAnimation(animSlideUp);
                       Music = (ImageButton) findViewById(R.id.tombolMusic);
                       Music.setVisibility(View.VISIBLE);
                       Efek = (ImageButton) findViewById(R.id.tombolEfek);
                       Efek.setVisibility(View.VISIBLE);
                       Info = (ImageButton) findViewById(R.id.tombolInfo);
                       Info.setVisibility(View.VISIBLE);
                       mServTombol.startMusic();
                       flagPengaturan = true;

                   }else{

                       v.startAnimation(animScale);
                       btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                       btn.startAnimation(animSlideDown);
                       Music = (ImageButton) findViewById(R.id.tombolMusic);
                       Music.setVisibility(View.GONE);
                       Efek = (ImageButton) findViewById(R.id.tombolEfek);
                       Efek.setVisibility(View.GONE);
                       Info = (ImageButton) findViewById(R.id.tombolInfo);
                       Info.setVisibility(View.GONE);
                       mServTombol.startMusic();
                       flagPengaturan = false;
                   }}
            }
        });

        btnBelajar.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,BelajarAksara.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);

                if(!flagPengaturan) {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.setVisibility(View.GONE);
                } else {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.startAnimation(animSlideDownFaster);
                    btn.setVisibility(View.GONE);

                    Music = (ImageButton) findViewById(R.id.tombolMusic);
                    Music.setVisibility(View.GONE);
                    Efek = (ImageButton) findViewById(R.id.tombolEfek);
                    Efek.setVisibility(View.GONE);
                    Info = (ImageButton) findViewById(R.id.tombolInfo);
                    Info.setVisibility(View.GONE);
                    flagPengaturan = false;
                }
            }
        });

        btnBermain.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,BermainAksara.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                overridePendingTransition(0, 0);

                if(!flagPengaturan) {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.setVisibility(View.GONE);
                } else {
                    btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
                    btn.startAnimation(animSlideDownFaster);
                    btn.setVisibility(View.GONE);

                    Music = (ImageButton) findViewById(R.id.tombolMusic);
                    Music.setVisibility(View.GONE);
                    Efek = (ImageButton) findViewById(R.id.tombolEfek);
                    Efek.setVisibility(View.GONE);
                    Info = (ImageButton) findViewById(R.id.tombolInfo);
                    Info.setVisibility(View.GONE);
                    flagPengaturan = false;
                }
            }
        });
    }

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

    @Override
    public final void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mServ.onDestroy();
        doUnbindService();
        doUnbindServiceTombol();
        unregisterReceiver(sendBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public final void onResume() {
        if(MusicPause) {
            mServ.resumeMusic();
        } super.onResume();
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        if(!flagPengaturan) {
            btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
            btn.setVisibility(View.GONE);
        } else {
            btn = (RelativeLayout) findViewById(R.id.panelPengaturan);
            btn.startAnimation(animSlideDown);
            btn.setVisibility(View.GONE);

            Music = (ImageButton) findViewById(R.id.tombolMusic);
            Music.setVisibility(View.GONE);
            Efek = (ImageButton) findViewById(R.id.tombolEfek);
            Efek.setVisibility(View.GONE);
            Info = (ImageButton) findViewById(R.id.tombolInfo);
            Info.setVisibility(View.GONE);
            flagPengaturan = false;
        }
    }

    public void backButtonHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setIcon(R.drawable.tombolbantuan);
        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}





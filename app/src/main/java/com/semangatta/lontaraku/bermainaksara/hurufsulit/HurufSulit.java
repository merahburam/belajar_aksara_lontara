package com.semangatta.lontaraku.bermainaksara.hurufsulit;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.BermainAksara;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceBermain;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;

import java.util.ArrayList;
import java.util.Random;

public class HurufSulit extends AppCompatActivity {

    private Animation animScale;
    ImageButton btnOK, btnYa, btnTidak;
    ImageView bgHitam, background_bantuan, tombolHome, tombolLanjut;
    RelativeLayout LNotice, DKeluar;
    private BroadcastReceiver sendBroadcastReceiver;

    private boolean mIsBound, mIsBoundTombol, mIsBoundBermain, MusicPause = false;
    private MusicService mServ;
    private MusicServiceBermain mServBermain;
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
        setContentView(R.layout.activity_huruf_sulit);

        Animasi();
        GambarAksaraDasar();
        TombolDialogKeluar();
        PopupPemberitahuan();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mServBermain.pauseMusic();
                MusicPause=true;
            }
        }; registerReceiver(sendBroadcastReceiver , new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    public void PopupPemberitahuan(){
        Runnable r = new Runnable(){
            public void run(){
                LNotice = (RelativeLayout) findViewById(R.id.Pemberitahuan);
                LNotice.startAnimation(animScale);
                LNotice.setVisibility(View.VISIBLE);

                btnOK = (ImageButton) findViewById(R.id.OK);
                btnOK.setOnClickListener(new ImageButton.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        LNotice = (RelativeLayout) findViewById(R.id.Pemberitahuan);
                        LNotice.setVisibility(View.GONE);

                        ArrayList<Class> activityList = new ArrayList<>();
                        activityList.add(HSSoal1.class);
                        activityList.add(HSSoal2.class);
                        activityList.add(HSSoal3.class);
                        activityList.add(HSSoal4.class);
                        activityList.add(HSSoal5.class);
                        activityList.add(HSSoal6.class);
                        activityList.add(HSSoal7.class);
                        activityList.add(HSSoal8.class);
                        activityList.add(HSSoal9.class);
                        activityList.add(HSSoal10.class);

                        Random generator = new Random();
                        int number = generator.nextInt(10) + 1;
                        Class activity = null;

                        switch(number) {
                            case 1: activity = HSSoal1.class;
                                    activityList.remove(HSSoal1.class);
                                    break;
                            case 2: activity = HSSoal2.class;
                                    activityList.remove(HSSoal2.class);
                                    break;
                            case 3: activity = HSSoal3.class;
                                    activityList.remove(HSSoal3.class);
                                    break;
                            case 4: activity = HSSoal4.class;
                                    activityList.remove(HSSoal4.class);
                                    break;
                            case 5: activity = HSSoal5.class;
                                    activityList.remove(HSSoal5.class);
                                    break;
                            case 6: activity = HSSoal6.class;
                                    activityList.remove(HSSoal6.class);
                                    break;
                            case 7: activity = HSSoal7.class;
                                    activityList.remove(HSSoal7.class);
                                    break;
                            case 8: activity = HSSoal8.class;
                                    activityList.remove(HSSoal8.class);
                                    break;
                            case 9: activity = HSSoal9.class;
                                    activityList.remove(HSSoal9.class);
                                    break;
                            default:
                                    activity = HSSoal10.class;
                                    activityList.remove(HSSoal10.class);
                                    break;
                        }   Intent intent = new Intent(getBaseContext(), activity);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("ACTIVITY_LIST", activityList);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(0,0);
                    }
                });
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 300);
    }

    public void GambarDialogKeluar(){
        bgHitam = (ImageView) findViewById(R.id.bgHitamDKeluar);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        background_bantuan = (ImageView) findViewById(R.id.bgDKeluar);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann) //Ganti
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        tombolHome = (ImageView) findViewById(R.id.btnYa);
        Glide   .with(this)
                .load(R.drawable.tombolinfo) //200x200 GANTI
                .into(tombolHome);

        tombolLanjut = (ImageView) findViewById(R.id.btnTidak);
        Glide   .with(this)
                .load(R.drawable.tombolmusik)
                .into(tombolLanjut);
    }

    public void TombolDialogKeluar(){
        btnYa = (ImageButton) findViewById(R.id.btnYa);
        btnTidak = (ImageButton) findViewById(R.id.btnTidak);

        btnYa.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServ.startMusic();
                mServBermain.stopMusic();
                mServTombol.startMusic();
                Intent intent = new Intent(HurufSulit.this,BermainAksara.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnTidak.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                RelativeLayout DKeluar = (RelativeLayout) findViewById(R.id.DialogKeluar);
                DKeluar.setVisibility(View.GONE);
                mServTombol.startMusic();
            }
        });
    }

    public void GambarAksaraDasar() {
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.menuutamadua) //ganti
                .centerCrop()
                .into(background_image);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        //Pemberitahuan
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView bgPemberitahuan = (ImageView) findViewById(R.id.bgPemberitahuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann)
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(bgPemberitahuan);

        ImageView tombolOK = (ImageView) findViewById(R.id.OK);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolOK);

        GambarDialogKeluar();
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
            mServBermain.resumeMusic();
            mServ.resumeMusic2();
        } super.onResume();
    }

    @Override
    public void onBackPressed() {
        DKeluar = (RelativeLayout) findViewById(R.id.DialogKeluar);
        DKeluar.setVisibility(View.VISIBLE);
    }
}
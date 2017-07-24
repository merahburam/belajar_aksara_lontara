package com.semangatta.lontaraku;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DialogInfoPengembang extends Activity {

    TextView tv1,tv2;
    ImageButton btnKeluar;

    private boolean mIsBound = false;
    private MusicService mServ;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name) {
            //mServ = null;
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
        setContentView(R.layout.activity_dialog_info_pengembang);
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);

        GambarDialogInfo();
        TombolDialogInfo();
        TextDialogInfo();
    }

    public void TextDialogInfo(){
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/grobold.ttf");
        tv1=(TextView)findViewById(R.id.tentangAplikasi);
        tv2=(TextView)findViewById(R.id.textTentang);
        tv1.setTypeface(face);
        tv2.setTypeface(face);
    }

    public void GambarDialogInfo(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);
        ImageView background_bantuan = (ImageView) findViewById(R.id.bgPengaturan);
        Glide   .with(this)
                .load(R.drawable.dialogpengaturann)
                .placeholder(R.drawable.dialogpengaturann)
                .dontAnimate()
                .into(background_bantuan);
        ImageView tombolbatal = (ImageView) findViewById(R.id.tombolkeluar);
        Glide   .with(this)
                .load(R.drawable.tombolbataldua) //150x150
                .into(tombolbatal);
    }

    public void TombolDialogInfo(){
        btnKeluar = (ImageButton) findViewById(R.id.tombolkeluar);
        btnKeluar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,0);
    }
}

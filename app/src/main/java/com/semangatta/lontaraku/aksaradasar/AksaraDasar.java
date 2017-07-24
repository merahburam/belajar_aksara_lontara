package com.semangatta.lontaraku.aksaradasar;

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
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.belajaraksara.DaftarHuruf;
import com.semangatta.lontaraku.R;

public class AksaraDasar extends FragmentActivity {

    private Animation animScale;
    ImageButton btnKembali, btnPenjelasan, btnOK;
    RelativeLayout LNotice;

    private BroadcastReceiver sendBroadcastReceiver;
    private boolean mIsBound, mIsBoundTombol, MusicPause = false;

    private static final int NUM_PAGES = 2;
    public ViewPager mPager;
    public PagerAdapter mPagerAdapter;

    private MusicService mServ;
    private MusicServiceTombol mServTombol;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder
                binder) {
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
        setContentView(R.layout.activity_aksara_dasar);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        Animasi();
        GambarAksaraDasasr();
        TombolAksaraDasar();
        PopupPemberitahuan();
        startAnimationButton();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mServ.pauseMusic();
                MusicPause=true;
            }
        }; registerReceiver(sendBroadcastReceiver , new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    public void PopupPemberitahuan(){
        Runnable r = new Runnable(){
            public void run(){
                LNotice = (RelativeLayout) findViewById(R.id.Pemberitahuan);
                LNotice.startAnimation(animScale);
                LNotice.setVisibility(View.VISIBLE);

                btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
                btnPenjelasan = (ImageButton) findViewById(R.id.tombolpenjelasan);

                btnKembali.setClickable(false);
                btnPenjelasan.setClickable(false);

                btnOK = (ImageButton) findViewById(R.id.OK);
                btnOK.setOnClickListener(new ImageButton.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        LNotice = (RelativeLayout) findViewById(R.id.Pemberitahuan);
                        LNotice.setVisibility(View.GONE);
                        ViewPager LPager = (ViewPager) findViewById(R.id.pager);
                        LPager.setVisibility(View.VISIBLE);
                        btnKembali.setClickable(true);
                        btnPenjelasan.setClickable(true);
                    }
                });
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 200);
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    public void GambarAksaraDasasr() {
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.mm)
                .centerCrop()
                .into(background_image);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        ImageView tombolpenjelasan = (ImageView) findViewById(R.id.tombolpenjelasan);
        Glide   .with(this)
                .load(R.drawable.tinfo) //GANTI
                .into(tombolpenjelasan);

        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView background_bantuan = (ImageView) findViewById(R.id.background_bantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann)
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        ImageView tombolOK = (ImageView) findViewById(R.id.OK);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolOK);
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

    public void TombolAksaraDasar() {
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnPenjelasan = (ImageButton) findViewById(R.id.tombolpenjelasan);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AksaraDasar.this,DaftarHuruf.class);
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
                Intent intent = new Intent(AksaraDasar.this,DialogPenjelasanDasar.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
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
        Intent intent = new Intent(AksaraDasar.this,DaftarHuruf.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {
                case 0:
                    return DasarSatuFragment.newInstance("ScreenSlidePageFragment, Instance 1");
                case 1:
                    return DasarDuaFragment.newInstance("ScreenSlidePageTwoFragment, Instance 1");
                default:
                    return DasarSatuFragment.newInstance("ScreenSlidePageFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

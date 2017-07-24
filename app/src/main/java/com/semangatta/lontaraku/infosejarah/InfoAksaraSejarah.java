package com.semangatta.lontaraku.infosejarah;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.belajaraksara.InfoAksara;
import com.semangatta.lontaraku.R;

public class InfoAksaraSejarah extends FragmentActivity {

    private static final int NUM_PAGES = 1;
    public ViewPager mPager;
    public PagerAdapter mPagerAdapter;
    private MusicServiceTombol mServTombol;

    private boolean mIsBoundTombol = false;
    private Animation animScale;
    ImageButton btnKembali;

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
        doBindServiceTombol();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_aksara_sejarah);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        Animasi();
        GambarInfoAksaraSejarah();
        TombolInfoAksaraSejarah();
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    public void GambarInfoAksaraSejarah(){
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.mm)
                .centerCrop()
                .into(background_image);

        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);
    }

    public void TombolInfoAksaraSejarah(){
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InfoAksaraSejarah.this,InfoAksara.class);
                startActivity(intent);
                arg0.startAnimation(animScale);
                finish();
                mServTombol.startMusic();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        doUnbindServiceTombol();
        super.onDestroy();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoAksaraSejarah.this,InfoAksara.class);
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
                    return InfoAksaraSejarahFragment.newInstance("ScreenSlidePageFragment, Instance 1");
                default:
                    return InfoAksaraSejarahFragment.newInstance("ScreenSlidePageFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

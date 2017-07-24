package com.semangatta.lontaraku;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicServiceTombol extends Service implements MediaPlayer.OnErrorListener{

    private final IBinder mBinderTombol = new ServiceBinder();
    MediaPlayer mPlayer, mSwitch, mWrong, mCorrect, mKalah, mMenang;
    private int length = 0;
    public MusicServiceTombol() { }

    public class ServiceBinder extends Binder {
        public MusicServiceTombol getService()
        {
            return MusicServiceTombol.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return mBinderTombol;}

    @Override
    public void onCreate (){
        super.onCreate();
        CreateBg();
        CreateSwitch();
        CreateSalah();
        CreateKalah();
        CreateBenar();
        CreateMenang();
    }

    //button keseluruhan
    public void CreateBg() {
        mPlayer = MediaPlayer.create(this, R.raw.button);
        mPlayer.setOnErrorListener(this);
    }

    //button bantuan
    public void CreateSwitch() {
        mSwitch = MediaPlayer.create(this, R.raw.switchho);
        //mSwitch.setOnErrorListener(this);
    }

    //button salah
    public void CreateSalah() {
        mWrong = MediaPlayer.create(this, R.raw.buttonwrongdua);
        //mWrong.setOnErrorListener(this);
    }

    //button benar
    public void CreateBenar() {
        mCorrect = MediaPlayer.create(this, R.raw.answercorrect);
        //mWrong.setOnErrorListener(this);
    }

    //Musik Kalah
    public void CreateKalah() {
        mKalah = MediaPlayer.create(this, R.raw.rewardlosedua);
    }

    //Musik Menang
    public void CreateMenang() {
        mMenang = MediaPlayer.create(this, R.raw.rewardwintiga);
    }

    public void pauseMusic() {
        if( mPlayer.isPlaying() && mSwitch.isPlaying() && mWrong.isPlaying() && mKalah.isPlaying() && mCorrect.isPlaying()) {
            mPlayer.pause(); length=mPlayer.getCurrentPosition();
            mSwitch.pause(); length=mSwitch.getCurrentPosition();
            mWrong.pause(); length=mWrong.getCurrentPosition();
            mKalah.pause(); length=mKalah.getCurrentPosition();
            mCorrect.pause(); length=mCorrect.getCurrentPosition();
        }
    }

    public void playMusic() {
        CreateBg();
        mPlayer.start();
        mPlayer.setVolume(0.06f, 0.06f);
    }

    public void playMusicSwitch() {
        CreateSwitch();
        mSwitch.start();
        mSwitch.setVolume(0, 0);
    }

    public void playMusicWrong() {
        CreateSalah();
        mWrong.start();
        mWrong.setVolume(0, 0);
    }

    public void playMusicCorrect() {
        CreateBenar();
        mCorrect.start();
        mCorrect.setVolume(0, 0);
    }

    public void playMusicKalah() {
        CreateKalah();
        mKalah.start();
        mWrong.setVolume(0.7f, 0.7f);
    }

    public void playMusicMenang() {
        CreateMenang();
        mMenang.start();
        mMenang.setVolume(0.9f, 0.9f);
    }

    public void resumeMusic() {
        if(!mPlayer.isPlaying() && !mSwitch.isPlaying() &&
           !mWrong.isPlaying() && !mKalah.isPlaying() &&
           !mCorrect.isPlaying() && mMenang.isPlaying()) {

            mPlayer.seekTo(length); mPlayer.start();
            mSwitch.seekTo(length); mSwitch.start();
            mWrong.seekTo(length); mWrong.start();
            mKalah.seekTo(length); mKalah.start();
            mCorrect.seekTo(length); mCorrect.start();
            mMenang.seekTo(length); mMenang.start();
        }
    }

    public  void startMusic() {
        if( mPlayer != null) {
            mPlayer.start();
            mPlayer.setVolume(0.06f, 0.06f);
        }
    }

    public  void startMusicSwitch() {
        if( mSwitch != null) {
            mSwitch.start();
            mSwitch.setVolume(0.7f, 0.7f);
        }
    }

    public  void startMusicWrong() {
        if( mWrong != null) {
            mWrong.start();
            mWrong.setVolume(0.7f, 0.7f);
        }
    }

    public  void startMusicCorrect() {
        if( mCorrect != null) {
            mCorrect.start();
            mCorrect.setVolume(0.3f, 0.3f);
        }
    }

    public  void startMusicKalah() {
        if( mKalah != null) {
            mKalah.start();
            //mKalah.setVolume(0.7f, 0.7f);
        }
    }

    public  void startMusicMenang() {
        if( mMenang != null) {
            mMenang.start();
            //mKalah.setVolume(0.7f, 0.7f);
        }
    }

    public void stopMusic() {
        if( mPlayer != null ) {
            mPlayer.stop();
            mPlayer = null;
        }
    }

    public void stopMusicSwitch() {
        if( mSwitch != null ) {
            mSwitch.stop();
            mSwitch = null;
        }
    }

    public void stopMusicWrong() {
        if( mWrong != null ) {
            mWrong.stop();
            mWrong = null;
        }
    }

    public void stopMusicCorrect() {
        if( mCorrect != null ) {
            mCorrect.stop();
            mCorrect = null;
        }
    }

    public void stopMusicKalah() {
        if( mKalah != null ) {
            mKalah.stop();
            mKalah = null;
        }
    }

    public void stopMusicMenang() {
        if( mMenang != null ) {
            mMenang.stop(); mMenang = null;
        }
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        if(mPlayer != null && mSwitch != null &&
           mWrong != null && mKalah != null &&
           mCorrect != null && mMenang != null) {
            try{
                mPlayer.stop(); mPlayer.release();
                mSwitch.stop(); mSwitch.release();
                mWrong.stop(); mWrong.release();
                mKalah.stop(); mKalah.release();
                mCorrect.stop(); mCorrect.release();
                mMenang.stop(); mMenang.release();
            }finally {
                mPlayer = null; mSwitch = null;
                mWrong = null; mKalah = null;
                mCorrect = null; mMenang = null;
            }
        }
    }
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if(mPlayer != null) {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        } return false;
    }
}
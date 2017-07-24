package com.semangatta.lontaraku;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicServiceBermain extends Service implements MediaPlayer.OnErrorListener{

    private final IBinder mBinderBermain = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    public MusicServiceBermain() { }

    public class ServiceBinder extends Binder {
        public MusicServiceBermain getService()
        {
            return MusicServiceBermain.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return mBinderBermain;}

    @Override
    public void onCreate (){
        super.onCreate();
        Create();
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra){
                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    public void Create() {
        mPlayer = MediaPlayer.create(this, R.raw.bgbermain);
        mPlayer.setOnErrorListener(this);

        if(mPlayer!= null)
        {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);
        }
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        mPlayer = MediaPlayer.create(this, R.raw.bgbermain);
        mPlayer.start();
        mPlayer.setLooping(true);
        return START_STICKY;
    }

    public void pauseMusic()
    {
        if(mPlayer.isPlaying())
        {
            mPlayer.pause();
            length=mPlayer.getCurrentPosition();
        }
    }

    public void inMusic() {
        mPlayer.setVolume(0, 0);
    }

    public void outMusic() {
        mPlayer.setVolume(0.9f, 0.9f);
    }

    public void resumeMusic()
    {
        if(!mPlayer.isPlaying())
        {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public  void startMusic() {
        mPlayer.start();
    }

    public void stopMusic()
    {
        mPlayer.stop();
        mPlayer.reset();
        //mPlayer.seekTo(length);
        //mPlayer.release();
        //mPlayer = null;
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
    }
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if(mPlayer != null)
        {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
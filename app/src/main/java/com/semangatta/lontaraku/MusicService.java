package com.semangatta.lontaraku;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service implements MediaPlayer.OnErrorListener{

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    public MusicService() { }

    public class ServiceBinder extends Binder {
        public MusicService getService()
        {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0){return mBinder;}

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
        mPlayer = MediaPlayer.create(this, R.raw.bgmusic);
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
        mPlayer = MediaPlayer.create(this, R.raw.bgmusic);
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

    public void resumeMusic()
    {
        if(!mPlayer.isPlaying())
        {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void inMusic() {
        mPlayer.setVolume(0.2f, 0.2f);
    }

    public void outMusic() {
        mPlayer.setVolume(0.9f, 0.9f);
    }

    public void resumeMusic2()
    {
        if(!mPlayer.isPlaying())
        {
            mPlayer.seekTo(0);
            //mPlayer.reset();
            mPlayer.start();
        }
    }

    public  void startMusic() {
        //if (mPlayer == null) Create();
        /*Thread th = new Thread(new Runnable() {
            @Override
            public void run()
            {
                mPlayer.start();
            }
        });
        th.start();*/
        mPlayer.seekTo(0);
        mPlayer.start();
    }

    public  void startMusic2() {
        Create();
        //mPlayer.seekTo(0);
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
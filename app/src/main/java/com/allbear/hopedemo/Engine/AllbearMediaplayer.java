package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.allbear.hopedemo.util.FileUtil;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/20.
 */

public class AllbearMediaplayer {
    private AllbearMediaplayer(Context context) {
        mContext = context;
        initPlayer();
    }
    private static AllbearMediaplayer mAllbearMediaplayer = null;

    private static MediaPlayer mMediaPlayer = null;
    private static Context mContext;
    public static AllbearMediaplayer getInstance(Context context) {
        if (mAllbearMediaplayer == null) {
            synchronized (AllbearMediaplayer.class) {
                if (mAllbearMediaplayer == null) {
                    mAllbearMediaplayer = new AllbearMediaplayer(context);
                }
            }
        }
        return mAllbearMediaplayer;
    }

    public void destory(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    public void setMediaPlayListener(MediaPlayer.OnCompletionListener callback){
        if(callback == null){
            return;
        }
        mMediaPlayer.setOnCompletionListener(callback);
    }
    private static void initPlayer(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void startPlaySDPath(String path){
        if(path == null || !FileUtil.isPathFileExist(path))
            return;
        mMediaPlayer.reset();
        AllbearEngineBase.getInstance().stopAllEngine();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startPlay(String name){
        if(name == null)
            return;
        mMediaPlayer.reset();
        AllbearEngineBase.getInstance().stopAllEngine();
        try {
            AssetFileDescriptor fileDescriptor;
            fileDescriptor = mContext.getAssets().openFd(name);
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startPlay(){
        if(mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            AllbearEngineBase.getInstance().stopAllEngine();
            mMediaPlayer.start();
        }
    }
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }
    public void pausePlay(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }
    public void stopPlay(){
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
    }
}

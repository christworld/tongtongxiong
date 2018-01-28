package com.allbear.hopedemo.Fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearIat;
import com.allbear.hopedemo.Engine.AllbearIse;
import com.allbear.hopedemo.Engine.AllbearMediaplayer;
import com.allbear.hopedemo.Engine.AllbearTranslate;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Engine.AllbearTulingTalk;
import com.allbear.hopedemo.SQLite.SQLiteHelper;

/**
 * Created by Administrator on 2017/3/19.
 */

public class AllbearFragment extends Fragment
{
    private static String LogTag = "HopeAllbearFragment";
    protected View mView;

    protected AllbearMediaplayer mMediaPlayer;
    protected AllbearTts mAllbearTts;
    protected AllbearIat mAllbearIat;
    protected AllbearIse mAllbearIse;
    protected AllbearTranslate mAllbearTranslate;
    protected AllbearTulingTalk mAllbearTulingTalk;
    protected AudioManager mAudioManager;
    //最大音量
    protected int mMaxVolume = 0;
    //当前音量
    protected int mCurrentVolume = 0;
    protected Handler mHandler = null;

    protected SQLiteHelper mSQLHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaPlayer = AllbearMediaplayer.getInstance(getActivity());
        mAllbearTts = AllbearTts.getInstance(getActivity());
        mAllbearIat = AllbearIat.getInstance(getActivity());
        mAllbearIse = AllbearIse.getInstance(getActivity());
        mAllbearTranslate = AllbearTranslate.getInstance(getActivity());
        mAllbearTulingTalk = AllbearTulingTalk.getInstance(getActivity());
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mHandler = new Handler();
        mSQLHelper = SQLiteHelper.getInstance();
    }

    public void onMediaPlayerCompletion(){
        Log.info(LogTag,"onMediaPlayerCompletion");
    }
    public void onTtsListenerCompleted(){
        Log.info(LogTag,"onTtsListenerCompleted");
    }
    public void onIatGetResultText(String text){
        Log.info(LogTag,"onIatGetResultText");
    }
    public void onIatGetResultLast(String text){
        Log.info(LogTag,"onIatGetResultLast");
    }
    public void onTranslateText(String text){
        Log.info(LogTag,"onTranslateText");
    }
    public void onTulingTalkText(String text){
//        Log.info(LogTag,"onTulingTalkText");
    }
    public void buttonLongPressUp(int i){
        Log.info(LogTag,"buttonLongPressUp id=" + i);
    }
    public void buttonLongPress(int i){
        Log.info(LogTag,"buttonLongPress id=" + i);
    }
    public void onEntryView(){
//        Log.info(LogTag,"onEntryView");
    }

    public void onExitView(){
//        Log.info(LogTag,"onExitView");
    }
}

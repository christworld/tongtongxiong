package com.allbear.hopedemo.Engine;


import android.content.Context;
import android.media.AudioManager;

import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/12/24.
 */

public class AllbearEngineBase {
    private static AllbearEngineBase instance = null;
    private Context mContext;
    private AllbearMediaplayer mMediaPlayer;
    private AllbearTts mAllbearTts;
//    private AllbearIat mAllbearIat;
//    private AllbearIse mAllbearIse;
//    private AllbearTranslate mAllbearTranslate;
//    private AllbearTulingTalk mAllbearTulingTalk;
//    private AllbearWeather mAllbearWeather;
//    private AudioManager mAudioManager;
    private AllbearEngineBase() {
        mContext = Util.getMainActivityContext();
        mMediaPlayer = AllbearMediaplayer.getInstance(mContext);
        mAllbearTts = AllbearTts.getInstance(mContext);
//        mAllbearIat = AllbearIat.getInstance(mContext);
//        mAllbearIse = AllbearIse.getInstance(mContext);
//        mAllbearTranslate = AllbearTranslate.getInstance(mContext);
//        mAllbearWeather = AllbearWeather.getInstance(mContext);
//        mAllbearTulingTalk = AllbearTulingTalk.getInstance(mContext);
//        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }
    public static AllbearEngineBase getInstance() {
        if (instance == null) {
            instance = new AllbearEngineBase();
        }
        return instance;
    }

    public void stopAllEngine(){
        mMediaPlayer.pausePlay();
//        mAllbearIat.stopIat();
//        mAllbearIse.stopIse();
        mAllbearTts.pauseTts();
    }
}

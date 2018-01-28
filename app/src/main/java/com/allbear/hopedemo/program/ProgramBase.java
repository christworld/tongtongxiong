package com.allbear.hopedemo.program;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.ArrayMap;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearIat;
import com.allbear.hopedemo.Engine.AllbearIse;
import com.allbear.hopedemo.Engine.AllbearMediaplayer;
import com.allbear.hopedemo.Engine.AllbearTranslate;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Engine.AllbearTulingTalk;
import com.allbear.hopedemo.Engine.AllbearWeather;
import com.allbear.hopedemo.Engine.IatRecogListener;
import com.allbear.hopedemo.Engine.IseRecogListener;
import com.allbear.hopedemo.Engine.TranslateTextListener;
import com.allbear.hopedemo.Engine.TtsSynthesizerListener;
import com.allbear.hopedemo.Engine.WeatherListener;
import com.allbear.hopedemo.SQLite.SQLiteHelper;
import com.allbear.hopedemo.Structure.ask_children;
import com.allbear.hopedemo.Structure.ask_robot;
import com.allbear.hopedemo.Structure.users;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class ProgramBase {
    private static String LogTag = "HopeProgramBase";

    protected AllbearMediaplayer mMediaPlayer;
    protected AllbearTts mAllbearTts;
    protected AllbearIat mAllbearIat;
    protected AllbearIse mAllbearIse;
    protected AllbearTranslate mAllbearTranslate;
    protected AllbearTulingTalk mAllbearTulingTalk;
    protected AllbearWeather mAllbearWeather;
    protected AudioManager mAudioManager;
    protected Handler mHandler = null;
    protected SQLiteHelper mSQLHelper;
    protected Context mContext;
    protected ask_children mAskChildren ;
    protected ask_robot mAskRobot ;
    protected users mUsers;
    protected String mCity;

    public ProgramBase(){
        mContext = Util.getMainActivityContext();
        mMediaPlayer = AllbearMediaplayer.getInstance(mContext);
        mAllbearTts = AllbearTts.getInstance(mContext);
        mAllbearIat = AllbearIat.getInstance(mContext);
        mAllbearIse = AllbearIse.getInstance(mContext);
        mAllbearTranslate = AllbearTranslate.getInstance(mContext);
        mAllbearWeather = AllbearWeather.getInstance(mContext);
        mAllbearTulingTalk = AllbearTulingTalk.getInstance(mContext);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mHandler = new Handler();
        mSQLHelper = SQLiteHelper.getInstance();
        mAskChildren = ask_children.getInstance();
        mAskRobot = ask_robot.getInstance();
        mUsers = users.getInstance();
        Log.info("HopeProgramBase","ProgramBase  ");
    }

    protected void doProgramTime(){}

    protected String doGetCity() {
        String cityName = "";
        Cursor cursor = mSQLHelper.query(Const.sqlSelectCityName);
        if(cursor.moveToFirst()) {
            cityName = cursor.getString(0);
        }
        cursor.close();
        return cityName;
    }

    protected IatRecogListener mIatRecogListener = new IatRecogListener() {
        @Override
        public void onIatRecogResult(String result, boolean isLast) {
            if(isLast){
                onIatResult(result);
            }
        }
        @Override
        public void onIatRecogResult(String EnglisthResult,String ChineseResult,boolean isLast) {
            if(isLast){
                onIatResult(EnglisthResult,ChineseResult);
            }
            Log.info(LogTag,"onIatRecogResult EnglisthResult=" + EnglisthResult + "  ChineseResult=" + ChineseResult + " isLast=" + isLast);
        }

        @Override
        public void onError(String error) {
            Log.info(LogTag,"onIatRecogResult error=" + error);
            onIatError(error);
        }
    };
    protected void onIatError(String error) {}
    protected void onIatResult(String result) {}
    protected void onIatResult(String EnglisthResult,String ChineseResult) {}

    protected TtsSynthesizerListener mTtsSynthesizerListener = new TtsSynthesizerListener() {
        @Override
        public void onTtsListenerCompleted() {
            onTtsCompleted();
            Log.info(LogTag,"onTtsListenerCompleted ");
        }
    };
    protected void onTtsCompleted() {
    }

    protected ArrayMap<String,String> mWeatherMap = new ArrayMap<String,String>();
    protected WeatherListener mWeatherListener = new WeatherListener() {
        @Override
        public void onWeatherResult(ArrayMap<String, String> result) {
            Log.info(LogTag,"onWeatherResult  result=" + result);
            mWeatherMap = result;
            onGetWeatherResult(result);
        }
    };
    protected void onGetWeatherResult(ArrayMap<String, String> result) {
    }

    protected TranslateTextListener mTranslateTextListener = new TranslateTextListener() {
        @Override
        public void onTranslateResult(String result) {
            onTransResult(result);
        }
    };
    protected void onTransResult(String result) {}

    protected IseRecogListener mIseRecogListener = new IseRecogListener() {
        @Override
        public void onIseRecogResult(String result) {
            onIseRecResult(result);
        }

        @Override
        public void onIseRecogScoreResult(float score) {
            onIseRecScoreResult(score);
        }
    };
    protected void onIseRecScoreResult(float score) {}
    protected void onIseRecResult(String result) {}

    protected MediaPlayer.OnCompletionListener mMediaPlayerOncompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            onMediaPlayCompletion(mp);
        }
    };
    protected void onMediaPlayCompletion(MediaPlayer mp) {}
}

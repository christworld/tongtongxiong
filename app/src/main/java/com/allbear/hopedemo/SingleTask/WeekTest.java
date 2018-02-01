package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

import java.util.logging.Handler;

/**
 * Created by Administrator on 2018/1/30.
 */

public class WeekTest extends SingleTaskBase {
    private static String LogTag = "HopeWeekTest";
    private static WeekTest instance = null;

    private int mWaitNoSpeachTimes = 0;
    private static final int WATI_TIMES = 10;
    private static final String mNoSpecachError = "错误码:10118";
    private String mProgramFlag = "";
    private int mEnglishTestNo = 0;
    private boolean mIsSecondTimes = false;

    public static WeekTest getInstance() {
        if (instance == null) {
            instance = new WeekTest();
        }
        return instance;
    }
    public WeekTest(){
        mAllbearIat.setIatRecogListener(mIatRecogListener);
        mAllbearTts.setTtsSynthesizerListener(mTtsSynthesizerListener);
        mAllbearIse.setIseRecogListener(mIseRecogListener);
    }

    @Override
    protected void onTtsCompleted() {
        Log.info(LogTag,"onTtsCompleted");
        super.onTtsCompleted();
        if(mProgramFlag.equals("title")){
            doTestTitle("repleat");
        }else if(mProgramFlag.equals("repleat")){
            mMediaPlayer.startPlay("Sound/didi.wav");
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAllbearIse.startIse(mEnglishTests.getEnglish(mEnglishTestNo));
                }
            },1000);
        }
    }
    @Override
    protected void onIatError(String error) {
        Log.info(LogTag,"onIatError error=" + error);
        if(error.contains(mNoSpecachError)){
//            mWaitNoSpeachTimes += 1;
//            if(mWaitNoSpeachTimes < WATI_TIMES){
//                doStartIat();
//            }else{
//                doNoBodyHere();
//            }
        }
        super.onIatError(error);
    }

    @Override
    protected void onIatResult(String result) {
        super.onIatResult(result);
    }

    @Override
    protected void onIatResult(String EnglisthResult, String ChineseResult) {
        String result = EnglisthResult;
        Log.info(LogTag,"onIatResult result=" + result);
        super.onIatResult(EnglisthResult, ChineseResult);

    }

    @Override
    protected void onIseRecScoreResult(float score) {
        super.onIseRecScoreResult(score);
        Log.info(LogTag,"onIseRecScoreResult score=" + score*20);
        if(mIsSecondTimes == true){
            mEnglishTestNo ++;
            mIsSecondTimes = false;
        }else{
            mIsSecondTimes = true;
        }
        doTestTitle("title");
    }

    private void doStartTtsFlag(String flag, String str) {
        mProgramFlag = flag;
        mAllbearTts.startTts(str);
    }

    private void doTestTitle(String flag){
        Log.info(LogTag,"doTestTitle");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String question = mEnglishTests.getEnglish(mEnglishTestNo) + mEnglishTests.getChinese(mEnglishTestNo);
                doStartTtsFlag(flag,question);
            }
        },1000);
    }
    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");
        mMediaPlayer.startPlay("Sound/begin.wav");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doStartTtsFlag("welcome","开始测验，每道题会播放两遍。");
            }
        },6000);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnglishTestNo = 0;
                doTestTitle("title");
            }
        },11000);
    }
}


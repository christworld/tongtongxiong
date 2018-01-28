package com.allbear.hopedemo.program;

import android.os.Handler;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class SleepTime extends ProgramBase {
    private static String LogTag = "HopeSleepTime";
    private static SleepTime instance = null;

    private int mWaitNoSpeachTimes = 0;
    private static final int WATI_TIMES = 10;
    private String mProgramFlag = "";
    private int mAskRobotNo = 0;

    public static SleepTime getInstance() {
        if (instance == null) {
            instance = new SleepTime();
        }
        return instance;
    }
    public SleepTime(){
        mAllbearIse.setIseRecogListener(mIseRecogListener);
        mAllbearTts.setTtsSynthesizerListener(mTtsSynthesizerListener);
    }
    @Override
    protected void onTtsCompleted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doTtsCompleted();
            }
        },1500);

        super.onTtsCompleted();
    }

    private void doTtsCompleted() {
        if(mProgramFlag.equals("welcome")) {
            doNextQueation();
        }else if(mProgramFlag.equals("repeat")){
            doFollowMe();
        }else if(mProgramFlag.equals("follow")){
            doIseStart();
        }else if(mProgramFlag.equals("ise")){
            mAllbearIse.startIse(mAskRobot.getEnglishQuestion(mAskRobotNo));
        }
    }

    private void doIseStart() {
        doAskQuestion(getRepeatIse());
    }

    private void doNextQueation() {
        mAskRobotNo  += 1;
        String repeat = getRepeatNoStart();
        doAskQuestion(repeat);
    }
    private void doFollowMe() {
        String follow = getRepeatFollow();
        doAskQuestion(follow);
    }

    private void doAskQuestion(String pre) {
        Log.info(LogTag,"doAskQuestion mAskRobot.getAskRobotCount()=" + mAskRobot.getAskRobotCount() + mProgramFlag);
        Log.info(LogTag,"doAskQuestion pre=" + pre);
        if(mAskRobotNo >= mAskRobot.getAskRobotCount()){
            doStartTtsFlag("","hooray,we are doen.");
        }
        Log.info(LogTag,"doAskQuestion mAskQuestionNo=" + mAskRobotNo);
        if(mProgramFlag.equals("welcome")) {
            String question = pre + mAskRobot.getEnglishQuestion(mAskRobotNo) + mAskRobot.getChineseQuestion(mAskRobotNo)+ mAskRobot.getEnglishQuestion(mAskRobotNo);
            doStartTtsFlag("repeat",question);
        }else if(mProgramFlag.equals("repeat")){
            String question = pre + mAskRobot.getEnglishQuestion(mAskRobotNo);
            doStartTtsFlag("follow",question);
        }else if(mProgramFlag.equals("follow")){
            String question = pre;
            doStartTtsFlag("ise",question);
        }
    }
    private String getRepeatNoStart(){
        String repeat = "Number " + mAskRobotNo + " ; 第" + mAskRobotNo + "句";
        return repeat;
    }
    private String getRepeatFollow(){
        String repeat = "Please follow me.请跟我读。 ";
        return repeat;
    }
    private String getRepeatIse(){
        String repeat = "Now,It's your turn.现在该你读了，滴。 ";
        return repeat;
    }
    @Override
    protected void onIseRecScoreResult(float score) {
        doIseScoreResult((int)(score*20));
        super.onIseRecScoreResult(score);
    }

    private void doIseScoreResult(int score) {
        Log.info(LogTag,"doIseScoreResult score=" + score);
        String PreResult = getScoreLevel(score);
        if(PreResult.equals("")){
            doIseBad(score);
        }else{
            doIseWell(score);
        }
    }

    private void doIseWell(int score) {
        String welcome = getScoreLevel(score) + ",you scored " + score + " 你得了" + score + "分。";
        doStartTtsFlag("welcome",welcome);
        mIseBadLastScore = -1;
    }

    private void doIseBad(int score) {
        if(mIseBadLastScore == -1) {
            mIseBadLastScore = score;
            String repeat = "you scored " + score + ",try agian.";
            doStartTtsFlag("repeat", repeat);
        }else{
            String welcome = "you scored " + score + " 你得了" + score + "分。";
            if(score > mIseBadLastScore){
                welcome += "it is better,哦，有进步哦。";
            }else{
                welcome += "never mind,let's move on.";
            }
            mIseBadLastScore = -1;
            doStartTtsFlag("welcome",welcome);
        }
    }
    private int mIseBadLastScore = -1;
    private final String SCORE_Perfect = "Perfect";
    private final String SCORE_Execllent = "Execllent";
    private final String SCORE_Great = "Great";
    private final String SCORE_Good = "Good";
    private final String SCORE_Bad = "";
    private String getScoreLevel(int score){
        String level = SCORE_Bad;
        if(score >= 95){
            level = SCORE_Perfect;
        }else if(score >= 90){
            level = SCORE_Execllent;
        }else if(score >= 85){
            level = SCORE_Great;
        }else if(score >= 80){
            level = SCORE_Good;
        }else {
            level = SCORE_Bad;
        }
        return level;
    }
    private void doStartTtsFlag(String flag, String str) {
        mProgramFlag = flag;
        mAllbearTts.startTts(str);
    }
    @Override
    public void doProgramTime(){
        String welcome = "Hi " + mUsers.getEngName() + " ,you are back. Let's Repeat. " + mUsers.getName() + " 你回来了。我们开始跟读吧！";
        doStartTtsFlag("welcome",welcome);
    }
}

package com.allbear.hopedemo.program;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class OralTime extends ProgramBase {
    private static String LogTag = "HopeOralTime";
    private static OralTime instance = null;

    private int mWaitNoSpeachTimes = 0;
    private static final int WATI_TIMES = 10;
    private static final String mNoSpecachError = "错误码:10118";
    private String mProgramFlag = "";
    private int mAskQuestionNo = 0;

    public static OralTime getInstance() {
        if (instance == null) {
            instance = new OralTime();
        }
        return instance;
    }
    public OralTime(){
        mAllbearIat.setIatRecogListener(mIatRecogListener);
        mAllbearTts.setTtsSynthesizerListener(mTtsSynthesizerListener);
    }

    private void doNoBodyHere() {
        Log.info(LogTag,"doNoBodyHere ");
        String noBody = "No Body Here? " + mUsers.getEngName() + " ! Are you Here? " + mUsers.getName() + "Are you Here?";//"！ 你在吗？";
        doStartTtsFlag("",noBody);
    }

    @Override
    protected void onTtsCompleted() {
        doStartIat();
        super.onTtsCompleted();
    }
    private void doStartIat(){
        if(mProgramFlag.equals("welcome")) {
            mAllbearIat.startIatNoUI("zh_cn");
        }else if(mProgramFlag.equals("ask")){
            mAllbearIat.startIatNoUI("en_us");
        }else{

        }
    }
    @Override
    protected void onIatError(String error) {
        if(error.contains(mNoSpecachError)){
            mWaitNoSpeachTimes += 1;
            if(mWaitNoSpeachTimes < WATI_TIMES){
                doStartIat();
            }else{
                doNoBodyHere();
            }
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
        mWaitNoSpeachTimes = 0;
        if(mProgramFlag.equals("welcome")) {
            if (result.contains("") || result.contains("back")|| result.contains("Yes") || result.contains("好")) {
                doAskQuestion("");
            }
        }else if(mProgramFlag.equals("ask")){
            boolean answer2 = mAskChildren.getAnswer2(mAskQuestionNo).equals("")?false:Util.contains(result,mAskChildren.getAnswer2(mAskQuestionNo));
            if(Util.contains(result,mAskChildren.getAnswer1(mAskQuestionNo)) || answer2){
                doAnswerPerfect();
                return;
            }
            String[] Keywords = mAskChildren.getKeywords(mAskQuestionNo);
            boolean isRight = false;
            for(int i=0;i<Keywords.length;i++){
                if(Util.contains(result,Keywords[i])){
                    doAnswerRight();
                    isRight = true;
                }
            }
            if(!isRight){
                doAnswerWrong();
            }
        }
        super.onIatResult(EnglisthResult, ChineseResult);
    }

    private int mAnswerWrongTimes = 0;
    private void doAnswerWrong() {
        mAnswerWrongTimes  += 1;
        Log.info(LogTag,"doAnswerWrong mAnswerWrongTimes=" + mAnswerWrongTimes);
        if(mAnswerWrongTimes == 1){
            String question = mAskChildren.getEngQuestion(mAskQuestionNo) + " ! !" + mAskChildren.getChiQuestion(mAskQuestionNo);
            doStartTtsFlag("ask",question);
        }else if(mAnswerWrongTimes == 2){
            String question = mAskChildren.getEngQuestion(mAskQuestionNo) + " ! !" + mAskChildren.getChiQuestion(mAskQuestionNo)
                    + " ! !" + mAskChildren.getAnswer1(mAskQuestionNo);
            doStartTtsFlag("ask",question);
        }else{
            mAskQuestionNo  += 1;
            doAskQuestion("");
        }
    }
    //回答完全正确
    private void doAnswerPerfect() {
        mAskQuestionNo += 1;
        doGetScore();
        String answerRight = "Perfect，得分，Next Question!!";
        doAskQuestion(answerRight);
    }
    //得分
    private void doGetScore() {
    }
    //回答对了
    private void doAnswerRight() {
        mAskQuestionNo  += 1;
        String answerRight = "Do well，鼓掌，Next Question!!";
        doAskQuestion(answerRight);
    }

    private void doAskQuestion(String pre) {
        Log.info(LogTag,"doAskQuestion pre=" + pre);
        mAnswerWrongTimes = 0;
        if(mAskQuestionNo >= mAskChildren.getQuestionCount()){
            mAskQuestionNo = 0;
        }
        Log.info(LogTag,"doAskQuestion mAskQuestionNo=" + mAskQuestionNo);
        String question = pre + mAskChildren.getEngQuestion(mAskQuestionNo);
        doStartTtsFlag("ask",question);
    }

    private void doStartTtsFlag(String flag, String str) {
        mProgramFlag = flag;
        mAllbearTts.startTts(str);
    }

    @Override
    public void doProgramTime(){
        Log.info(LogTag,mUsers.getEngName());
        String welcome = "Hi " + mUsers.getEngName() + " ,you are back. Let's chat. " + mUsers.getName() + " 你回来了，我好想你哦！呵呵呵。我们开始聊天吧！";
        doStartTtsFlag("welcome",welcome);
    }
}

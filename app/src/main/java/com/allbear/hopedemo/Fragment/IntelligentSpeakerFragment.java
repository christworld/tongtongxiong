package com.allbear.hopedemo.Fragment;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allbear.hopedemo.AllbearViewLongPress;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/19.
 */

public class IntelligentSpeakerFragment extends AllbearFragment implements View.OnClickListener {
    static String LogTag = "HopeIntelligentSpeakerFragment";

    private List<Button> mButtons = new ArrayList<Button>();
    private int [] mButtonIds = {
            R.id.id_top_speaker_btn,
            R.id.id_top_trans_btn,
            R.id.id_back_btn,
            R.id.id_forward_btn,
            R.id.id_bottom_describe_btn,
            R.id.id_bottom_ask_btn,
            R.id.id_bottom_talk_btn
    };
    private String [] mCommandKeyWord = {
            "介绍自己",
            "介绍小主人",
            "操作指南",
            "故事",
            "歌曲",
            "诗歌"
    };
    private String [] mCommandResult = {
            "self-introduction.wav",
            "introduce-master.wav",
            "operation-guide.wav",
            "story.wav",
            "song.mp3",
            "poetry.wav",
            "not-identified.wav"
    };
    private int mMediaplayerState = 0;//0:idle;1:playing;2:pause
    private int mNowPressButton = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.intelligentspeaker_fragment, container, false);
        }
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView() {
        for(int i = 0;i < mButtonIds.length; i++){
            Button button = (Button) getActivity().findViewById(mButtonIds[i]);
            button.setOnClickListener(this);
            new AllbearViewLongPress(button,this);
            mButtons.add(button);
        }
    }
    @Override
    public void onEntryView() {
        super.onEntryView();
        Log.info(LogTag,"onEntryView");
    }
    @Override
    public void onExitView() {
        super.onExitView();
        Log.info(LogTag,"onExitView");
        mAllbearIat.stopIat();
        mAllbearTts.stopTts();
        mMediaPlayer.pausePlay();
        mMediaplayerState = 0;
        mAllbearTulingTalk.onExit();
        mAllbearTranslate.onExit();
    }
    @Override
    public void onClick(View view) {
        Log.info(LogTag,"onClick");
        int id = view.getId();
        onClick(getIdByViewId(id));
    }
    private int getIdByViewId(int id){
        for(int i = 0;i < mButtonIds.length; i++){
            if(mButtonIds[i] == id){
                return i;
            }
        }
        return 0;
    }
    private int getCommandResult(String str){
        int i = 0;
        for(;i<mCommandKeyWord.length;i++) {
            if (str.contains(mCommandKeyWord[i])) {
                return i;
            }
        }
        return i;
    }
    //0、音箱键
    private void buttonLongPressUp0(){
        mAllbearIat.stopIat();
    }
    private void buttonLongPress0(){
        mAllbearIat.startIatNoUI("mandarin");
    }
    private void buttonOnClick0(){
        if(mMediaplayerState == 1){
            mMediaPlayer.pausePlay();
            mMediaplayerState = 2;
        }else if(mMediaplayerState == 2){
            mMediaPlayer.startPlay();
            mMediaplayerState = 1;
        }
    }
    //1、翻译键
    private void buttonLongPressUp1(){
        mAllbearIat.stopIat();
    }
    private void buttonLongPress1(){
        mAllbearIat.startIatNoUI("mandarin");
    }
    private void buttonOnClick1(){

    }

    private Runnable mTurnDownVolRunnable = new Runnable() {
        public void run() {
            //execute the task
            if (mAudioManager != null) {
                Log.info(LogTag,"mTurnDownVolRunnable mCurrentVolume=" + mCurrentVolume);
                if (mCurrentVolume > 0) {
                    mCurrentVolume -= 1;
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0); //mCurrentVolume:音量绝对值
                    mHandler.postDelayed(mTurnDownVolRunnable, 200);
                }
            }
        }
    };
    private int mMediaPlayingIndex = 1;
    //2、后退键
    private void buttonLongPressUp2(){
        mHandler.removeCallbacks(mTurnDownVolRunnable);
    }
    private void buttonLongPress2(){
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mHandler.postDelayed(mTurnDownVolRunnable,0);
    }
    private void buttonOnClick2(){
        mMediaPlayingIndex -= 1;
        if(mMediaPlayingIndex < 1){
            mMediaPlayingIndex = mTalkingTimes;
        }
        File f = new File(getIatAudioPath(mMediaPlayingIndex + ""));
        if (f.exists()){
            mMediaPlayer.startPlaySDPath(getIatAudioPath(mMediaPlayingIndex + ""));
        }
    }
    private Runnable mTurnUpVolRunnable = new Runnable() {
        public void run() {
            //execute the task
            if (mAudioManager != null) {
                Log.info(LogTag,"mTurnDownVolRunnable mCurrentVolume=" + mCurrentVolume + "  mMaxVolume=" + mMaxVolume);
                if (mCurrentVolume < mMaxVolume) {
                    mCurrentVolume += 1;
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0); //mCurrentVolume:音量绝对值
                    mHandler.postDelayed(mTurnUpVolRunnable, 200);
                }
            }
        }
    };
    //3、前进键
    private void buttonLongPressUp3(){
        mHandler.removeCallbacks(mTurnUpVolRunnable);
    }
    private void buttonLongPress3(){
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mHandler.postDelayed(mTurnUpVolRunnable,0);
    }
    private void buttonOnClick3(){
        mMediaPlayingIndex += 1;
        Log.info(LogTag,"buttonOnClick3 mMediaPlayingIndex =" + mMediaPlayingIndex);
        if(mMediaPlayingIndex > mTalkingTimes){
            mMediaPlayingIndex = 1;
        }
        File f = new File(getIatAudioPath(mMediaPlayingIndex + ""));
        Log.info(LogTag,"buttonOnClick3 f.exists() =" + f.exists());
        if (f.exists()){
            mMediaPlayer.startPlaySDPath(getIatAudioPath(mMediaPlayingIndex + ""));
        }
    }
    //4、绘本键
    private void buttonLongPressUp4(){

    }
    private void buttonLongPress4(){

    }
    private void buttonOnClick4(){

    }

    private String [] mTalkingAskStrings = {
            "Hello! Good morning",
            "How are you?",
            "What's your name?",
            "Hello. Nice to meet you",
            "How do you do?",
            "This is my good friend, Hope. Do you know him",
            "Who's that boy over there?",
            "How old are you?",
            "Which school are you in?",
            "What class are you in?",
            "Where do you live?",
            "What does your father do? ",
            "What does your mother do? ",
            "Have you learned English?",
            "Do you like English?",
            "Do you like Chinese?",
            "Do you like math?",
            "What is this?",
            "Is this your bag? ",
            "Whose bag is this? ",
            "Whose book is this? ",
            "Whose pen is this? ",
            "Do you want something to drink?",
            "Do you want something to eat?",
            "What did you have for lunch?",
            "What food do you like? Help yourself to the dishes",
            "What would you like to eat? ",
            "What's on today on TV?",
            "What is your favourite programme on TV? ",
            "Can I watch TV a while?",
            "Turn the TV down a little. It is too loud."
    };
    private HashSet<String> mHashSet=new HashSet<String>();
    private int mTalkingTimes = 5;
    private int mCurrentTalkinkTime = 0;
    private String getIatAudioPath(String name){
        return Environment.getExternalStorageDirectory() + "/msc/" + name + ".wav";
    }
    //5、问答键
    private void buttonLongPressUp5() {
        File f = new File(getIatAudioPath(1 + ""));
        Log.info(LogTag,"f.exists()=" + f.exists());
        if (f.exists()){
            mMediaPlayer.startPlaySDPath(getIatAudioPath(1 + ""));
        }
    }
    private void buttonLongPress5(){

    }
    private void buttonOnClick5(){
        Log.info(LogTag,"buttonOnClick5 mTalkingsize=" + mTalkingAskStrings.length);
        mHashSet.clear();
        mCurrentTalkinkTime = 0;
        mMediaPlayingIndex = 1;
        startTalking();
    }
    private Runnable mStartTalkingRunnable = new Runnable() {
        public void run() {
            startTalking();
        }
    };
    private void startTalking(){
        Random random=new Random();
        mCurrentTalkinkTime += 1;
        Log.info(LogTag,"startTalking mCurrentTalkinkTime =" + mCurrentTalkinkTime);
        if(mCurrentTalkinkTime > mTalkingTimes)
            return;
        for(;;) {
            int randomInt = random.nextInt(mTalkingAskStrings.length);
            Log.info(LogTag,"startTalking randomInt=" + randomInt);
            if(!mHashSet.contains(randomInt)) {
                mHashSet.add(randomInt + "");
                mAllbearTts.startTts(mTalkingAskStrings[randomInt]);
                break;
            }
        }
    }

    @Override
    public void onTtsListenerCompleted() {
        super.onTtsListenerCompleted();
        mAllbearIat.startIatNoUI("mandarin" ,mCurrentTalkinkTime + "");
    }

    //6、对话键
    private void buttonLongPressUp6(){
        mAllbearIat.stopIat();
    }
    private void buttonLongPress6(){
        mAllbearIat.startIatNoUI("mandarin");
    }
    private void buttonOnClick6(){

    }
    @Override
    public void onMediaPlayerCompletion() {
        super.onMediaPlayerCompletion();
        Log.info(LogTag,"onMediaPlayerCompletion");
        mMediaplayerState = 0;
    }
    @Override
    public void onTranslateText(String text) {
        super.onTranslateText(text);
        Log.info(LogTag,"onTranslateText text =" + text);
        mAllbearTts.startTts(text);
    }
    @Override
    public void onTulingTalkText(String text) {
        super.onTulingTalkText(text);
        Log.info(LogTag,"onTulingTalkText text =" + text);
        mAllbearTts.startTts(text);
    }
    @Override
    public void onIatGetResultLast(String text) {
        super.onIatGetResultLast(text);
        Log.info(LogTag,"onIatGetResultText text=" + text + "  mNowPressButton=" + mNowPressButton);
        if(mNowPressButton == 0) {
            mMediaPlayer.startPlay(mCommandResult[getCommandResult(text)]);
            mMediaplayerState = 1;
        }else if(mNowPressButton == 1){
            if(text.length() > 1) {
                mAllbearTranslate.startTranslate(text);
            }
        }else if(mNowPressButton == 5){
            mHandler.postDelayed(mStartTalkingRunnable,1000);
        }else if(mNowPressButton == 6){
            if(text.length() > 1) {
                mAllbearTulingTalk.startTulingTalk(text);
            }
        }
    }
    @Override
    public void buttonLongPressUp(int i){
        int id = getIdByViewId(i);
        Log.info(LogTag,"buttonLongPressUp id=" + id);
        switch (id){
            case 0:
                buttonLongPressUp0();
                break;
            case 1:
                buttonLongPressUp1();
                break;
            case 2:
                buttonLongPressUp2();
                break;
            case 3:
                buttonLongPressUp3();
                break;
            case 4:
                buttonLongPressUp4();
                break;
            case 5:
                buttonLongPressUp5();
                break;
            case 6:
                buttonLongPressUp6();
                break;
        }
    }
    @Override
    public void buttonLongPress(int i){
        int id = getIdByViewId(i);
        Log.info(LogTag,"buttonLongPress id=" + id);
        mNowPressButton = id;
        switch (id){
            case 0:
                buttonLongPress0();
                break;
            case 1:
                buttonLongPress1();
                break;
            case 2:
                buttonLongPress2();
                break;
            case 3:
                buttonLongPress3();
                break;
            case 4:
                buttonLongPress4();
                break;
            case 5:
                buttonLongPress5();
                break;
            case 6:
                buttonLongPress6();
                break;
        }
    }
    private void onClick(int id){
        Log.info(LogTag,"onClick id=" + id);
//        mAllbearTts.startTts("小主人：我没听懂你说的是什么，请再说一次！");
        mNowPressButton = id;
        switch (id){
            case 0:
                buttonOnClick0();
                break;
            case 1:
                buttonOnClick1();
                break;
            case 2:
                buttonOnClick2();
                break;
            case 3:
                buttonOnClick3();
                break;
            case 4:
                buttonOnClick4();
                break;
            case 5:
                buttonOnClick5();
                break;
            case 6:
                buttonOnClick6();
                break;
        }
    }
}

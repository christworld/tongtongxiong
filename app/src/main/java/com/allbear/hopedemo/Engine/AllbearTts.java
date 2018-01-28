package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.os.Bundle;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MainActivity;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.Util;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AllbearTts {
    private static String LogTag = "HopeAllbearTts";
    private static AllbearTts mAllbearTts = null;
    private static Context mContext;
    private TtsSynthesizerListener mTtsSynthesizerListener = null ;

    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";
    private String mSpeed = "50";//;

    private AllbearTts(Context context) {
        mContext = context;
        init();
    }
    public static AllbearTts getInstance(Context context) {
        if (mAllbearTts == null) {
            synchronized (AllbearTts.class) {
                if (mAllbearTts == null) {
                    mAllbearTts = new AllbearTts(context);
                }
            }
        }
        return mAllbearTts;
    }
    public void setTtsSynthesizerListener(TtsSynthesizerListener callback){
        mTtsSynthesizerListener = callback;
    }
    private void init(){
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
//        Log.info(LogTag,"startTts mTts =" + mTts);
        setLanguage("us");
    }
    public void destory(){
        mTts.stopSpeaking();
        mTts.destroy();
    }
    private void setLanguage(String lang){
        if(lang.contains("ch")){
            voicer = "xiaoyan";
            mSpeed = "50";
        }else{
            voicer = "catherine";
            mSpeed = "1";
        }
    }
    public void startTts(String text){
        if(text == null || text.equals("")){
            return;
        }
        AllbearEngineBase.getInstance().stopAllEngine();
        setLangTtsStrings(text);

        if(mTts == null){
            mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
        }
//        Log.info(LogTag,"startTts mTts =" + mTts);
        if(mLangTtsStrings.size()>0) {
            startLangTtsStrings(mLangTtsStrings.get(mLangTtsStringsIndex++));
        }
    }
    private void startLangTtsStrings(LangTtsString langString){
        AllbearEngineBase.getInstance().stopAllEngine();
        setLanguage(langString.lang);
        FlowerCollector.onEvent(mContext, "tts_play");
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(langString.ttsString, mTtsListener);
    }
    public void stopTts(){
        if(mTts != null) {
            mTts.stopSpeaking();
        }
    }
    public void pauseTts(){
        if(mTts != null) {
            mTts.pauseSpeaking();
        }
    }
    public void resumeTts(){
        if(mTts != null) {
            mTts.resumeSpeaking();
        }
    }
    /**
     * 参数设置
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        // 引擎类型
        String mEngineType = SpeechConstant.TYPE_CLOUD;
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSpeed);
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "100");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, FileUtil.getSavePath() +"/msc/tts.wav");
    }
    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.info(LogTag,"InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.info(LogTag,"初始化失败,错误码："+code);
//                showTip("初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
                if(mLangTtsStringsIndex < mLangTtsStrings.size()){
                    startLangTtsStrings(mLangTtsStrings.get(mLangTtsStringsIndex++));
                }else {
                    if (mTtsSynthesizerListener != null) {
                        mTtsSynthesizerListener.onTtsListenerCompleted();
                    } else {
                        MainActivity.instance.onTtsListenerCompleted();
                    }
                }
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
            }
            setLanguage("us");
        }
        @Override
        public void onSpeakBegin() {
        }
        @Override
        public void onSpeakPaused() {
        }
        @Override
        public void onSpeakResumed() {
        }
        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }
        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };
    class LangTtsString{
        public String lang;
        public String ttsString;

        @Override
        public String toString() {
            return "LangTtsString{" +
                    "lang='" + lang + '\'' +
                    ", ttsString='" + ttsString + '\'' +
                    '}';
        }
    }
    private int mLangTtsStringsIndex = 0;
    private List<LangTtsString> mLangTtsStrings = new ArrayList<LangTtsString>();
    private void setLangTtsStrings(String str){
        mLangTtsStrings.clear();
        mLangTtsStringsIndex = 0;
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;i++){
//            Log.info(LogTag,"setLangTtsStrings：chars[i]=" + chars[i]);
            if(Util.isEnglishChars(chars[i]+"")){
                String EnglishStr = chars[i]+"";
                for(i++;i<chars.length;i++){
                    if(Util.isChineseChars(chars[i]+"")){
                        i--;
                        break;
                    }
                    EnglishStr += chars[i]+"";
                }
                LangTtsString langStr = new LangTtsString();
                langStr.lang = "us";
                langStr.ttsString = EnglishStr;
                mLangTtsStrings.add(langStr);
            }else{
                String ChineseStr = chars[i]+"";
                for(i++;i<chars.length;i++){
                    if(Util.isEnglishChars(chars[i]+"")){
                        i--;
                        break;
                    }
                    ChineseStr += chars[i]+"";
                }
                LangTtsString langStr = new LangTtsString();
                langStr.lang = "ch";
                langStr.ttsString = ChineseStr;
                mLangTtsStrings.add(langStr);
            }
        }
    }
}

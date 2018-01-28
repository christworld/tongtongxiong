package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MainActivity;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.JsonParser;
import com.allbear.hopedemo.util.Util;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AllbearIat {
    static String LogTag = "HopeAllbearIat";
    private static AllbearIat mAllbearIat = null;
    private static Context mContext;
    private Toast mToast;

    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private IatRecogListener mIatRecogListener = null ;

    private AllbearIat(Context context) {
        mContext = context;
        init();
    }
    public static AllbearIat getInstance(Context context) {
        if (mAllbearIat == null) {
            synchronized (AllbearTts.class) {
                if (mAllbearIat == null) {
                    mAllbearIat = new AllbearIat(context);
                }
            }
        }
        return mAllbearIat;
    }
    public void setIatRecogListener(IatRecogListener callback) {
        this.mIatRecogListener = callback;
    }
    private void init(){
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
//        Log.info(LogTag,"startmIat mIat =" + mIat);
//        Log.info(LogTag,"startmIat mContext =" + mContext);
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
    public void destory(){
        stopIat();
        mIat.destroy();
    }
    public void startIat(String lag){
        if(mIat == null){
            return;
        }
        FlowerCollector.onEvent(mContext, "iat_recognize");
        mIatResults.clear();
        // 设置参数
        setParam(lag);
        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
    }
    public void startIatNoUI(String lag){
        if(mIat == null){
            return;
        }
        FlowerCollector.onEvent(mContext, "iat_recognize");
        mIatResults.clear();
        // 设置参数
        setParam(lag);
        int ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            showTip("听写失败,错误码：" + ret);
        } else {
            showTip("请开始说话…");
        }
    }
    public void startIatNoUI(String lag ,String name){
        if(mIat == null){
            return;
        }
        FlowerCollector.onEvent(mContext, "iat_recognize");
        mIatResults.clear();
        // 设置参数
        setParam(lag);
        setParamAudioName(name);
        int ret = mIat.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            showTip("听写失败,错误码：" + ret);
        } else {
            showTip("请开始说话…");
        }
    }

    private boolean isChineseIatDone = false;
    private String mChineseResult = "";

    public void startIatNoUI(){
        if(mIat == null){
            return;
        }
        isChineseIatDone = false;
        mChineseResult = "";
        FlowerCollector.onEvent(mContext, "iat_recognize");
        mIatResults.clear();
        // 设置参数
        setParam("zh_cn");
        int ret = mIat.startListening(mRecognizerListener);
    }
    public void startIatEn(){
        String filePath = FileUtil.getSavePath() +"/msc/iat.wav";
        Log.info(LogTag,"startIseSelf：filePath=" + filePath + FileUtil.isPathFileExist(filePath));
        byte[] audioData = Util.readAudioFile(filePath);
        writeaudio(audioData);
    }
    public void writeaudio(byte[] buffers) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setParam("en_us");
//                mIat.setParameter(SpeechConstant.DOMAIN, "iat");
                mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
                // 设置多个候选结果
//                mIat.setParameter(SpeechConstant.ASR_NBEST, "3");
//                mIat.setParameter(SpeechConstant.ASR_WBEST, "3");

                mIat.startListening(mRecognizerListener);
                try {
                    mIat.writeAudio(buffers, 0,
                            buffers.length);
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mIat.stopListening();
            }

        }).start();
    }
    public void stopIat(){
        if(mIat != null) {
            mIat.stopListening();
        }
    }
    public void cancelIat(){
        mIat.cancel();
    }
//    public void resumeTts(){
//        mTts.resumeSpeaking();
//    }
    private void printResult(RecognizerResult results, boolean isLast) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        Log.info(LogTag,resultBuffer.toString()+ isLast);

        if(isLast && mIatRecogListener != null){
            if(isChineseIatDone == false){
                isChineseIatDone = true;
                mChineseResult = resultBuffer.toString();
                startIatEn();
            }
            mIatRecogListener.onIatRecogResult(resultBuffer.toString(),mChineseResult,isLast);
        }
    }

    public void setParam(String lag) {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        String mEngineType = SpeechConstant.TYPE_CLOUD;
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        if (lag.equals("en_us")|| lag.equals("")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "3000");
        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, FileUtil.getSavePath() +"/msc/iat.wav");
    }
    public void setParamAudioName(String name) {
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, FileUtil.getSavePath() + "/msc/" + name + ".wav");
    }
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.info(LogTag,"SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
    //                showTip("初始化失败，错误码：" + code);
            }
        }
    };
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results,isLast);
        }
        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            Log.info(LogTag,error.getPlainDescription(true));
            mIatDialog.dismiss();
            showTip(error.getPlainDescription(true));
        }
    };
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
            if(mIatRecogListener != null){
                mIatRecogListener.onError(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results,isLast);
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
}


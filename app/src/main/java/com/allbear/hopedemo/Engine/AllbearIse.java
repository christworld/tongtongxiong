package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.allbear.hopedemo.IseResult.Result;
import com.allbear.hopedemo.IseResult.xml.XmlResultParser;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.Util;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/10.
 */

public class AllbearIse {
    static String LogTag = "HopeAllbearIse";
    private static AllbearIse mAllbearIse = null;
    private static Context mContext;
    private Toast mToast;

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;
    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;

    private String mLastResult;
    private SpeechEvaluator mIse;

    private IseRecogListener mIseRecogListener = null;
    public void setIseRecogListener(IseRecogListener callback){
        mIseRecogListener = callback;
    }
    private AllbearIse(Context context) {
        mContext = context;
        init();
    }
    public static AllbearIse getInstance(Context context) {
        if (mAllbearIse == null) {
            synchronized (AllbearIse.class) {
                if (mAllbearIse == null) {
                    mAllbearIse = new AllbearIse(context);
                }
            }
        }
        return mAllbearIse;
    }
    private void init(){
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        mIse = SpeechEvaluator.createEvaluator(mContext, null);
        Log.info(LogTag,"init mIse :" + mIse);
    }
    private void showTip(String str) {
        if(!TextUtils.isEmpty(str)) {
            mToast.setText(str);
            mToast.show();
        }
    }
    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {
        @Override
        public void onResult(EvaluatorResult xmlresult, boolean isLast) {
            Log.info(LogTag,"evaluator result :" + isLast);
            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(xmlresult.getResultString());

                if(!TextUtils.isEmpty(builder)) {
//                    Log.info(LogTag, builder.toString());
                }
//                mIseStartButton.setEnabled(true);
                mLastResult = builder.toString();
                if (!TextUtils.isEmpty(mLastResult)) {
                    XmlResultParser resultParser = new XmlResultParser();
                    Result result = resultParser.parse(mLastResult);
                    if (null != result) {
                        if(mIseRecogListener != null){
                            mIseRecogListener.onIseRecogScoreResult(result.total_score);
                            mIseRecogListener.onIseRecogResult("评测内容 :" + result.content + " \n总得分:" + "TotalScore = " + result.total_score);
                        }
                        Log.info(LogTag,"评测内容 :" + result.content + " \n总得分:" + "TotalScore = " + result.total_score);
                        Log.info(LogTag,result.toString());
                    } else {
                        Log.info(LogTag,"解析结果为空");
                    }
                }
                Log.info(LogTag,"评测结束");
                //评测结束停止评测
                if (mIse.isEvaluating()) {
//                    mResultEditText.setHint("评测已停止，等待结果中...");
                    mIse.stopEvaluating();
                }
            }
        }

        @Override
        public void onError(SpeechError error) {
//            mIseStartButton.setEnabled(true);
            if(error != null) {
                showTip("error:"+ error.getErrorCode() + "," + error.getErrorDescription());
//                mResultEditText.setText("");
//                mResultEditText.setHint("请点击“开始评测”按钮");
            } else {
                Log.info(LogTag, "evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("evaluator stoped");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前音量：" + volume);
//            Log.info(LogTag, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
    private void setParams() {
        SharedPreferences pref = mContext.getSharedPreferences(PREFER_NAME, mContext.MODE_PRIVATE);
        // 设置评测语言
        language = pref.getString(SpeechConstant.LANGUAGE, "en_us");
        // 设置需要评测的类型
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
        // 设置结果等级（中文仅支持complete）
        result_level = pref.getString(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = pref.getString(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = pref.getString(SpeechConstant.VAD_EOS, "1800");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "/tts/ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
//        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"1");
    }
    public void stopIse(){
        if(mIse != null){
            mIse.stopEvaluating();
        }
    }
    public void startIse(String evaText){
        Log.info(LogTag,"startIse：evaText=" + evaText);
        setParams();
        int ret = mIse.startEvaluating(evaText, null, mEvaluatorListener);
        Log.info(LogTag,"startIse：ret=" + ret);
    }
    public void startIseSelf(String filePath){
        Log.info(LogTag,"startIseSelf：filePath=" + filePath + FileUtil.isPathFileExist(filePath));
        setParams();
        //通过writeaudio方式直接写入音频时才需要此设置
        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"1");
        int ret = mIse.startEvaluating("The quick brown fox jumps over the lazy dog", null, mEvaluatorListener);
        Log.info(LogTag,"startIse：ret=" + ret);
        byte[] audioData = Util.readAudioFile(filePath);
        if(audioData != null) {
            //防止写入音频过早导致失败
            try{
                new Thread().sleep(100);
            }catch (InterruptedException e) {
                Log.info(LogTag,"InterruptedException :"+e);
            }
            boolean bret = mIse.writeAudio(audioData,0,audioData.length);
            Log.info(LogTag,"startIseSelf：bret=" + bret);
//            mIse.stopEvaluating();
        }
//        Log.info(LogTag,"startIse：ret=" + ret);
    }
}

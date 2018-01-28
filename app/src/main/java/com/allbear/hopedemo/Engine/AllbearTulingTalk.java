package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MainActivity;
import com.allbear.hopedemo.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/4/23.
 */

public class AllbearTulingTalk {
    private static String LogTag = "HopeAllbearTulingTalk";
    private AllbearTulingTalk(Context context) {
        mContext = context;
        init();
    }
    private static AllbearTulingTalk mAllbearTulingTalk = null;
    private static Context mContext;
    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;
    private String mTranslateText = null;
    private HttpURLConnection mHttpURLConnection = null;
    private String  httpAPI="http://www.tuling123.com/openapi/api?key=da203166383849b595a80d261f191175&info=";

    public static AllbearTulingTalk getInstance(Context context) {
        if (mAllbearTulingTalk == null) {
            synchronized (AllbearMediaplayer.class) {
                if (mAllbearTulingTalk == null) {
                    mAllbearTulingTalk = new AllbearTulingTalk(context);
                }
            }
        }
        return mAllbearTulingTalk;
    }

    public void destory(){

    }
    private void init(){

    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.info(LogTag,"handleMessage msg.what=" + msg.what);
            switch (msg.what) {
                case SUCCESS:
                    JSONAnalysis(msg.obj.toString());
                    break;
                case FAILURE:
                    break;
                case ERRORCODE:
                    break;
                default:
                    break;
            }
        };
    };
    /**
     * JSON解析方法
     */
    protected void JSONAnalysis(String string) {
        JSONObject object = null;
        String tulingResult=null;
        try {
            object = new JSONObject(string);
            tulingResult = object.getString("text").trim();
//            tulingResult = tulingResult.substring(2,tulingResult.length()-2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainActivity.instance.onTulingTalkText(tulingResult);
        Log.info(LogTag,"JSONAnalysis tulingResult =" + tulingResult);
//        mTranslateText ="";
//        result.setText(tulingResult);
    }

    private Thread postThread = new Thread() {

        public void run() {
            Log.info(LogTag,"postThread run");
            int code;
            try {
                String text = mTranslateText;
                URL url = new URL(httpAPI.concat(java.net.URLEncoder.encode(text, "utf-8")));
                /**
                 * 这里网络请求使用的是类HttpURLConnection，另外一种可以选择使用类HttpClient。
                 */
                mHttpURLConnection = (HttpURLConnection) url.openConnection();
                mHttpURLConnection.setRequestMethod("GET");//使用GET方法获取
                mHttpURLConnection.setConnectTimeout(3000);
                code = mHttpURLConnection.getResponseCode();
                Log.info(LogTag,"postThread msg.code=" + code);
                if (code == 200) {
                    /**
                     * 如果获取的code为200，则证明数据获取是正确的。
                     */
                    InputStream is = mHttpURLConnection.getInputStream();
                    String result = HttpUtils.readMyInputStream(is);

                    /**
                     * 子线程发送消息到主线程，并将获取的结果带到主线程，让主线程来更新UI。
                     */
                    //   result.setText(result1);
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = ERRORCODE;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                /**
                 * 如果获取失败，或出现异常，那么子线程发送失败的消息（FAILURE）到主线程，主线程显示Toast，来告诉使用者，数据获取是失败。
                 */
                Message msg = new Message();
                msg.what = FAILURE;
                handler.sendMessage(msg);
            }finally {
                if(mHttpURLConnection != null){
                    mHttpURLConnection.disconnect();
                }
            }
        };
    };
    public void onExit(){
        if(mHttpURLConnection != null){
            mHttpURLConnection.disconnect();
        }
    }
    public void  startTulingTalk(String text){
        Log.info(LogTag,"startTulingTalk text =" + text);
        mTranslateText = text;
        new Thread(postThread).start();
    }
}


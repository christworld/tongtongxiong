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
 * Created by Administrator on 2017/3/26.
 */

public class AllbearTranslate {
    private static String LogTag = "HopeAllbearTranslate";

    private AllbearTranslate(Context context) {
        mContext = context;
        init();
    }
    private static AllbearTranslate mAllbearTranslate = null;
    private static Context mContext;
    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;
    private String mTranslateText = null;
    private HttpURLConnection mHttpURLConnection = null;
    private TranslateTextListener mTranslateTextListener = null;
    private String  httpAPI="http://fanyi.youdao.com/openapi.do?keyfrom=tongtongxiong1&key=1296041950&type=data&doctype=json&version=1.1&q=";

    public static AllbearTranslate getInstance(Context context) {
        if (mAllbearTranslate == null) {
            synchronized (AllbearTranslate.class) {
                if (mAllbearTranslate == null) {
                    mAllbearTranslate = new AllbearTranslate(context);
                }
            }
        }
        return mAllbearTranslate;
    }

    public void destory(){

    }
    private void init(){

    }
    public void setTranslateTextListener(TranslateTextListener callback){
        mTranslateTextListener = callback;
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
        String translationResult=null;
        try {
            object = new JSONObject(string);
            translationResult = object.getString("translation");
            translationResult = translationResult.substring(2,translationResult.length()-2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(mTranslateTextListener != null){
            mTranslateTextListener.onTranslateResult(translationResult);
        }else {
            MainActivity.instance.onTranslateText(translationResult);
        }
        Log.info(LogTag,"JSONAnalysis translationResult =" + translationResult);
//        mTranslateText ="";
//        result.setText(translationResult);
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
                mHttpURLConnection.setConnectTimeout(5000);
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
    public void  startTranslate(String text){
        Log.info(LogTag,"startTranslate text =" + text);
        mTranslateText = text;
        new Thread(postThread).start();
    }
}

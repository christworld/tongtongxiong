package com.allbear.hopedemo.Engine;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MainActivity;
import com.allbear.hopedemo.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AllbearWeather {
    private static String LogTag = "HopeAllbearWeather";

    private AllbearWeather(Context context) {
        mContext = context;
        init();
    }
    private static AllbearWeather mAllbearWeather = null;
    private static Context mContext;
    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;
    private String mCity = null;
    private HttpURLConnection mHttpURLConnection = null;
    private String  httpAPIStart="http://v.juhe.cn/weather/index?cityname=";
    private String  httpAPIEnd="&dtype=&format=&key=28e32f033f4089180f1838506c3fc1bd";
    private WeatherListener mWeatherListener = null;
    private ArrayMap<String,String> mWeatherMap = new ArrayMap<String,String>();
    private String [] mWeatherIndex = {
            "temperature",
            "weather",
            "wind",
            "week",
            "city",
            "date_y",
            "dressing_index",
            "dressing_advice",
            "uv_index",
            "comfort_index",
            "wash_index",
            "travel_index",
            "exercise_index",
            "drying_index"
    };

    public static AllbearWeather getInstance(Context context) {
        if (mAllbearWeather == null) {
            synchronized (AllbearWeather.class) {
                if (mAllbearWeather == null) {
                    mAllbearWeather = new AllbearWeather(context);
                }
            }
        }
        return mAllbearWeather;
    }
    public void setWeatherListener(WeatherListener callback){
        mWeatherListener = callback;
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
        Log.info(LogTag,"JSONAnalysis string =" + string);
        JSONObject object = null;
        String weatherResult=null;
        try {
            object = new JSONObject(string);
            JSONObject weatherJson = new JSONObject(new JSONObject(object.getString("result")).getString("today"));
            if(weatherJson != null){
                for(int i=0;i<mWeatherIndex.length;i++) {
                    mWeatherMap.put(mWeatherIndex[i],weatherJson.getString(mWeatherIndex[i]));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<mWeatherIndex.length;i++) {
            Log.info(LogTag,"JSONAnalysis weatherResult =" + mWeatherMap.get(mWeatherIndex[i]));
        }
        if(mWeatherListener != null) {
            mWeatherListener.onWeatherResult(mWeatherMap);
        }
    }
    public ArrayMap<String,String> getWeatherMap(){
        return mWeatherMap;
    }
    private Thread postThread = new Thread() {

        public void run() {
            Log.info(LogTag,"postThread run");
            int code;
            try {
                String text = mCity;
                URL url = new URL(httpAPIStart.concat(java.net.URLEncoder.encode(text, "utf-8")).concat(httpAPIEnd));
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
    public void getWeather(String city){
        mCity = city;
        Log.info(LogTag,"startTranslate text =" + mCity);
        new Thread(postThread).start();
    }
}

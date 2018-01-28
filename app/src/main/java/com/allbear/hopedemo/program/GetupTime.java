package com.allbear.hopedemo.program;

import android.database.Cursor;
import android.os.Handler;
import android.util.ArrayMap;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Engine.IatRecogListener;
import com.allbear.hopedemo.Engine.TtsSynthesizerListener;
import com.allbear.hopedemo.Engine.WeatherListener;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/24.
 */

public class GetupTime extends ProgramBase{
    private static String LogTag = "HopeGetupTime";
    private static GetupTime instance = null;

    public static GetupTime getInstance() {
        if (instance == null) {
            instance = new GetupTime();
        }
        return instance;
    }
    public GetupTime(){
        initWeatherInfo();
        mAllbearTts.setTtsSynthesizerListener(mTtsSynthesizerListener);
    }

    @Override
    public void doProgramTime(){
        int weekIndex = Util.getWeekIndex()-1;
        Log.info(LogTag,"doGetUpCall..............weekIndex=." + weekIndex);
        String getupWeekCall = Const.mGetUpWeekCall[weekIndex];
        mAllbearTts.startTts(getupWeekCall);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAllbearTts.startTts(getWeatherInfo());
            }
        },11000);
    }

    private void initWeatherInfo(){
        mAllbearWeather.setWeatherListener(mWeatherListener);
        mCity = doGetCity();
        Log.info(LogTag,"initWeatherInfo  mCity=" + mCity);
        mAllbearWeather.getWeather(mCity);
    }
    protected String getWeatherInfo(){
        String weatherInfo = "";
        if(!mWeatherMap.isEmpty()){
            weatherInfo += "今天" + mCity + "天气：" + mWeatherMap.get("weather") +
                    " 。温度是" + mWeatherMap.get("temperature") +
                    " ，" + mWeatherMap.get("wind") +
                    " ，" + mWeatherMap.get("dressing_index") ;
        }
        return weatherInfo;
    }
}

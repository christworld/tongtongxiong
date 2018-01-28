package com.allbear.hopedemo.program;

import android.util.ArrayMap;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Engine.TranslateTextListener;
import com.allbear.hopedemo.Engine.WeatherListener;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class HoliGetUpTime extends ProgramBase {
    private static String LogTag = "HopeHoliGetUpTime";
    private static HoliGetUpTime instance = null;
    public static HoliGetUpTime getInstance() {
        if (instance == null) {
            instance = new HoliGetUpTime();
        }
        return instance;
    }
    private HoliGetUpTime(){
        mWeatherMap = mAllbearWeather.getWeatherMap();
        mAllbearTranslate.setTranslateTextListener(mTranslateTextListener);
    }

    @Override
    protected void onTransResult(String result) {
        mAllbearTts.startTts(result);
        super.onTransResult(result);
    }

    @Override
    public void doProgramTime(){
        int weekIndex = Util.getWeekIndex()-1;
        Log.info(LogTag,"doGetUpCall..............weekIndex=." + weekIndex);
        String getupWeekCall = "今天是节日！" + Const.mGetUpWeekCall[weekIndex];
        initWeatherInfo();
        
//        if(!mWeatherMap.isEmpty()){
//            String weathinfo = getWeatherInfo();
//            Log.info(LogTag,"doGetUpCall..............weathinfo=." + weathinfo);
//            mAllbearTts.startTts(weathinfo);
//        }else {
//            initWeatherInfo();
//        }
    }

    @Override
    protected void onGetWeatherResult(ArrayMap<String, String> result) {
        mAllbearTranslate.startTranslate(getWeatherInfo());
        super.onGetWeatherResult(result);
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
                    " 。温度：" + mWeatherMap.get("temperature") +
                    " ，" + mWeatherMap.get("wind") +
                    " ，" + mWeatherMap.get("dressing_index") ;
        }
        return weatherInfo;
    }
}


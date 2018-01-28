package com.allbear.hopedemo.Engine;

import android.util.ArrayMap;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface WeatherListener {
    void onWeatherResult(ArrayMap<String,String> result);
}

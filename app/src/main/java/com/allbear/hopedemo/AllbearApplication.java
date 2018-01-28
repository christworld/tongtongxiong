package com.allbear.hopedemo;

import android.app.Application;
import android.util.Log;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.util.Util;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AllbearApplication extends Application {

    @Override
    public void onCreate() {
        SpeechUtility speechUtility = SpeechUtility.createUtility(this, "appid=58c53820");
        Log.i("HopeAllbearApp"," speechUtility=" + speechUtility);
        super.onCreate();
        Util.setPackageContext(getApplicationContext());

        int min = Calendar.getInstance().get(Calendar.MINUTE);
        Log.i("HopeLog","Min = " + ((min%3)+11-10));
        Const.setUserId(((min%3)+11-10));
    }

}

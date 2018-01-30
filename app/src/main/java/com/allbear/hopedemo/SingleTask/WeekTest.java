package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class WeekTest extends SingleTaskBase {
    private static String LogTag = "HopeWeekTest";
    private static WeekTest instance = null;
    public static WeekTest getInstance() {
        if (instance == null) {
            instance = new WeekTest();
        }
        return instance;
    }
    public WeekTest(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}


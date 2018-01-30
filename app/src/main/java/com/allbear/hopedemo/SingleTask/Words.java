package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Words extends SingleTaskBase {
    private static String LogTag = "HopeWords";
    private static Words instance = null;
    public static Words getInstance() {
        if (instance == null) {
            instance = new Words();
        }
        return instance;
    }
    public Words(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

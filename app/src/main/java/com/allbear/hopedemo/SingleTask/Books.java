package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Books extends SingleTaskBase {
    private static String LogTag = "HopeBooks";
    private static Books instance = null;
    public static Books getInstance() {
        if (instance == null) {
            instance = new Books();
        }
        return instance;
    }
    public Books(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Talk extends SingleTaskBase {
    private static String LogTag = "HopeTalk";
    private static Talk instance = null;
    public static Talk getInstance() {
        if (instance == null) {
            instance = new Talk();
        }
        return instance;
    }
    public Talk(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

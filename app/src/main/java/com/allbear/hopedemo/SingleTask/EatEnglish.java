package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class EatEnglish extends SingleTaskBase {
    private static String LogTag = "HopeEatEnglish";
    private static EatEnglish instance = null;
    public static EatEnglish getInstance() {
        if (instance == null) {
            instance = new EatEnglish();
        }
        return instance;
    }
    public EatEnglish(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

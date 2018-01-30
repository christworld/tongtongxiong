package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MorningEnglish extends SingleTaskBase {
    private static String LogTag = "HopeMorningEnglish";
    private static MorningEnglish instance = null;
    public static MorningEnglish getInstance() {
        if (instance == null) {
            instance = new MorningEnglish();
        }
        return instance;
    }
    public MorningEnglish(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

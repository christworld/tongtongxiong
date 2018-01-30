package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class EnglishFAQ extends SingleTaskBase {
    private static String LogTag = "HopeEnglishFAQ";
    private static EnglishFAQ instance = null;
    public static EnglishFAQ getInstance() {
        if (instance == null) {
            instance = new EnglishFAQ();
        }
        return instance;
    }
    public EnglishFAQ(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

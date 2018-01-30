package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class FollowRead extends SingleTaskBase {
    private static String LogTag = "HopeFollowRead";
    private static FollowRead instance = null;
    public static FollowRead getInstance() {
        if (instance == null) {
            instance = new FollowRead();
        }
        return instance;
    }
    public FollowRead(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

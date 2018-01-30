package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class SceneEnglish extends SingleTaskBase {
    private static String LogTag = "HopeSceneEnglish";
    private static SceneEnglish instance = null;
    public static SceneEnglish getInstance() {
        if (instance == null) {
            instance = new SceneEnglish();
        }
        return instance;
    }
    public SceneEnglish(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");

    }
}

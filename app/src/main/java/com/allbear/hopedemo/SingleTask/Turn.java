package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.program.ProgramBase;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2018/1/30.
 */

public class Turn extends SingleTaskBase {
    private static String LogTag = "HopeTurn";
    private static Turn instance = null;
    public static Turn getInstance() {
        if (instance == null) {
            instance = new Turn();
        }
        return instance;
    }
    public Turn(){}

    @Override
    public void doProgramTime() {
        Log.info(LogTag, "doProgramTime");
        mMediaPlayer.startPlay("Sound/wrong.wav");
    }
}

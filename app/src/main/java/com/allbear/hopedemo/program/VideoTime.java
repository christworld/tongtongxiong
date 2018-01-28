package com.allbear.hopedemo.program;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class VideoTime extends ProgramBase {
    private static String LogTag = "HopeVideoTime";
    private static VideoTime instance = null;
    public static VideoTime getInstance() {
        if (instance == null) {
            instance = new VideoTime();
        }
        return instance;
    }
    public VideoTime(){
    }

    @Override
    public void doProgramTime(){
        int weekIndex = Util.getWeekIndex()-1;
        Log.info(LogTag,"doGetUpCall..............weekIndex=." + weekIndex);
        String getupWeekCall = "现在是视频时间，让我们看视频吧！";
        mAllbearTts.startTts(getupWeekCall);
    }
}

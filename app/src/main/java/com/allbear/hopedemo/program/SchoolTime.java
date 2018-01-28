package com.allbear.hopedemo.program;

import android.os.Environment;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class SchoolTime extends ProgramBase {
    private static String LogTag = "HopeSchoolTime";
    private static SchoolTime instance = null;
    public static SchoolTime getInstance() {
        if (instance == null) {
            instance = new SchoolTime();
        }
        return instance;
    }
    public SchoolTime(){
    }

    @Override
    public void doProgramTime(){
        int weekIndex = Util.getWeekIndex()-1;
        Log.info(LogTag,"doSchoolCall..............weekIndex=." + weekIndex);
//        String getupWeekCall = "现在是上学时间，上学了！" ;
//        mAllbearTts.startTts(getupWeekCall);
        String evaText = "Good morning, good morning Sunday morning";
//        mAllbearIse.startIse(evaText);
//        mAllbearIse.startIseSelf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ise.wav");
        mAllbearIat.startIatNoUI();

    }
}

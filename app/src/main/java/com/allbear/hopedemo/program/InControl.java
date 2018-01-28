package com.allbear.hopedemo.program;

import android.os.Handler;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearMediaplayer;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class InControl {
    private static String LogTag = "HopeInControl";
    private static InControl instance = null;
    public static InControl getInstance() {
        if (instance == null) {
            instance = new InControl();
        }
        return instance;
    }
    public InControl(){
        mMediaPlayer = AllbearMediaplayer.getInstance(Util.getMainActivityContext());
    }
    protected AllbearMediaplayer mMediaPlayer;

    private ProgramBase [] mProgramBases = {
            GetupTime.getInstance(),
            HoliGetUpTime.getInstance(),
            SchoolTime.getInstance(),
            BookTime.getInstance(),
            VideoTime.getInstance(),
            OralTime.getInstance(),
            SleepTime.getInstance()
    };

    public void doInControl(int index){
        mProgramBases[index].doProgramTime();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String path = FileUtil.getSavePath() + "/ttx/huiben_mp3/hba0" + (index+1) +".mp3";
//                Log.info(LogTag,"doInControl " + path + FileUtil.isPathFileExist(path));
//                mMediaPlayer.startPlaySDPath(path);
//            }
//        },13000);
    }
}

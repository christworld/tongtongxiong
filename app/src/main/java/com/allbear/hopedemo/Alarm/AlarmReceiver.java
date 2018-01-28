package com.allbear.hopedemo.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.program.GetupTime;
import com.allbear.hopedemo.program.InControl;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/10/22.
 */

public class AlarmReceiver extends BroadcastReceiver {
    static String LogTag = "HopeAlarmReceiver";

    private AlarmSetTime mAlarmSetTime = AlarmSetTime.getInstance();
    @Override
    public void onReceive(Context context, Intent intent) {
        int indexTime = mAlarmSetTime.mCurrentTimeIndex;//intent.getIntExtra("indexTime",1);
        Log.info(LogTag,"TTX_ALARM_CLOCK..........msg indexTime=" + indexTime);
        Toast.makeText(context,"Hope msg=" + indexTime,Toast.LENGTH_LONG).show();
        startActivity(indexTime);
        mAlarmSetTime.setAlarmTime();
    }

    private void startActivity(int indexTime){
        InControl.getInstance().doInControl(indexTime);
    }
}
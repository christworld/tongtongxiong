package com.allbear.hopedemo.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MySQL.MySQL;
import com.allbear.hopedemo.SQLite.SQLiteHelper;
import com.allbear.hopedemo.Structure.time_sets;
import com.allbear.hopedemo.util.Util;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Administrator on 2017/11/24.
 */

public class AlarmSetTime {
    private static String LogTag = "HopeAlarmSetTime";

    private time_sets mTimeSets;
    protected SQLiteHelper mSQLHelper;

    private static AlarmSetTime instance = null;
    public static AlarmSetTime getInstance() {
        if (instance == null) {
            instance = new AlarmSetTime();
        }
        return instance;
    }
    public AlarmSetTime(){
        mTimeSets = time_sets.getInstance();
        mSQLHelper = SQLiteHelper.getInstance();
        readTimeSets();
    }
    private void readTimeSets() {
        Cursor cursor = mSQLHelper.query("select * from time_sets");
        if(cursor.moveToFirst()) {
            for(int i =0;i<cursor.getColumnCount();i++){
                mTimeSets.mSetTimesStr[i] = cursor.getString(i);
            }
        }
        cursor.close();
    }

    public int mCurrentTimeIndex = 1;
    public Long mCurrentTime = 1L;

    public void setAlarmTime(){
        mCurrentTimeIndex = getCurrentTimeIndex();
        Log.info(LogTag,"setAlarm..............index=." +mCurrentTimeIndex);

        Intent intent = new Intent("TTX_ALARM_CLOCK");
//        intent.putExtra("indexTime",mCurrentTimeIndex);
        PendingIntent pi = PendingIntent.getBroadcast(Util.getPackageContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) Util.getPackageContext().getSystemService(ALARM_SERVICE);

        //设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
        Log.info(LogTag, "mTimeSets.mSetTimesStr[i]=" + mTimeSets.mSetTimesStr[mCurrentTimeIndex]);
        Long Time = mCurrentTime;//System.currentTimeMillis() + 60000*10;
        Log.info(LogTag, "getTimeLong=" + Time);
        if(Time != 0){
            am.set(AlarmManager.RTC_WAKEUP,Time,pi);
        }
    }
    private int getCurrentTimeIndex(){
        Long currentTime = System.currentTimeMillis();
        for(int i = 1;i < mTimeSets.mSetTimesStr.length;i++) {
            Long Time = Util.getTimeLong(mTimeSets.mSetTimesStr[i]);
            if(currentTime < Time){
                mCurrentTime = Time;
                return i;
            }
        }
        mCurrentTime = Util.getTimeLong(mTimeSets.mSetTimesStr[1]) + 3600*24*1000; //第二天起床时间
        Log.info(LogTag,"getCurrentTimeIndex..............mCurrentTime=." +mCurrentTime);
        return 1;
    }
}

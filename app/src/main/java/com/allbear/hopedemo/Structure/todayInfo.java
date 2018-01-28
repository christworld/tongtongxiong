package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/12/25.
 */

public class todayInfo {
    private static todayInfo instance = null;
    private todayInfo() {
        mSQLiteHelper = SQLiteHelper.getInstance();
    }
    public static todayInfo getInstance() {
        if (instance == null) {
            instance = new todayInfo();
        }
        return instance;
    }
    private SQLiteHelper mSQLiteHelper;
    private String mIsHoliday = "";

    public String getIsHoliday(){
        return mIsHoliday;
    }

    public void InitData() {
        String today = Util.getCurrentTime("yyyy/M/d");
        Log.info("Hopeholiday","mBooksData today=" + today);
        Cursor cursor = mSQLiteHelper.query("select comment from holiday where YMD like '" + today + "'");
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                mIsHoliday = cursor.getString(0);
            }
        }
        cursor.close();
    }

    protected String doGetCity() {
        String cityName = "";
        Cursor cursor = mSQLiteHelper.query(Const.sqlSelectCityName);
        if(cursor.moveToFirst()) {
            cityName = cursor.getString(0);
        }
        cursor.close();
        return cityName;
    }
}

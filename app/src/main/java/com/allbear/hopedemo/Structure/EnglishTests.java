package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/1.
 */

public class EnglishTests {
    private static EnglishTests instance = null;
    private EnglishTests() {
        mSQLiteHelper = SQLiteHelper.getInstance();
    }
    public static EnglishTests getInstance() {
        if (instance == null) {
            instance = new EnglishTests();
        }
        return instance;
    }
    private SQLiteHelper mSQLiteHelper;
    private ArrayList<tests_structure> mTestsData = new ArrayList<tests_structure>();
    public void InitData(){
        mTestsData.clear();
        Cursor cursor = mSQLiteHelper.query("select * from tests ");
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                tests_structure test = new tests_structure();
                test.english1 = cursor.getString(cursor.getColumnIndex("english1"));
                test.chinese1 = cursor.getString(cursor.getColumnIndex("chinese1"));
                test.keywords = cursor.getString(cursor.getColumnIndex("keywords"));
                mTestsData.add(test);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.info("HopeEnglishTests","mTestsData =" + mTestsData.size());
        for(int i = 0;i<mTestsData.size();i++){
            Log.info("HopeEnglishTests","mTestsData =" + i +  mTestsData.get(i).english1);
        }
    }
    public int getTestsCount(){
        return mTestsData.size();
    }
    public String getEnglish(int index){
        return mTestsData.get(index).english1;
    }
    public String getChinese(int index){
        return mTestsData.get(index).chinese1;
    }
    public String getKeywords(int index){
        return mTestsData.get(index).keywords;
    }
    class tests_structure {
        public int test_id;
        public int test_cate;
        public int cate_data;
        public int cate_id;
        public String english1;
        public String chinese1;
        public String keywords;
    }
}
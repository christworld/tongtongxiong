package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/3.
 */

public class ask_robot {
    private static ask_robot instance = null;
    private ask_robot() {
        mSQLiteHelper = SQLiteHelper.getInstance();
    }
    public static ask_robot getInstance() {
        if (instance == null) {
            instance = new ask_robot();
        }
        return instance;
    }
    private SQLiteHelper mSQLiteHelper;
    private ArrayList<ask_robot_structure> mAskRobotData = new ArrayList<ask_robot_structure>();
    public void InitData(){
        mAskRobotData.clear();
        mAskRobotData.add(new ask_robot_structure());
        Cursor cursor = mSQLiteHelper.query("select * from ask_robot ");
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                ask_robot_structure askrobot = new ask_robot_structure();
                askrobot.english_question = cursor.getString(cursor.getColumnIndex("english_question"));
                askrobot.chinese_question = cursor.getString(cursor.getColumnIndex("chinese_question"));
                mAskRobotData.add(askrobot);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.info("Hopeaskrobot","mAskRobotData =" + mAskRobotData.size());
        for(int i = 1;i<mAskRobotData.size();i++){
            Log.info("Hopeaskrobot","mAskRobotData =" + i +  mAskRobotData.get(i).english_question);
        }
    }
    public int getAskRobotCount(){
        return mAskRobotData.size();
    }
    public String getEnglishQuestion(int index){
        return mAskRobotData.get(index).english_question;
    }
    public String getChineseQuestion(int index){
        return mAskRobotData.get(index).chinese_question;
    }
    class ask_robot_structure {
        public int ask_robot_id;
        public String catelogy;
        public String english_question;
        public String chinese_question;
        public String key_words;
        public String answer1;
        public String answer1_chi;
        public String answer2;
        public String answer2_chi;
    }
}

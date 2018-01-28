package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/4.
 */

public class ask_children {
    private static ask_children instance = null;
    private ask_children() {
        mSQLiteHelper = SQLiteHelper.getInstance();
//        InitData();
    }
    public static ask_children getInstance() {
        if (instance == null) {
            instance = new ask_children();
        }
        return instance;
    }

    private SQLiteHelper mSQLiteHelper;
    private ArrayList<ask_children_structure> mAskChildrenData = new ArrayList<ask_children_structure>();
    public void InitData(){
        mAskChildrenData.clear();
        Cursor cursor = mSQLiteHelper.query("select * from ask_children ");
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                ask_children_structure ACS = new ask_children_structure();
                ACS.eng_question = cursor.getString(cursor.getColumnIndex("eng_question"));
                ACS.chi_question = cursor.getString(cursor.getColumnIndex("chi_question"));
                ACS.keywords = cursor.getString(cursor.getColumnIndex("keywords"));
                ACS.answer1 = cursor.getString(cursor.getColumnIndex("answer1"));
                ACS.answer1_chi = cursor.getString(cursor.getColumnIndex("answer1_chi"));
                ACS.answer2 = cursor.getString(cursor.getColumnIndex("answer2"));
                ACS.answer2_chi = cursor.getString(cursor.getColumnIndex("answer2_chi"));
                mAskChildrenData.add(ACS);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.info("Hopeaskchildren","mAskChildrenData =" + mAskChildrenData.size());
//        for(int i = 0;i<mAskChildrenData.size();i++){
//            Log.info("Hopeaskchildren","mAskChildrenData =" + i +  mAskChildrenData.get(i).eng_question);
//        }
    }
    public int getQuestionCount(){
        return mAskChildrenData.size();
    }
    public String getEngQuestion(int index){
        return mAskChildrenData.get(index).eng_question;
    }
    public String getChiQuestion(int index){
        return mAskChildrenData.get(index).chi_question;
    }
    public String[] getKeywords(int index){
        String[] str = mAskChildrenData.get(index).keywords.split("/");
        return str;
    }
    public String getAnswer1(int index){
        return mAskChildrenData.get(index).answer1;
    }
    public String getAnswer1Chi(int index){
        return mAskChildrenData.get(index).answer1_chi;
    }
    public String getAnswer2(int index){
        return mAskChildrenData.get(index).answer2;
    }
    public String getAnswer2Chi(int index){
        return mAskChildrenData.get(index).answer2_chi;
    }
    class ask_children_structure{
        int ask_children_id = 0;
        int day_nums = 0;
        int no = 0;
        String eng_question = "";
        String chi_question = "";
        String relative_flag = "";
        String keywords = "";
        String answer1 = "";
        String answer1_chi = "";
        String answer2 = "";
        String answer2_chi = "";
    }
}

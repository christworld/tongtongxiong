package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/12/4.
 */

public class users {
    private static users instance = null;
    private users() {
        mSQLiteHelper = SQLiteHelper.getInstance();
//        InitData();
    }

    public static users getInstance() {
        if (instance == null) {
            instance = new users();
        }
        return instance;
    }

    private SQLiteHelper mSQLiteHelper;
    private user_structure mUserStructure = new user_structure();
    public void InitData() {
        Cursor cursor = mSQLiteHelper.query("select * from users where user_id = " + Const.getUserId());
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                mUserStructure.name = cursor.getString(cursor.getColumnIndex("name"));
                mUserStructure.eng_name = cursor.getString(cursor.getColumnIndex("eng_name"));
                mUserStructure.gender = cursor.getString(cursor.getColumnIndex("gender"));
                mUserStructure.user_birth = cursor.getString(cursor.getColumnIndex("user_birth"));
                mUserStructure.papa_birth = cursor.getString(cursor.getColumnIndex("papa_birth"));
                mUserStructure.mama_birth = cursor.getString(cursor.getColumnIndex("mama_birth"));
            }
        }
        cursor.close();
        Log.info("Hopeusers","mUserStructure =" + mUserStructure.name);
    }
    public String getName(){
        return mUserStructure.name;
    }
    public String getEngName(){
        return mUserStructure.eng_name;
    }
    public String getGender(){
        return mUserStructure.gender;
    }
    public String getUserBirth(){
        return mUserStructure.user_birth;
    }
    public String getPapaBirth(){
        return mUserStructure.papa_birth;
    }
    public String getMamaBirth(){
        return mUserStructure.mama_birth;
    }
    public String getBirthDay(){
        String birth = "";
        if(Util.checkBirthDay(getUserBirth())){
            birth += getEngName();
        }
        if(Util.checkBirthDay(getPapaBirth())){
            if(!birth.equals("")){
                birth +=" and ";
            }
            birth += "Daddy";
        }
        if(Util.checkBirthDay(getMamaBirth())){
            if(!birth.equals("")){
                birth +=" and ";
            }
            birth += "Mummy";
        }
        return birth;
    }
    class user_structure{
        int user_id = 0;
        String name = "";
        String eng_name = "";
        String gender = "";
        String user_birth = "";
        String papa_birth = "";
        String mama_birth = "";
    }
}

package com.allbear.hopedemo.TtxData;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Http.HttpDownload;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MySQL.MySQL;
import com.allbear.hopedemo.MySQL.MySQLUtil;
import com.allbear.hopedemo.Engine.AllbearWeather;
import com.allbear.hopedemo.SQLite.SQLiteHelper;
import com.allbear.hopedemo.Structure.UrlStructure;
import com.allbear.hopedemo.Structure.ask_children;
import com.allbear.hopedemo.Structure.users;
import com.allbear.hopedemo.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */

public class TtxDataInit {
    static String LogTag = "HopeTtxDataInit";
    
    private SQLiteHelper mSQLiteHelper;
    private MySQL mMySQL;
    private int mTryTime = 0;
    AlertDialog mAlert;
    private UrlStructure mUrlStructure = UrlStructure.getInstance();
    private HttpDownload mHttpDownload = HttpDownload.getInstance();

    public TtxDataInit(){
        mSQLiteHelper = SQLiteHelper.getInstance();
        mMySQL = MySQL.getInstance();
    }
    public void init(){
//        mAlert = new AlertDialog.Builder(Util.getMainActivityContext())
//                .setTitle("提示")
//                .setMessage("数据库同步中，请稍后！！")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .show();
        createTables();
        insertDataToTables();
    }

    private void updateProgress(){
        double percent = ((float)mIndexInsert/(float)mTableNames.size());
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        mAlert.setMessage("数据库同步中，请稍后！！\n 已同步 " + nt.format(percent) );
    }
    private void insertDataToTables() {
        mIndexInsert = 0;
        mMySQL.setMysqlListener(mMysqlQueryListener);
        mMySQL.queryTableNames();
    }

    private int mIndexInsert = 0;
    private ArrayList<String> mTableColname = new ArrayList<String>();
    private ArrayList<String> mTableNames = new ArrayList<String>();
    private MysqlQueryListener mMysqlQueryListener = new MysqlQueryListener(){
        @Override
        public void onQueryTableColResult(String table,ArrayList<String> tableColname) {
            mTableColname = tableColname;
            Log.info(LogTag,"onQueryTableColResult mTableCol=" + mTableColname);
            String where = "";
            for(int i=0;i<Const.mTablesNamesWithUserID.length;i++){
                if(Const.mTablesNamesWithUserID[i].equals(table)){
                    where = " where user_id = " + Const.getUserId() ;
                }
            }
            String sql = "SELECT * FROM " + table  + where;
            Log.info(LogTag,"onQueryTableColResult sql=" + sql);
            onDeleteTableWithUserId(table);
            mMySQL.queryTable(table,sql, mTableColname);
        }

        @Override
        public void onQueryInsertResult(String table,ContentValues cv) {
            Log.info(LogTag,"onQueryInsertResult cv=" + cv);
            long intsert = mSQLiteHelper.insert(table,cv);
            Log.info(LogTag,"onQueryInsertResult intsert=" + intsert);
        }

        @Override
        public void onQueryTableNamesResult(ArrayList<String> str) {
            mTableNames = str;
            Log.info(LogTag,"onQueryTableNamesResult mTableCol=" + mTableNames);
            mMySQL.queryTableColname(mTableNames.get(mIndexInsert));
        }

        @Override
        public void onInsertDone() {
            mIndexInsert += 1;
//            mHandler.sendEmptyMessage(0);
            if(mIndexInsert < mTableNames.size()) {
                mMySQL.queryTableColname(mTableNames.get(mIndexInsert));
            }else{
                onInsertTablesDone();
            }
        }
    };

    private void onDeleteTableWithUserId(String table) {
        for(int i =0;i<Const.mTablesNamesWithUserID.length;i++){
            if(Const.mTablesNamesWithUserID[i].equals(table)){
                mSQLiteHelper.delete(table);
            }
        }
    }

    private void onInsertTablesDone() {
        Log.info(LogTag,"onInsertTablesDone");
        for(int i=0;i<mUrlStructure.mUrlLists.size();i++) {
            DownLoadFile(mUrlStructure.mUrlLists.get(i));
        }
        ask_children.getInstance().InitData();
        users.getInstance().InitData();
//        mAlert.dismiss();
    }

    private void DownLoadFile(String filePathName){
        String urlStr= Const.MySQLHttpHead + filePathName;

        mHttpDownload.downlaodFile(urlStr,filePathName);
    }

    private void createTables(){
        Log.info(LogTag,"createTables helper=" + mSQLiteHelper);

        for (int i = 0; i< Const.mCreateTables.length; i++) {
            Cursor cursor = mSQLiteHelper.query("select * from sqlite_master where name="+"'"+ Const.mTablesNames[i] +"'");
//            Log.info(LogTag,"onModeClick cursor=" + cursor.getCount() + Const.mTablesNames[i] );
            if(cursor.getCount() == 0) {
                mSQLiteHelper.execSQL(Const.mCreateTables[i]);
            }
            cursor.close();
        }
    }
}

//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    //完成主界面更新,拿到数据
//                    updateProgress();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
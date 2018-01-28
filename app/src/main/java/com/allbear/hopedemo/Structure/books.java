package com.allbear.hopedemo.Structure;

import android.database.Cursor;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.SQLite.SQLiteHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/7.
 */

public class books {
    private static books instance = null;
    private books() {
        mSQLiteHelper = SQLiteHelper.getInstance();
    }
    public static books getInstance() {
        if (instance == null) {
            instance = new books();
        }
        return instance;
    }
    private SQLiteHelper mSQLiteHelper;
    private ArrayList<books_structure> mBooksData = new ArrayList<books_structure>();
    public void InitData(){
        mBooksData.clear();
        Cursor cursor = mSQLiteHelper.query("select * from books ");
        if(cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                books_structure book = new books_structure();
                book.file_url = cursor.getString(cursor.getColumnIndex("file_url"));
                mBooksData.add(book);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Log.info("Hopebooks","mBooksData =" + mBooksData.size());
        for(int i = 0;i<mBooksData.size();i++){
            Log.info("Hopebooks","mAskChildrenData =" + i +  mBooksData.get(i).file_url);
        }
    }
    public int getBooksCount(){
        return mBooksData.size();
    }
    public String getFileUrl(int index){
        return mBooksData.get(index).file_url;
    }

    class books_structure {
        public int book_id;
        public int unit_num;
        public String eng_name;
        public String chi_name;
        public String file_name;
        public String file_url;
        public String gui_name;
        public String gui_url;
        public String words;
        public String stats;
    }
}

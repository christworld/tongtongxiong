package com.allbear.hopedemo.SQLite;

/**
 * Created by Administrator on 2017/11/12.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Structure.time_sets;
import com.allbear.hopedemo.util.Util;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String TABLE_NAME = "ttx";

    private static SQLiteHelper instance = null;
    //构造函数，创建数据库
    private SQLiteHelper() {
        super(Util.getPackageContext(), Const.DATABASE_NAME, null, Const.DATABASE_VERSION);
    }
    public static SQLiteHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteHelper();
        }
        return instance;
    }

    //建表
    public void onCreate(SQLiteDatabase db) {
        Log.i("HopeSQLlit","onCreate db=" + db);
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,"
                + " BookName VARCHAR(30)  NOT NULL,"
                + " Author VARCHAR(20),"
                + " Publisher VARCHAR(30))";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    //获取游标
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //根据条件查询
    public Cursor query(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    //插入一条记录
    public long insert(String table,ContentValues cv ) {
        SQLiteDatabase db = this.getWritableDatabase();
        long row = db.insert(table, null, cv);
        return row;
    }
    //执行SQL语句
    public void execSQL(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    //删除记录
    public void delete(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null, null);
    }

    //更新记录
    public void update(int id, String bookName,String author,String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { Integer.toString(id) };
        ContentValues cv = new ContentValues();
        cv.put("BookName", bookName);
        cv.put("Author", author);
        cv.put("Publisher", publisher);
        db.update(TABLE_NAME, cv, where, whereValue);
    }

}

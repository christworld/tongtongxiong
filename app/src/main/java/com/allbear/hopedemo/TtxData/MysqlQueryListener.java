package com.allbear.hopedemo.TtxData;

import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public interface MysqlQueryListener {
    void onQueryTableNamesResult(ArrayList<String> str);
    void onQueryTableColResult(String table,ArrayList<String> str);
    void onQueryInsertResult(String table,ContentValues cv);
    void onInsertDone();
}

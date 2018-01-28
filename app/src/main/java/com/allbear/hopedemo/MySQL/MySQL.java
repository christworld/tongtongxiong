package com.allbear.hopedemo.MySQL;

import android.content.ContentValues;
import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Structure.UrlStructure;
import com.allbear.hopedemo.TtxData.MysqlQueryListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MySQL {
    static String LogTag = "HopeMySQL";

    private UrlStructure mUrlStructure = UrlStructure.getInstance();
    private MysqlQueryListener mMysqlQueryListener;
    public void setMysqlListener(MysqlQueryListener callback) {
        this.mMysqlQueryListener = callback;
    }
    private static MySQL instance = null;
    //构造函数，创建数据库
    private MySQL() {
    }
    public static MySQL getInstance() {
        if (instance == null) {
            instance = new MySQL();
        }
        return instance;
    }

    public void queryTableNames(){
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Connection conn = MySQLUtil.openConnection(Const.MySQLURL, Const.MySQLUSER, Const.MySQLPASSWORD);
                Statement statement = null;
                ResultSet result = null;
                String sql = "select table_name \n" +
                        "from information_schema.tables\n" +
                        "where table_schema='" + Const.MySQLDB + "'";
                ArrayList<String> tableNames = new ArrayList<String>();
                try {
                    statement = conn.createStatement();
                    result = statement.executeQuery(sql);
                    if (result != null && result.first()) {
                        while (!result.isAfterLast()) {
                            tableNames.add(result.getString(1));
//                            Log.i("HopeMySQLUtil", result.getString(1) );
                            result.next();
                        }
                    }
                    mMysqlQueryListener.onQueryTableNamesResult(tableNames);
                } catch (SQLException e) {
                    Log.info("HopeMySQLUtil", "queryTableNames SQLException e=" + e);
                    e.printStackTrace();
                }finally {
                    try {
                        if (result != null) {
                            result.close();
                            result = null;
                        }
                        if (statement != null) {
                            statement.close();
                            statement = null;
                        }
                        if(conn != null){
                            conn.close();
                            conn = null;
                        }
                    } catch (SQLException sqle) {

                    }
                }
            }
        });
        thread.start();
    }
    public void queryTableColname(String table){
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Connection conn = MySQLUtil.openConnection(Const.MySQLURL, Const.MySQLUSER, Const.MySQLPASSWORD);
                Statement statement = null;
                ResultSet result = null;
                String sql = "select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='" + table + "'";
                ArrayList<String> tableCol = new ArrayList<String>();
                try {
                    statement = conn.createStatement();
                    result = statement.executeQuery(sql);
                    if (result != null && result.first()) {
                        while (!result.isAfterLast()) {
                            tableCol.add(result.getString(1));
                            result.next();
                        }
                    }
                    mMysqlQueryListener.onQueryTableColResult(table,tableCol);
                } catch (SQLException e) {
                    Log.info("HopeMySQLUtil", "queryTableColname SQLException e=" + e);
                    e.printStackTrace();
                }finally {
                    try {
                        if (result != null) {
                            result.close();
                            result = null;
                        }
                        if (statement != null) {
                            statement.close();
                            statement = null;
                        }
                        if(conn != null){
                            conn.close();
                            conn = null;
                        }
                    } catch (SQLException sqle) {

                    }
                }
            }
        });
        thread.start();
    }

    public void queryTable(String table,String sql,ArrayList<String> tableColname){
        Log.info("HopeMySQLUtil", tableColname.toString());
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Connection conn = MySQLUtil.openConnection(Const.MySQLURL, Const.MySQLUSER, Const.MySQLPASSWORD);
                Statement statement = null;
                ResultSet result = null;
                ArrayList<String> tableCol = new ArrayList<String>();
                try {
                    statement = conn.createStatement();
                    result = statement.executeQuery(sql);
//                    Log.i("HopeMySQLUtil", "result =" + result);
                    ContentValues cv = new ContentValues();
                    if (result != null && result.first()) {
                        while (!result.isAfterLast()) {
                            for (int i=0;i<tableColname.size();i++){
                                String colname = tableColname.get(i);
                                cv.put(colname, result.getString(colname));
                                if(tableColname.get(i).contains("_url")){
                                    if(!result.getString(colname).equals("")) {
                                        mUrlStructure.mUrlLists.add(result.getString(colname));
                                    }
                                }
                            }
                            mMysqlQueryListener.onQueryInsertResult(table,cv);
                            result.next();
                        }
                    }
                } catch (SQLException e) {
                    Log.info("HopeMySQLUtil", "queryTable SQLException e=" + e);
                    e.printStackTrace();
                }finally {
                    try {
                        if (result != null) {
                            result.close();
                            result = null;
                        }
                        if (statement != null) {
                            statement.close();
                            statement = null;
                        }
                        if(conn != null){
                            conn.close();
                            conn = null;
                        }
                    } catch (SQLException sqle) {

                    }
                }
                mMysqlQueryListener.onInsertDone();
            }
        });
        thread.start();
    }
    public void execSQL(String sql){
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Connection conn = MySQLUtil.openConnection(Const.MySQLURL, Const.MySQLUSER, Const.MySQLPASSWORD);
                Log.info(LogTag,"conn =" + conn);
                Log.info(LogTag," sql =" + sql);
                MySQLUtil.execSQL(conn,sql);
            }
        });
        thread.start();
    }
}
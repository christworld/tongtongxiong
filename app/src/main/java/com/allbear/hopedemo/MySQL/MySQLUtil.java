package com.allbear.hopedemo.MySQL;

import android.content.Context;

import com.allbear.hopedemo.Log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/10/31.
 */
public class MySQLUtil {
    public static Connection openConnection(String url, String user,
                                            String password) {
        Connection conn = null;
        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
//            Log.i("HopeMySQLUtil","成功加载MySQL驱动");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
            Log.info("HopeMySQLUtil","ClassNotFoundException e=" + e);
        } catch (SQLException e) {
            conn = null;
            Log.info("HopeMySQLUtil", "SQLException e=" + e);
        }
        return conn;
    }

    public static ResultSet query(Context context,Connection conn, String sql) {
        Log.info("HopeMySQLUtil","conn=" + conn);
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            Log.info("HopeMySQLUtil","result =" + result);
        } catch (SQLException e) {
            Log.info("HopeMySQLUtil","SQLException e=" + e);
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException sqle) {

            }
        }
        return result;
    }

    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }
        return execResult;
    }
}


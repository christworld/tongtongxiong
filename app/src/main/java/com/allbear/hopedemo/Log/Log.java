package com.allbear.hopedemo.Log;

/**
 * Created by Administrator on 2017/11/20.
 */

public class Log {

    public static void info(String tag,String info){
        android.util.Log.i(tag,info);
    }

    public static boolean chenkNull(String tag,String info){

        android.util.Log.i(tag,info);

        return false;
    }
}

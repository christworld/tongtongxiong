package com.allbear.hopedemo.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Http.HttpDownload;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Structure.UrlStructure;
import com.allbear.hopedemo.TtxData.TtxDataInit;


/**
 * Created by Administrator on 2017/11/20.
 */

public class TtxServices extends Service {
    private static final String TAG = "HopeTtxServices";

    public MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.info(TAG, "onCreate() executed");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.info(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                new TtxDataInit().init();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public void startDownload() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.info(TAG, "MyBinder startDownload");
//                    new TtxDataInit().init();
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.info(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

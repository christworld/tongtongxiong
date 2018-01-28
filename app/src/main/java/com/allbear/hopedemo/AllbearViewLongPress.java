package com.allbear.hopedemo;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.allbear.hopedemo.Fragment.AllbearFragment;
import com.allbear.hopedemo.Log.Log;

/**
 * Created by Administrator on 2017/4/10.
 */

public class AllbearViewLongPress {
    static String LogTag = "HopeAllbearViewLongPress";
    private AllbearFragment mFragment = null;
    private View mView = null;
    private static int mLongPressTimeMS = 400;
    private boolean mIsLongClick = false;
    private Handler mHandler = new Handler();
    private CheckForLongPressRunnable mCheckForLongPressRunnable = null;
    public AllbearViewLongPress(View view,AllbearFragment f){
        mFragment = f;
        mView = view;
        view.setOnTouchListener(buttonTouchListener);
    }
    private View.OnTouchListener buttonTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent event) {
            int iAction = event.getAction();
            if (iAction == MotionEvent.ACTION_DOWN) {	// 按下
                Log.info(LogTag,"onTouch MotionEvent.ACTION_DOWN");
                checkForLongPress(view);
            } else if (iAction == MotionEvent.ACTION_UP) {	// 弹起
                Log.info(LogTag,"onTouch MotionEvent.ACTION_UP");
                removeLongPressCallback();
                if(mIsLongClick){
                    mFragment.buttonLongPressUp(view.getId());
                    mIsLongClick = false;
                    view.setPressed(false);
                    return true;
                }
            }
            return false;	// return false表示系统会继续处理
        }
    };
    private void checkForLongPress(View view){
        if (mCheckForLongPressRunnable == null) {
            mCheckForLongPressRunnable = new CheckForLongPressRunnable();
        }
        mCheckForLongPressRunnable.setButtonId(view.getId());
        mHandler.postDelayed(mCheckForLongPressRunnable, mLongPressTimeMS);
    }
    private void removeLongPressCallback() {
        if (mCheckForLongPressRunnable != null) {
            mHandler.removeCallbacks(mCheckForLongPressRunnable);
        }
    }
    class CheckForLongPressRunnable implements Runnable {
        int currentId = 0;
        public void run() {
            Log.info(LogTag,"CheckForLongPress run");
            mIsLongClick = true;
            mFragment.buttonLongPress(currentId);
        }
        public void setButtonId(int id) {
            currentId = id;
        }
    }
}

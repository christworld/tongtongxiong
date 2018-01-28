package com.allbear.hopedemo.Engine;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface IatRecogListener {
    void onIatRecogResult(String result,boolean isLast);
    void onIatRecogResult(String EnglisthResult,String ChineseResult,boolean isLast);
    void onError(String error);
}

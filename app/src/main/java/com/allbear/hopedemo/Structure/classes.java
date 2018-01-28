package com.allbear.hopedemo.Structure;

/**
 * Created by Administrator on 2017/11/7.
 */

public class classes {
    private static classes instance = null;
    private classes() {}
    public static classes getInstance() {
        if (instance == null) {
            instance = new classes();
        }
        return instance;
    }

    public int class_id;

    public int unit_num;

    public int day_nums;

    public String books_sch;
}

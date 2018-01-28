package com.allbear.hopedemo.Structure;

/**
 * Created by Administrator on 2017/11/7.
 */

public class dialog_prog {
    private static dialog_prog instance = null;
    private dialog_prog() {}
    public static dialog_prog getInstance() {
        if (instance == null) {
            instance = new dialog_prog();
        }
        return instance;
    }
    public int dialog_prog_id;

    public int user_id;

    public String question;

    public String answer;
}

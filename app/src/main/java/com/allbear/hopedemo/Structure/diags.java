package com.allbear.hopedemo.Structure;

/**
 * Created by Administrator on 2017/11/7.
 */

public class diags {
    private static diags instance = null;
    private diags() {}
    public static diags getInstance() {
        if (instance == null) {
            instance = new diags();
        }
        return instance;
    }

    public int diag_id;

    public int unit_num;

    public String eng_sent;

    public String eng_url;

    public String chi_sent;

    public String chi_url;

    public String keywords;

}

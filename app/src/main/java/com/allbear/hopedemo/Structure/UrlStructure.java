package com.allbear.hopedemo.Structure;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class UrlStructure {

    private static UrlStructure instance = null;
    private UrlStructure() {
    }
    public static UrlStructure getInstance() {
        if (instance == null) {
            instance = new UrlStructure();
        }
        return instance;
    }
    public static List<String> mUrlLists = new ArrayList<String>();
    public static Hashtable mTableColsnames = new Hashtable();
}

package com.allbear.hopedemo.Structure;

import java.sql.Time;

/**
 * Created by Administrator on 2017/11/16.
 */

public class time_sets {
    private static time_sets instance = null;
    private time_sets() {}
    public static time_sets getInstance() {
        if (instance == null) {
            instance = new time_sets();
        }
        return instance;
    }

    public static String user_id="1";

    public static String getup_time="07:30:00";
    public static String holi_getup_time="08:00:00";
    public static String school_time="08:30:00";
    public static String oral_time="16:15:00";
    public static String video_time="19:30:00";
    public static String book_time="20:30:00";
    public static String sleep_time="21:00:00";
    public static String last_update_time="23:00:00";

    public static String [] mSetTimesStr = {
            user_id,
            getup_time,
            holi_getup_time,
            school_time,
            oral_time,
            video_time,
            book_time,
            sleep_time,
            last_update_time
    };
}

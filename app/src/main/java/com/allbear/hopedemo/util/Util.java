package com.allbear.hopedemo.util;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearMediaplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/22.
 */

public class Util {
    private static Context mMainActivityContext;
    public static void setMainActivityContext(Context context){
        mMainActivityContext = context;
    }
    public static Context getMainActivityContext(){
        return mMainActivityContext;
    }
    private static Context mPackageContext;
    public static void setPackageContext(Context context){
        mPackageContext = context;
    }
    public static Context getPackageContext(){
        return mPackageContext;
    }
    public static String getCurrentTime(String format){
        SimpleDateFormat dateformat = new SimpleDateFormat(format);//format = "yyyy-MM-dd HH:mm:ss"
        String dateStr = dateformat.format(System.currentTimeMillis());
        return dateStr;
    }
    public static long getTimeLong(String time){
        SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strDate = formatter.format(curDate);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long Time = 0;
        try {
            Time = dateformat.parse(strDate + " " + time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Time;
    }
    public static long getDateTimeLong(String DateTime){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long Time = 0;
        try {
            Time = dateformat.parse(DateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Time;
    }
    public static boolean checkBirthDay(String birthDate){
        if(birthDate == null || birthDate.equals("")){
            return false;
        }
        SimpleDateFormat formatter =  new SimpleDateFormat("MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strCurDate = formatter.format(curDate);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strBirthDate = formatter.format(new Date(getDateTimeLong(birthDate)));
        Log.info("HopeUtil","checkBirthDay strDate=" + strCurDate);
        Log.info("HopeUtil","checkBirthDay strDate=" + strBirthDate);
        if(strCurDate.equals(strBirthDate)){
            return true;
        }else{
            return false;
        }
    }
    public static void playNotRing(Context context){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

    public static void playDefSong(Context context){
        AllbearMediaplayer mMediaPlayer = AllbearMediaplayer.getInstance(context);
        mMediaPlayer.startPlay("yujian.mp3");
    }
    public static int getWeekIndex() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    public static boolean contains(String str1,String str2){
        if(str1.trim().toLowerCase().contains(str2.trim().toLowerCase())){
            return true;
        }else{
            return false;
        }
    }
    public static boolean equals(String str1,String str2){
        if(str1.trim().toLowerCase().equals(str2.trim().toLowerCase())){
            return true;
        }else{
            return false;
        }
    }
    public static byte[] readAudioFile( String filename) {
        try {
            FileInputStream ins = new FileInputStream(filename);
            byte[] data = new byte[ins.available()];

            ins.read(data);
            ins.close();

            return data;
        } catch (IOException e) {
            Log.info("HopeUtil","readAudioFile：e=" + e);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNumberChars(String str) {
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;i++){
//            Log.info("HopeUtil","isNumberChars：chars[i]=" + chars[i]);
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(chars[i] + "");
            if(!m.matches()){
                return false;
            }
        }
        return true;
    }
    public static boolean isEnglishChars(String str) {
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;i++){
//            Log.info("HopeUtil","isEnglishChars：chars[i]=" + chars[i]);
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher m = p.matcher(chars[i] + "");
            if(!m.matches()){
                return false;
            }
        }
        return true;
    }
    public static boolean isChineseChars(String str){
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;i++){
//            Log.info("HopeUtil","isChineseChars：chars[i]=" + chars[i]);
            Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
            Matcher m = p.matcher(chars[i] + "");
            if(!m.matches()){
                return false;
            }
        }
        return true;
    }
}

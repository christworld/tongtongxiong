package com.allbear.hopedemo.util;

/**
 * Created by Administrator on 2017/11/22.
 */

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Spring on 2015/11/7.
 */
public class FileUtil {
    private static String SDPath = Environment.getExternalStorageDirectory() + "/" ;

    public static String getSDPATH() {
        return SDPath;
    }
    public static String getPackageFilePath() {
        File fileDir = Util.getPackageContext().getFilesDir();
        return fileDir.getAbsolutePath() + "//";
    }
    public static String getSavePath(){
        return getPackageFilePath();
    }
    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(getSavePath() + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     * @param dirName 目录名字
     * @return 文件目录
     */
    public static File createDir(String dirName){
        File dir = new File(getSavePath() + dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName){
        File file = new File(getSavePath() + fileName);
        return file.exists();
    }
    public static boolean isPathFileExist(String fileName){
        File file = new File(fileName);
        return file.exists();
    }
    public static String getFileName(String filePathName){
        int start=filePathName.lastIndexOf("/");
        int end=filePathName.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return filePathName.substring(start+1,end);
        }else{
            return null;
        }
    }
    public static String getFilePath(String filePathName){
        int start=filePathName.lastIndexOf("/");
        int end=filePathName.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return filePathName.substring(0,start+1);
        }else{
            return null;
        }
    }
    public static File write2SDFromInput(String filePathName,InputStream input){
        File file = null;
        OutputStream output = null;
        try {
            createDir(getFilePath(filePathName));
            file =createSDFile(filePathName);
            output = new FileOutputStream(file);
            byte [] buffer = new byte[2];
            while(input.read(buffer) != -1){
                output.write(buffer);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
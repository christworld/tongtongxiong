package com.allbear.hopedemo.Http;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.util.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/11/22.
 */

public class HttpDownload {
    private URL url = null;
    private final String TAG = "HopeHttpDownload";

    private static HttpDownload instance = null;
    private HttpDownload() {
    }
    public static HttpDownload getInstance() {
        if (instance == null) {
            instance = new HttpDownload();
        }
        return instance;
    }
    /**
     * 读取文本文件
     * @param urlStr url路径
     * @return 文本信息
     * 根据url下载文件，前提是这个文件中的内容是文本，
     * 1.创建一个URL对象
     * 2.通过URL对象，创建一个Http连接
     * 3.得到InputStream
     * 4.从InputStream中得到数据
     */
    public String download(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader bufferedReader = null;

        try {
            url = new URL(urlStr);
            //创建http连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            //使用IO流读取数据
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info("TAG","下载txt文件");
        Log.info("TAG",sb.toString());
        return sb.toString();
    }

    /**
     * 读取任何文件
     * 返回-1 ，代表下载失败。返回0，代表成功。返回1代表文件已经存在
     *
     * @param urlStr
     * @return
     */
    public int downlaodFile(String urlStr, String filePathName) {
        InputStream input = null;
        Log.info(TAG,"filePathName=" + filePathName);
        if (urlStr==null||urlStr.equals("")|| filePathName==null||filePathName.equals("")||FileUtil.isFileExist(filePathName)) {
            return 1;
        }
        try {
            input = getInputStearmFormUrl(urlStr);
            File resultFile = FileUtil.write2SDFromInput(filePathName,input);
            if (resultFile == null)
                return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  0;
    }

    public InputStream getInputStearmFormUrl(String urlStr) throws IOException {
        url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream input = urlConn.getInputStream();
        return input;
    }
}

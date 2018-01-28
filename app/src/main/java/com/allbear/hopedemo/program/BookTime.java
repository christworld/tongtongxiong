package com.allbear.hopedemo.program;

import android.media.MediaPlayer;
import android.os.Environment;

import com.allbear.hopedemo.Const.Const;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Structure.books;
import com.allbear.hopedemo.util.FileUtil;
import com.allbear.hopedemo.util.Util;

/**
 * Created by Administrator on 2017/11/26.
 */

public class BookTime extends ProgramBase {
    private static String LogTag = "HopeBookTime";
    private static BookTime instance = null;
    public static BookTime getInstance() {
        if (instance == null) {
            instance = new BookTime();
        }
        return instance;
    }
    public BookTime(){
        mBooks = books.getInstance();
        mMediaPlayer.setMediaPlayListener(mMediaPlayerOncompletionListener);
    }
    private books mBooks;
    private int mBooksNo = 0;
    private boolean mPlayStateInit = false;

    @Override
    public void doProgramTime(){
//        int weekIndex = Util.getWeekIndex()-1;
//        Log.info(LogTag,"doGetUpCall..............weekIndex=." + weekIndex);
//        String getupWeekCall = "这是绘本时间，绘本时间！";
//        mAllbearTts.startTts(getupWeekCall);
//        mAllbearIse.startIseSelf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ise.wav");

        if(mPlayStateInit == false) {
            playBooksMedia();
            mPlayStateInit = true;
        }else if(mPlayStateInit == true){
            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.pausePlay();
            }else{
                mMediaPlayer.startPlay();
            }
        }
    }

    private void playBooksMedia() {
        String path = FileUtil.getSavePath() + mBooks.getFileUrl(mBooksNo++);
        mMediaPlayer.startPlaySDPath(path);
        if(mBooksNo >= mBooks.getBooksCount()){
            mBooksNo = 0;
        }
    }

    @Override
    protected void onMediaPlayCompletion(MediaPlayer mp) {
        playBooksMedia();
        super.onMediaPlayCompletion(mp);
    }
}

package com.allbear.hopedemo.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.allbear.hopedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class IntroduceFragment extends AllbearFragment  implements View.OnClickListener {
    static String LogTag = "HopeIntroduceFragment";

    private static final int mButtonCount = 4;
    private List<Button> mButtons = new ArrayList<Button>();
    private int [] mButtonIds = {
            R.id.id_play_bt,
            R.id.id_play_bt2,
            R.id.id_play_bt3,
            R.id.id_play_bt4
    };
    private String [] mMusicName = {
            "faded.mp3",
            "lost_boy.mp3",
            "miss_you.mp3",
            "yujian.mp3"
    };
    private boolean mPause = false;
    private int mPlayingIndex = -1;

    private void Logi(String info){
        Log.i(LogTag,info);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Logi("onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.introduce_fragment, container, false);
        }
        Logi("onCreateView " + mView);
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        for(int i = 0;i < mButtonCount; i++){
            Button btn = (Button) getActivity().findViewById(mButtonIds[i]);
            btn.setOnClickListener(this);
            btn.setBackgroundResource(R.drawable.icon_play);
            if(mButtons.size() != mButtonCount) {
                mButtons.add(btn);
            }
        }
    }

    @Override
    public void onMediaPlayerCompletion() {
        super.onMediaPlayerCompletion();
        Logi("onMediaPlayerCompletion");
        resetMediaplayer();
    }

    @Override
    public void onEntryView() {
        super.onEntryView();
    }

    @Override
    public void onExitView() {
        super.onExitView();
        Logi("onExitView");
        mMediaPlayer.pausePlay();
        resetMediaplayer();
    }

    @Override
    public void onClick(View view){
        Logi("onClick" + view.getId() + "  mImageButtons=" + mButtons.get(0));
        resetButtonIcon();
        for (int i = 0;i < mButtonCount;i++){
            if(view.getId() == mButtonIds[i]){
                if(mPlayingIndex == i){
                    if(mPause){
                        mPause = false;
                        playMediaPlayer(i);
                    }else {
                        mPause = true;
                        pauseMediaPlayer(i);
                    }
                }else{
                    mPlayingIndex = i;
                    startMediaPlayer(i);
                }
            }
        }
    }
    private void resetMediaplayer(){
        resetButtonIcon();
        mPlayingIndex = -1;
        mPause = false;
    }
    private void resetButtonIcon(){
        for (int i = 0;i < mButtonCount;i++){
            mButtons.get(i).setBackgroundResource(R.drawable.icon_play);
        }
    }
    private void playMediaPlayer(int index){
        mMediaPlayer.startPlay();
        mButtons.get(index).setBackgroundResource(R.drawable.icon_pause);
    }
    private void startMediaPlayer(int index){
        mMediaPlayer.startPlay(mMusicName[index]);
        mButtons.get(index).setBackgroundResource(R.drawable.icon_pause);
    }
    private void pauseMediaPlayer(int index){
        mMediaPlayer.pausePlay();
        mButtons.get(index).setBackgroundResource(R.drawable.icon_play);
    }
}

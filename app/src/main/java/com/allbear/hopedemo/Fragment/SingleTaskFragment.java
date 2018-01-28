package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;
import com.allbear.hopedemo.Structure.time_sets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public class SingleTaskFragment extends AllbearFragment implements View.OnClickListener {
    private String LogTag = "HopeSingleTaskFragment";

    private List<Button> mButtons = new ArrayList<Button>();
    private int [] mButtonIds = {
            R.id.tv_user_id,
            R.id.tv_getup_time,
            R.id.tv_holi_getup_time,
            R.id.tv_school_time,
            R.id.tv_book_time,
            R.id.tv_video_time,
            R.id.tv_oral_time,
            R.id.tv_sleep_time
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.singletask_fragment, container, false);
        }
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        for(int i = 0;i < mButtonIds.length; i++){
            mButtons.add((Button) mView.findViewById(mButtonIds[i]));
        }
    }

    @Override
    public void onEntryView() {
        super.onEntryView();
        Log.info(LogTag,"onEntryView");

    }
    @Override
    public void onExitView() {
        super.onExitView();
        Log.info(LogTag,"onExitView");
        mMediaPlayer.pausePlay();
    }
    @Override
    public void onClick(View v) {
        for(int i = 1;i < mButtonIds.length; i++){
            if(v.getId() == mButtonIds[i]) {
//                InControl.getInstance().doInControl(i-1);
                Log.info(LogTag,"onClick i= " + i);
            }
        }
    }
}

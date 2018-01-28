package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;
import com.allbear.hopedemo.Structure.time_sets;
import com.allbear.hopedemo.program.InControl;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/19.
 */

public class SettingsFragment extends AllbearFragment implements View.OnClickListener {
    static String LogTag = "HopeSettingsFragment";

    private List<TextView> mTextViews = new ArrayList<TextView>();
    private int [] mTextviewIds = {
            R.id.tv_user_id,
            R.id.tv_getup_time,
            R.id.tv_holi_getup_time,
            R.id.tv_school_time,
            R.id.tv_book_time,
            R.id.tv_video_time,
            R.id.tv_oral_time,
            R.id.tv_sleep_time
    };
    private time_sets mTimeSets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.settings_fragment, container, false);
        }
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTimeSets = time_sets.getInstance();
        initView();
    }

    private void initView(){
        for(int i = 0;i < mTextviewIds.length; i++){
            mTextViews.add((TextView) mView.findViewById(mTextviewIds[i]));
        }
    }

    @Override
    public void onEntryView() {
        super.onEntryView();
        Log.info(LogTag,"onEntryView");
        onShowTimeSets();
    }

    private void onShowTimeSets() {
        for(int i = 0;i < mTextviewIds.length; i++){
            mTextViews.get(i).setText(mTimeSets.mSetTimesStr[i]);
            mTextViews.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onExitView() {
        super.onExitView();
        Log.info(LogTag,"onExitView");
        mMediaPlayer.pausePlay();
    }

    @Override
    public void onClick(View v) {
        Log.info(LogTag,"onClick v= " + v);
        for(int i = 1;i < mTextviewIds.length; i++){
            if(v.getId() == mTextviewIds[i]) {
                InControl.getInstance().doInControl(i-1);
                Log.info(LogTag,"onClick i= " + i);
            }
        }
    }
}

package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;
import com.allbear.hopedemo.SingleTask.Books;
import com.allbear.hopedemo.SingleTask.EatEnglish;
import com.allbear.hopedemo.SingleTask.EnglishFAQ;
import com.allbear.hopedemo.SingleTask.FollowRead;
import com.allbear.hopedemo.SingleTask.MorningEnglish;
import com.allbear.hopedemo.SingleTask.SceneEnglish;
import com.allbear.hopedemo.SingleTask.Talk;
import com.allbear.hopedemo.SingleTask.WeekTest;
import com.allbear.hopedemo.SingleTask.Words;
import com.allbear.hopedemo.program.BookTime;
import com.allbear.hopedemo.program.OralTime;
import com.allbear.hopedemo.program.ProgramBase;
import com.allbear.hopedemo.program.SchoolTime;
import com.allbear.hopedemo.SingleTask.Turn;
import com.allbear.hopedemo.program.VideoTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/28.
 */

public class SingleTaskFragment extends AllbearFragment implements View.OnClickListener {
    private String LogTag = "HopeSingleTaskFragment";

    private List<Button> mButtons = new ArrayList<Button>();
    private int [] mButtonIds = {
            R.id.bt_turn_id,
            R.id.bt_english_faq_id,
            R.id.bt_talk_id,
            R.id.bt_weektest_id,
            R.id.bt_words_id,
            R.id.bt_book_id,
            R.id.bt_followread_id,
            R.id.bt_scene_english_id,
            R.id.bt_eat_english_id,
            R.id.bt_morning_english_id
    };
    private ProgramBase[] mProgramBases = {
            Turn.getInstance(),
            EnglishFAQ.getInstance(),
            Talk.getInstance(),
            WeekTest.getInstance(),
            Words.getInstance(),
            Books.getInstance(),
            FollowRead.getInstance(),
            SceneEnglish.getInstance(),
            EatEnglish.getInstance(),
            MorningEnglish.getInstance()
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
            Button button = (Button) mView.findViewById(mButtonIds[i]);
            button.setOnClickListener(this);
            mButtons.add(button);
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
        Log.info(LogTag,"onClick mButtonIds.length=" + mButtonIds.length);
        for(int i = 0;i < mButtonIds.length; i++){
            if(v.getId() == mButtonIds[i]) {
                Log.info(LogTag,"onClick i= " + i);
                mProgramBases[i].doProgramTime();
            }
        }
    }
}

package com.allbear.hopedemo.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;
import com.allbear.hopedemo.program.ProgramBase;
import com.allbear.hopedemo.util.Util;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by Administrator on 2017/3/19.
 */

public class EnglishPicBookFragment extends AllbearFragment implements View.OnClickListener{
    static String LogTag = "HopeEnglishPicBook";
    private TextView mEngTextview;
    private TextView mChiTextview;
    private EditText mIseEdittext;
    private TextView mIseResultTextview;
    private Button mIseButton;
    private Button mIatButton;
    private Button mTtsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.englishpicbook_fragment, container, false);
        }
        return mView;
    }

    private void initView() {
        new ProgramRec();
        mEngTextview = (TextView) mView.findViewById(R.id.eng_rec_id);
        mChiTextview = (TextView) mView.findViewById(R.id.chi_rec_id);
        mIseEdittext = (EditText) mView.findViewById(R.id.ise_text_id);
        mIseEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.info(LogTag,"onTextChanged s=" + s + " count=" + count);
                if(s.length() > 0) {
                    mIseButton.setEnabled(true);
                    mTtsButton.setEnabled(true);
                }else{
                    mIseButton.setEnabled(false);
                    mTtsButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mIseResultTextview = (TextView) mView.findViewById(R.id.ise_res_text_id);
        mIseButton = (Button) mView.findViewById(R.id.bt_ise_id);
        mIseButton.setOnClickListener(this);
        mIseButton.setEnabled(false);
        mTtsButton = (Button) mView.findViewById(R.id.bt_tts_id);
        mTtsButton.setOnClickListener(this);
        mTtsButton.setEnabled(false);
        mIatButton = (Button) mView.findViewById(R.id.bt_iat_id);
        mIatButton.setOnClickListener(this);
    }

    @Override
    public void onEntryView() {
        super.onEntryView();
        Log.info(LogTag,"onEntryView");
        initView();
    }

    @Override
    public void onExitView() {
        super.onExitView();
        Log.info(LogTag,"onExitView");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_iat_id){
            doIatRecgor();
        }else if(v.getId() == R.id.bt_ise_id){
            doIseRecgor();
        }else if(v.getId() == R.id.bt_tts_id){
            doTtsRecgor();
        }
    }

    private void doTtsRecgor() {
        Log.info(LogTag,"doIseRecgor");
        mAllbearTts.startTts(mIseEdittext.getText().toString());
    }

    private void doIseRecgor() {
        Log.info(LogTag,"doIseRecgor");
        mAllbearIse.startIse(mIseEdittext.getText().toString());
    }

    private void doIatRecgor() {
        Log.info(LogTag,"doIatRecgor");
        mAllbearIat.startIatNoUI();
        mIatButton.setEnabled(false);
        mEngTextview.setText("");
        mChiTextview.setText("");
        mIseEdittext.setText("");
    }

    private class ProgramRec extends ProgramBase {
        public ProgramRec(){
            mAllbearIat.setIatRecogListener(mIatRecogListener);
            mAllbearTts.setTtsSynthesizerListener(mTtsSynthesizerListener);
            mAllbearIse.setIseRecogListener(mIseRecogListener);
        }

        @Override
        protected void onIatResult(String EnglisthResult, String ChineseResult) {
            mEngTextview.setText(EnglisthResult);
            mChiTextview.setText(ChineseResult);
            mIatButton.setEnabled(true);
            mIseEdittext.setText(EnglisthResult);
            super.onIatResult(EnglisthResult, ChineseResult);
        }

        @Override
        protected void onIseRecScoreResult(float score) {
            mIseResultTextview.setText("总得分：" + score);
            super.onIseRecScoreResult(score);
        }

        @Override
        protected void onTtsCompleted() {
            Log.info(LogTag,"onTtsCompleted");
            super.onTtsCompleted();
        }
    }
}

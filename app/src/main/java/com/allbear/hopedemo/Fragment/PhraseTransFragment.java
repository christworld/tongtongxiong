package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;

/**
 * Created by Administrator on 2017/3/19.
 */

public class PhraseTransFragment extends AllbearFragment implements View.OnClickListener {
    static String LogTag = "HopePhraseTransFragment";

    private Button mButtonTrans;
    private Button mButtonTalk;
    private Button mButtonPlayRecord;
    private Button mButtonPlayTts;
    private TextView mTextViewTrans;
    private TextView mTextViewTransEnglish;
    private TextView mTextViewEnglishTalk;
    private TextView mTextViewEnglishAnswer;

    private boolean mIsChinese2English = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.phrasetrans_fragment, container, false);
        }
        Log.info(LogTag,"onCreateView " + mView);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        mButtonTrans = (Button) getActivity().findViewById(R.id.id_trans_btn);
        mButtonTrans.setOnClickListener(this);
        mButtonTalk = (Button) getActivity().findViewById(R.id.id_talk_btn);
        mButtonTalk.setOnClickListener(this);
        mButtonPlayRecord = (Button) getActivity().findViewById(R.id.id_play_record_btn);
        mButtonPlayRecord.setOnClickListener(this);
        mButtonPlayTts = (Button) getActivity().findViewById(R.id.id_play_tts_btn);
        mButtonPlayTts.setOnClickListener(this);
        mTextViewTrans = (TextView) getActivity().findViewById(R.id.id_trans_tw);
        mTextViewTransEnglish = (TextView) getActivity().findViewById(R.id.id_trans_english_tw);
        mTextViewEnglishTalk = (TextView) getActivity().findViewById(R.id.id_english_talk_tw);
        mTextViewEnglishAnswer = (TextView) getActivity().findViewById(R.id.id_english_answer_tw);
    }
    @Override
    public void onTtsListenerCompleted() {
        super.onTtsListenerCompleted();
        Log.info(LogTag,"onTtsListenerCompleted");
    }
    @Override
    public void onIatGetResultText(String text) {
        super.onIatGetResultText(text);
        if(mIsChinese2English){
            mTextViewTrans.setText(text);
        }else {
            mTextViewEnglishTalk.setText(text);
        }
    }
    @Override
    public void onIatGetResultLast(String text) {
        super.onIatGetResultText(text);
        Log.info(LogTag,"text .length()=" + text.length());
        if(mIsChinese2English){
            mTextViewTrans.setText(text);
        }else {
            mTextViewEnglishTalk.setText(text);
        }
        if(text.length() > 1) {
            mAllbearTts.startTts(text);
            mAllbearTranslate.startTranslate(text);
        }
    }
    @Override
    public void onTranslateText(String text) {
        super.onIatGetResultText(text);
        Log.info(LogTag,"onTranslateText text =" + text);
        if(mIsChinese2English){
            mTextViewTransEnglish.setText(text);
        }else {
            mTextViewEnglishAnswer.setText(text);
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
        mAllbearTts.stopTts();
        mAllbearIat.stopIat();
        mMediaPlayer.stopPlay();
    }
    @Override
    public void onClick(View view){
//        Log.info(LogTag,"onClick view=" + view);
        int id = view.getId();
        if(id == R.id.id_trans_btn){
            mTextViewTrans.setText(null);
            mTextViewTransEnglish.setText(null);
            mIsChinese2English = true;
            mAllbearIat.startIat("mandarin");
        }else if(id == R.id.id_talk_btn){
            mTextViewEnglishTalk.setText(null);
            mTextViewEnglishAnswer.setText(null);
            mIsChinese2English = false;
            mAllbearIat.startIat("en_us");
        }else if(id == R.id.id_play_record_btn){
            mMediaPlayer.startPlaySDPath(Environment.getExternalStorageDirectory()+"/msc/iat.wav");
        }else if(id == R.id.id_play_tts_btn){
            mMediaPlayer.startPlaySDPath(Environment.getExternalStorageDirectory()+"/msc/tts.wav");
        }
    }
}

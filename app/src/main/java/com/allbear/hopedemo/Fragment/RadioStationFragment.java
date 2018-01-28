package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;

/**
 * Created by Administrator on 2017/11/9.
 */

public class RadioStationFragment extends AllbearFragment {
    static String LogTag = "HopeRadioStation";
    private Boolean mModeFlag=false;
    private EditText mModeEditText;
    private Button mButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        if(mView == null) {
            mView = inflater.inflate(R.layout.radio_station_fragment, container, false);
        }
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        mModeEditText = (EditText) mView.findViewById(R.id.mode_text_id);
        mButton = (Button) getActivity().findViewById(R.id.button_id);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.info(LogTag,"onRadioClick");
                onModeClick();
            }
        });
    }

    private void onModeClick() {
        mModeFlag = !mModeFlag;
        mModeEditText.setText(mModeFlag?"巡游模式":"电台模式");
    }

    @Override
    public void onEntryView() {
        super.onEntryView();
    }

    @Override
    public void onExitView() {
        super.onExitView();
        Log.info(LogTag,"onExitView");
    }
}

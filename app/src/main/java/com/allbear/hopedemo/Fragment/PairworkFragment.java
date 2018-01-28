package com.allbear.hopedemo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.R;


/**
 * Created by Administrator on 2017/3/19.
 */

public class PairworkFragment extends AllbearFragment {
    static String LogTag = "HopePairworkFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.info(LogTag,"onCreateView");
        return inflater.inflate(R.layout.pairwork_fragment, container, false);
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
    }
}

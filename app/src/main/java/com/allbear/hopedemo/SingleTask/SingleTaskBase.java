package com.allbear.hopedemo.SingleTask;

import com.allbear.hopedemo.Structure.EnglishTests;
import com.allbear.hopedemo.program.ProgramBase;

/**
 * Created by Administrator on 2018/1/28.
 */

public class SingleTaskBase extends ProgramBase {
    protected EnglishTests mEnglishTests;

    public SingleTaskBase(){
        mEnglishTests = EnglishTests.getInstance();
    }
}

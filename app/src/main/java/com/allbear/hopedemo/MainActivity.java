package com.allbear.hopedemo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.allbear.hopedemo.Alarm.AlarmSetTime;
import com.allbear.hopedemo.Fragment.AllbearFragment;
import com.allbear.hopedemo.Fragment.EnglishPicBookFragment;
import com.allbear.hopedemo.Fragment.RadioStationFragment;
import com.allbear.hopedemo.Fragment.SettingsFragment;
import com.allbear.hopedemo.Fragment.SingleTaskFragment;
import com.allbear.hopedemo.Log.Log;
import com.allbear.hopedemo.MySQL.MySQL;
import com.allbear.hopedemo.Engine.AllbearIat;
import com.allbear.hopedemo.Engine.AllbearMediaplayer;
import com.allbear.hopedemo.Engine.AllbearTranslate;
import com.allbear.hopedemo.Engine.AllbearTts;
import com.allbear.hopedemo.Engine.AllbearTulingTalk;
import com.allbear.hopedemo.Engine.AllbearWeather;
import com.allbear.hopedemo.Services.TtxServices;
import com.allbear.hopedemo.Structure.EnglishTests;
import com.allbear.hopedemo.Structure.ask_children;
import com.allbear.hopedemo.Structure.ask_robot;
import com.allbear.hopedemo.Structure.books;
import com.allbear.hopedemo.Structure.todayInfo;
import com.allbear.hopedemo.Structure.users;
import com.allbear.hopedemo.TtxData.TtxDataInit;
import com.allbear.hopedemo.program.GetupTime;
import com.allbear.hopedemo.util.Util;

public class MainActivity extends FragmentActivity implements OnClickListener
{
    static String LogTag = "HopeMainActivity";
    public static MainActivity instance=null;
    private Context mContext;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private static int mTabCount = 4;
    private int mInitSelcet = 3;
    private int mPreSelectItem;

    private List<LinearLayout> mTabLinearLayouts = new ArrayList<LinearLayout>();
    private int [] mTabLinearLayoutIds = {
            R.id.id_tab_introdeuce,
            R.id.id_tab_englishpicbook,
//            R.id.id_tab_pairwork,
//            R.id.id_tab_phrasetrans,
//            R.id.id_tab_intelligentspeaker,
            R.id.id_tab_settings,
            R.id.id_tab_singletask
    };
    private List<ImageButton> mImageButtons = new ArrayList<ImageButton>();
    private int [] mImageButtonIds = {
            R.id.id_tab_introdeuce_img,
            R.id.id_tab_englishpicbook_img,
//            R.id.id_tab_pairwork_img,
//            R.id.id_tab_phrasetrans_img,
//            R.id.id_tab_intelligentspeaker_img,
            R.id.id_tab_settings_img,
            R.id.id_tab_singletask_img
    };
    private int [] mImageNormalIds = {
            R.drawable.tab_color_normal,
            R.drawable.tab_color_normal,
//            R.drawable.tab_color_normal,
//            R.drawable.tab_color_normal,
            R.drawable.tab_color_normal,
            R.drawable.tab_color_normal
//            R.drawable.img_introdeuce_normal,
//            R.drawable.img_englishpicbook_normal,
//            R.drawable.img_pairwork_normal,
//            R.drawable.img_phrasetrans_normal,
//            R.drawable.img_intelligentspeaker_normal,
//            R.drawable.img_settings_normal
    };
    private int [] mImagePressIds = {
            R.drawable.tab_color_pressed,
            R.drawable.tab_color_pressed,
//            R.drawable.tab_color_pressed,
//            R.drawable.tab_color_pressed,
            R.drawable.tab_color_pressed,
            R.drawable.tab_color_pressed
//            R.drawable.img_introdeuce_press,
//            R.drawable.img_englishpicbook_press,
//            R.drawable.img_pairwork_press,
//            R.drawable.img_phrasetrans_press,
//            R.drawable.img_intelligentspeaker_press,
//            R.drawable.img_settings_press
    };
    private Fragment mRadioStationFragment;
//    private Fragment mIntroduceFragment;
    private Fragment mEnglishPicBookFragment;
//    private Fragment mPairworkFragment;
//    private Fragment mPhraseTransFragment;
//    private Fragment mIntelligentSpeakerFragment;
    private Fragment mSettingsFragment;
    private Fragment mSingleTaskFragment;

    private AllbearMediaplayer mMediaPlayer;
    private AllbearTts mAllbearTts;
    private AllbearIat mAllbearIat;
    private AllbearTranslate mAllbearTranslate;
    private AllbearTulingTalk mAllbearTulingTalk;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        requestPermissions();
        mContext = this;
        Util.setMainActivityContext(mContext);
        instance = this;
        init();
        initView();
        initSelect();
        startTtxService();
    }
    private AllbearFragment getCurrentFragment(){
        AllbearFragment allbearFragment = (AllbearFragment)mFragments.get(mViewPager.getCurrentItem());
        return allbearFragment;
    }
    public void onMediaPlayerCompletion(){
        Log.info(LogTag,"onMediaPlayerCompletion");
        getCurrentFragment().onMediaPlayerCompletion();
    }
    public void onTtsListenerCompleted(){
        getCurrentFragment().onTtsListenerCompleted();
    }
    public void onIatGetResultText(String text){
        getCurrentFragment().onIatGetResultText(text);
    }
    public void onIatGetResultLast(String text){
        getCurrentFragment().onIatGetResultLast(text);
    }
    public void onTranslateText(String text){
        Log.info(LogTag,"onTranslateText text =" + text);
        getCurrentFragment().onTranslateText(text);
    }
    public void onTulingTalkText(String text){
//        Log.info(LogTag,"onTulingTalkText text =" + text);
        getCurrentFragment().onTulingTalkText(text);
    }
    private void init(){
        mMediaPlayer = AllbearMediaplayer.getInstance(this);
        mAllbearTts = AllbearTts.getInstance(this);
        mAllbearIat = AllbearIat.getInstance(this);
        mAllbearTranslate = AllbearTranslate.getInstance(this);
        mAllbearTulingTalk = AllbearTulingTalk.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.stopPlay();
        mAllbearTts.stopTts();
        mAllbearIat.stopIat();
    }
    @Override
    protected void onDestroy() {
        stopTtxService();
        mMediaPlayer.destory();
        mAllbearTts.destory();
        mAllbearIat.destory();
        mAllbearTulingTalk.onExit();
        mAllbearTranslate.onExit();
        super.onDestroy();
    }

    private void initSelect() {
        mPreSelectItem = mInitSelcet;
        mViewPager.setCurrentItem(mInitSelcet);
        mImageButtons.get(mInitSelcet).setBackgroundResource(mImagePressIds[0]);
        AllbearFragment allbearFragment = (AllbearFragment)mFragments.get(mInitSelcet);
        allbearFragment.onEntryView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mRadioStationFragment = new RadioStationFragment();
        mEnglishPicBookFragment = new EnglishPicBookFragment();
        mSettingsFragment = new SettingsFragment();
        mSingleTaskFragment = new SingleTaskFragment();
        mFragments.add(mRadioStationFragment);
        mFragments.add(mEnglishPicBookFragment);
        mFragments.add(mSettingsFragment);
        mFragments.add(mSingleTaskFragment);

        for(int i = 0;i < mTabCount; i++){
            mTabLinearLayouts.add((LinearLayout) findViewById(mTabLinearLayoutIds[i]));
            mTabLinearLayouts.get(i).setOnClickListener(this);
            mImageButtons.add((ImageButton) findViewById(mImageButtonIds[i]));
            mImageButtons.get(i).setBackgroundResource(mImageNormalIds[i]);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTabCount;
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                exitEntryFragmentView(currentItem);
                setTab(currentItem);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0){}
        });
    }
    private void exitEntryFragmentView(int selectItem){
        for(int i = 0;i < mFragments.size();i++){
            AllbearFragment allbearFragment = (AllbearFragment)mFragments.get(i);
            if(i == selectItem){
                allbearFragment.onEntryView();
            }else if(i == mPreSelectItem){
                allbearFragment.onExitView();
            }
        }
    }
    @Override
    public void onClick(View v) {
        for(int i = 0;i < mTabCount; i++){
            if(v.getId() == mTabLinearLayoutIds[i]){
                setSelect(i);
                break;
            }
        }
        Log.info(LogTag,"onClick context= " + mContext);
//        Intent bindIntent = new Intent(this, TtxServices.class);
//        bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void setSelect(int i) {
        int currentItem = mViewPager.getCurrentItem();
        if(i == currentItem){
            return;
        }
        mViewPager.setCurrentItem(i);
    }

    private void setTab(int i) {
        mImageButtons.get(mPreSelectItem).setBackgroundResource(mImageNormalIds[mPreSelectItem]);
        mImageButtons.get(i).setBackgroundResource(mImagePressIds[i]);
        mPreSelectItem = i;
    }
    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.LOCATION_HARDWARE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO},0x0010);
                }
            }
        } catch (Exception e) {
            Log.info(LogTag,"requestPermissions e=" + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private TtxServices.MyBinder myBinder;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.info(LogTag,"onServiceDisconnected name=" + name);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.info(LogTag,"onServiceConnected name=" + name);
            myBinder = (TtxServices.MyBinder) service;
            myBinder.startDownload();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                AllbearWeather.getInstance(mContext).getWeather("海西");
//            }
//        },1000);
        Log.info(LogTag,"onResume context= " + mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unbindService(mServiceConnection);
    }
//        Intent bindIntent = new Intent(this, TtxServices.class);
//        bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);

    private int isDebug = 0;
    private void doDebugHere(){

//        Util.checkBirthDay("1990-12-14 00:00:00");
//        setLangTtsStrings("Aa普通话123How are you !普通话");
        Log.info(LogTag,"doDebugHere getIsHoliday=" + todayInfo.getInstance().getIsHoliday());
    }

    private void startTtxService(){
        if(isDebug == 1){
            doDebugHere();
            return;
        }
        Intent startIntent = new Intent(this, TtxServices.class);
        startService(startIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InitDataAll();
            }
        },2000);
    }
    private void stopTtxService(){
        Intent stopIntent = new Intent(this, TtxServices.class);
        stopService(stopIntent);
    }
    private void InitDataAll(){
        AlarmSetTime.getInstance().setAlarmTime();
        users.getInstance().InitData();
        books.getInstance().InitData();
        ask_children.getInstance().InitData();
        todayInfo.getInstance().InitData();
        ask_robot.getInstance().InitData();
        EnglishTests.getInstance().InitData();
    }
}

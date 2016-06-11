package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;
import com.moral.airtree.widget.GifView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends ABaseActivity {

    private int mCurIndex = 0;
    private List<MonitorFragment> mFragmentList;
    private GifView mGifView;
    private ImageView mIvLeft;
    private Monitor mMonitor;
    private MonitorEnum mMonitorType;
    private CirclePageIndicator mPagerIndicator;
    private TextView mTvContent;
    private TextView mTvTitle;
    private ViewPager mViewPager;
    private MyPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        mMonitorType = MonitorEnum.PM;
        if(getIntent() != null) {
            mMonitorType = (MonitorEnum)getIntent().getSerializableExtra("monitorType");
            mMonitor = (Monitor)getIntent().getSerializableExtra("monitor");
        }

        mFragmentList = new ArrayList<>();

        if(mMonitorType == MonitorEnum.TEMPERATURE) {
            mCurIndex = 1;
        } else if(mMonitorType == MonitorEnum.HUMIDITY) {
            mCurIndex = 2;
        } else if(mMonitorType == MonitorEnum.FORMALDEHYDE) {
            mCurIndex = 3;
        }

        initTitleBar();
        initFragments();
        initView();
        initData();
    }

    private void initTitleBar() {
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager)findViewById(R.id.vp);
        mViewPagerAdapter = new MonitorActivity.MyPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mPagerIndicator = (CirclePageIndicator)findViewById(R.id.vc_pageindicator);
        mPagerIndicator.setViewPager(mViewPager);
        mTvContent = (TextView)findViewById(R.id.tv_content);
        setViewPagerChanger();
        setFragmentTitleAndTip(0);
        mViewPager.setCurrentItem(mCurIndex);
        mGifView = (GifView)findViewById(R.id.gifview);
    }

    private void initData() {
        if(mMonitor.getPm().getPm_data() != null) {
            long pmData = mMonitor.getPm().getPm_data().longValue();
            if((pmData > 0x0) && (pmData <= 0x23)) {
                //mGifView.setMovieResource(0x7f050004);
                mTvContent.setText(getResources().getString(R.string.good));
                return;
            }
            if((pmData > 0x23) && (pmData <= 0x4b)) {
                //mGifView.setMovieResource(0x7f050007);
                mTvContent.setText(getResources().getString(R.string.very));
                return;
            }
            if((pmData > 0x4b) && (pmData <= 0x96)) {
                //mGifView.setMovieResource(0x7f050001);
                mTvContent.setText(getResources().getString(R.string.general));
                return;
            }
            if(pmData > 0x96) {
                //mGifView.setMovieResource(0x7f050006);
                mTvContent.setText(getResources().getString(R.string.poor));
            }
        }
    }

    private void setFragmentTitleAndTip(int position) {
        if(position == 0) {
            mTvTitle.setText(R.string.monitor_pm);
            return;
        }
        if(position == 1) {
            mTvTitle.setText(R.string.monitor_temperature);
            return;
        }
        if(position == 2) {
            mTvTitle.setText(R.string.monitor_humidity);
            return;
        }
        if(position == 3) {
            mTvTitle.setText(R.string.monitor_formaldehyde);
        }
    }

    private void setViewPagerChanger() {
        if(mViewPager == null) {
            return;
        }
        mPagerIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    protected void setFragmentPage(int position) {
        MonitorFragment fragment = (MonitorFragment) mViewPagerAdapter.getItem((position - 1));
        if(position == 1) {
            fragment.initViewData(MonitorEnum.PM);
            return;
        }
        if(position == 2) {
            fragment.initViewData(MonitorEnum.TEMPERATURE);
            return;
        }
        if(position == 3) {
            fragment.initViewData(MonitorEnum.HUMIDITY);
            return;
        }
        if(position == 4) {
            fragment.initViewData(MonitorEnum.FORMALDEHYDE);
        }
    }

    private void initFragments() {
        mFragmentList.add(new MonitorFragment(MonitorEnum.PM, mMonitor));
        mFragmentList.add(new MonitorFragment(MonitorEnum.TEMPERATURE, mMonitor));
        mFragmentList.add(new MonitorFragment(MonitorEnum.HUMIDITY, mMonitor));
        mFragmentList.add(new MonitorFragment(MonitorEnum.FORMALDEHYDE, mMonitor));
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(MonitorActivity p1, FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment)mFragmentList.get(position);
        }

        public int getCount() {
            return mFragmentList.size();
        }
    }
}

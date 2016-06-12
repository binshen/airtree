package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moral.airtree.model.Device;
import com.viewpagerindicator.CirclePageIndicator;
import com.moral.airtree.common.ABaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ABaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ViewPager mViewPager;
    private CirclePageIndicator mPagerIndicator;
    private List<Device> mDevices;
    private List<Fragment> mFragmentList;
    private DevicePagerAdapter mViewPagerAdapter;
    private boolean mIsFirst;
    private TextView mTvDeviceManager;
    private TextView mTvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevices = new ArrayList<Device>();
        mFragmentList = new ArrayList<Fragment>();

        ImageView ivPersonal = (ImageView)findViewById(R.id.iv_personal);
        ivPersonal.setOnClickListener(this);
        ImageView ivMall = (ImageView)findViewById(R.id.iv_mall);
        ivMall.setOnClickListener(this);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvDeviceManager = (TextView)findViewById(R.id.tv_devicemanager);
        mTvDeviceManager.setOnClickListener(this);
        mTvHistory = (TextView)findViewById(R.id.tv_history);
        mTvHistory.setOnClickListener(this);

        mViewPager = (ViewPager)findViewById(R.id.viewPaper);
        mViewPagerAdapter = new DevicePagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mPagerIndicator = (CirclePageIndicator)findViewById(R.id.pageindicator);
        mPagerIndicator.setViewPager(mViewPager);

        mIsFirst = true;
        setViewPagerChanger();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mIsFirst) {
            mIsFirst = false;
            addFragments();
        }
//        else if(AirTreePreference.getInstance(this).getDeviceChanged()) {
//            AirTreePreference.getInstance(this).setDeviceChanged(false);
//            addFragments();
//        }
        setFragmentTitle();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.iv_personal:
                //startActivity(new Intent(this, PersonalMainActivity.class));
                startActivityForResult(new Intent(this, PersonalMainActivity.class), 1);
                break;

            case R.id.iv_mall:
                startActivity(new Intent(this, ShopActivity.class));
                break;

            case R.id.tv_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;

            case R.id.tv_devicemanager:
                startActivity(new Intent(this, DeviceManagerActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                finish();
            }
        }
    }

    private void addFragments() {
        mLoadDialog.show();
        removeFragments();

        Device device = new Device();
        device.setIp("192.168.2.13");
        device.setMac("ea24a2d456");
        device.setName("测试设备1");
        device.setStatus(1);

        mDevices.add(device);

        Fragment roomFragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("device", device);
        roomFragment.setArguments(bundle);
        mFragmentList.add(roomFragment);

        device = new Device();
        device.setIp("192.168.2.14");
        device.setMac("ea24a2d457");
        device.setName("测试设备2");
        device.setStatus(2);

        mDevices.add(device);

        roomFragment = new RoomFragment();
        bundle = new Bundle();
        bundle.putSerializable("device", device);
        roomFragment.setArguments(bundle);
        mFragmentList.add(roomFragment);

        device = new Device();
        device.setIp("192.168.2.15");
        device.setMac("ea24a2d458");
        device.setName("测试设备3");
        device.setStatus(3);

        mDevices.add(device);

        roomFragment = new RoomFragment();
        bundle = new Bundle();
        bundle.putSerializable("device", device);
        roomFragment.setArguments(bundle);
        mFragmentList.add(roomFragment);

        runOnUiThread (new Thread(new Runnable() {
            public void run() {
                mViewPagerAdapter.notifyDataSetChanged();
                mPagerIndicator.notifyDataSetChanged();

                mLoadDialog.dismiss();
            }
        }));
    }

    private void removeFragments() {
        mFragmentList.clear();
        mViewPager.removeAllViews();
        mViewPagerAdapter.notifyDataSetChanged();
        mPagerIndicator.notifyDataSetChanged();
    }

    private void setViewPagerChanger() {
        if(mViewPager == null) {
            return;
        }
        mPagerIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                setFragmentTitle();
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void setFragmentTitle() {
        int position = mViewPager.getCurrentItem();

        if((mDevices.size() > 0) && (position < mDevices.size())) {
            Device device = (Device)mDevices.get(position);
            if((device != null) && (!TextUtils.isEmpty(device.getMac()))) {
                mTvTitle.setText(device.getName());
                //mDeviceId = device.getDeviceId();
            }
            return;
        }
        mTvTitle.setText("未知设备");
    }

    class DevicePagerAdapter extends FragmentPagerAdapter {
        public DevicePagerAdapter(MainActivity p1, FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) mFragmentList.get(position);
        }

        public int getItemPosition(Object object) {
            return -0x2;
        }

        public int getCount() {
            return mFragmentList.size();
        }
    }
}

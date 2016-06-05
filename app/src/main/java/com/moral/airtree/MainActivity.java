package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.moral.airtree.common.ABaseActivity;

import java.util.List;

public class MainActivity extends ABaseActivity implements View.OnClickListener {

    TextView mTvTitle;

    ViewPager mViewPager;
    CirclePageIndicator mPagerIndicator;
    List<Fragment> mFragmentList;
    DevicePagerAdapter mViewPagerAdapter;
    private boolean mIsFirst;

    private TextView mTvDeviceManager;
    private TextView mTvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

        setViewPagerChanger();
    }

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

        /*
        DeviceRequestBiz deviceBiz = new DeviceRequestBiz(this);
        deviceBiz.requestDeviceList(new Handler(this) {

            1(MainActivity p1) {
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ArrayList<ACUserDevice> devices = msg.obj;
                if(devices != null) {
                    mACUserDevices.clear();
                    mACUserDevices.addAll(devices);
                    if(!devices.iterator().hasNext()) {
                    }
                    ACUserDevice device = (ACUserDevice)devices.iterator().next();
                    mFragmentList.add(new RoomFragment(device));
                    runOnUiThread(new Runnable(this) {

                        1(MainActivity.1 p1) {
                        }

                        public void run() {
                            MainActivity.1.this$0.mViewPagerAdapter.notifyDataSetChanged();
                            MainActivity.1.this$0.mPagerIndicator.notifyDataSetChanged();
                        }
                    });
                }
                mLoadDialog.dismiss();
            }
        });
        */
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
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private void setFragmentTitle() {
        int position = mViewPager.getCurrentItem();
        /*
        if((mACUserDevices.size() > 0) && (position < mACUserDevices.size())) {
            ACUserDevice device = (ACUserDevice)mACUserDevices.get(position);
            if((device != null) && (!TextUtils.isEmpty(device.getName()))) {
                mTvTitle.setText(device.getName());
                mDeviceId = device.getDeviceId();
            }
            return;
        }
        */
        mTvTitle.setText("XXXXX");
    }

    class DevicePagerAdapter extends FragmentPagerAdapter {
        public DevicePagerAdapter(MainActivity p1, FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment)mFragmentList.get(position);
        }

        public int getItemPosition(Object object) {
            return -0x2;
        }

        public int getCount() {
            return mFragmentList.size();
        }
    }
}

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.model.Device;
import com.viewpagerindicator.CirclePageIndicator;
import com.moral.airtree.common.ABaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

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
//        setFragmentTitle();
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

        String url = basePath + "/user/" + application.getLoginUser().get_id() + "/get_device";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject obj = response.optJSONObject(i);

                    Device device = new Device();
                    device.setMac(obj.optString("mac"));
                    if(!obj.optString("name").isEmpty()) {
                        device.setName(obj.optString("name"));
                    } else {
                        device.setName(obj.optString("mac"));
                    }
                    device.setStatus(obj.optInt("status"));
                    mDevices.add(device);

                    Fragment roomFragment = new RoomFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("device", device);
                    roomFragment.setArguments(bundle);
                    mFragmentList.add(roomFragment);
                }

                setFragmentTitle();
                runOnUiThread (new Thread(new Runnable() {
                    public void run() {
                        mViewPagerAdapter.notifyDataSetChanged();
                        mPagerIndicator.notifyDataSetChanged();
                        mLoadDialog.dismiss();
                    }
                }));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonRequest);
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

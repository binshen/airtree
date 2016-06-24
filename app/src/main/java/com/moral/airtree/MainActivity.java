package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.model.Device;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorFormaldehyde;
import com.moral.airtree.model.MonitorHumidity;
import com.moral.airtree.model.MonitorPm;
import com.moral.airtree.model.MonitorTemperature;
import com.moral.airtree.model.MonitorWindSpeed;
import com.viewpagerindicator.CirclePageIndicator;
import com.moral.airtree.common.ABaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends ABaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ViewPager mViewPager;
    private CirclePageIndicator mPagerIndicator;
    private List<Device> mDevices;
    private List<Fragment> mFragmentList;
    private DevicePagerAdapter mViewPagerAdapter;
    private TextView mTvDeviceManager;

    private ImageView ivPersonal;
    private ImageView ivMallIcon;
    private TextView ivMall;

    private long clickTime = 0;

    private Handler timeHandler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            addFragments();
            timeHandler.postDelayed(this, 6000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevices = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        ivPersonal = (ImageView)findViewById(R.id.iv_personal);
        ivPersonal.setOnClickListener(this);
        ivMall = (TextView)findViewById(R.id.iv_mall);
        ivMall.setOnClickListener(this);
        ivMallIcon = (ImageView)findViewById(R.id.iv_mall_icon);
        ivMallIcon.setOnClickListener(this);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvDeviceManager = (TextView)findViewById(R.id.tv_devicemanager);
        mTvDeviceManager.setOnClickListener(this);

        mViewPager = (ViewPager)findViewById(R.id.viewPaper);
        mViewPagerAdapter = new DevicePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mPagerIndicator = (CirclePageIndicator)findViewById(R.id.pageindicator);
        mPagerIndicator.setViewPager(mViewPager);

        setViewPagerChanger();

        addFragments();

        timeHandler.removeCallbacks(runnable);
        timeHandler.postDelayed(runnable, 6000);
    }

    public void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(runnable);
    }

    private void addFragments() {
        mLoadDialog.show();
        removeFragments();

        String url = basePath + "/user/" + application.getLoginUserID() + "/get_device";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                application.setDeviceChanged(false);

                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.optJSONObject(i);

                    Device device = new Device();
                    device.setMac(json.optString("mac"));
                    if(!json.optString("name").isEmpty()) {
                        device.setName(json.optString("name"));
                    } else {
                        device.setName(json.optString("mac"));
                    }
                    device.setStatus(json.optInt("status"));
                    device.set_id(json.optString("_id"));
                    device.setType(json.optInt("type"));
                    device.setLast_updated(json.optLong("last_updated"));
                    mDevices.add(device);

                    Fragment roomFragment = new RoomFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("device", device);

                    Monitor mMonitor = null;
                    JSONObject data = json.optJSONObject("data");
                    if(data != null) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date  = dateFormat.format(new Date(data.optLong("created")));

                        MonitorPm mPm = new MonitorPm(date, data.optLong("x1"), "");
                        mPm.setPm03p01(data.optInt("x3"));
                        MonitorWindSpeed mWs = new MonitorWindSpeed(date, data.optLong("x12"), "");
                        MonitorHumidity mHu = new MonitorHumidity(data.optString("x10"), date, "");
                        MonitorTemperature mTe = new MonitorTemperature(data.optString("x11"), date, "");
                        MonitorFormaldehyde mFo = new MonitorFormaldehyde(data.optLong("x9"), date, "");
                        mMonitor = new Monitor("", data.optString("x13"), "", data.optLong("x14"), "", 1l, "", date, mPm, mWs, mHu, mTe, mFo);
                    } else {
                        MonitorPm mPm = new MonitorPm("", 0l, "");
                        mPm.setPm03p01(0);
                        MonitorWindSpeed mWs = new MonitorWindSpeed("", 0l, "");
                        MonitorHumidity mHu = new MonitorHumidity("", "", "");
                        MonitorTemperature mTe = new MonitorTemperature("", "", "");
                        MonitorFormaldehyde mFo = new MonitorFormaldehyde(0l, "", "");
                        mMonitor = new Monitor("", "", "", 0l, "", 0l, "", "", mPm, mWs, mHu, mTe, mFo);
                    }
                    bundle.putSerializable("monitor", mMonitor);

                    roomFragment.setArguments(bundle);
                    mFragmentList.add(roomFragment);
                }
                application.setDevices(mDevices);

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
        mDevices.clear();
        mFragmentList.clear();
        mViewPager.removeAllViews();
        mViewPagerAdapter.notifyDataSetChanged();
        mPagerIndicator.notifyDataSetChanged();
    }

    private void setFragmentTitle() {
        int position = mViewPager.getCurrentItem();
        if((mDevices.size() > 0) && (position < mDevices.size())) {
            Device device = mDevices.get(position);
            if((device != null) && (!TextUtils.isEmpty(device.getMac()))) {
                mTvTitle.setText(device.getName());
            }
            return;
        }
        mTvTitle.setText("未知设备");
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

    class DevicePagerAdapter extends FragmentStatePagerAdapter {
        public DevicePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.iv_personal:
                startActivityForResult(new Intent(this, PersonalMainActivity.class), 1);
                break;

            case R.id.iv_mall:
            case R.id.iv_mall_icon:
                startActivity(new Intent(this, ShopActivity.class));
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
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

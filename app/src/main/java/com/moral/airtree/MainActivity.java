package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.moral.airtree.common.AConstants;
import com.moral.airtree.model.Device;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.service.HeartbeatService;
import com.moral.airtree.update.UpdateManager;
import com.moral.airtree.utils.NetUtils;
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
    //private TextView mTvDeviceManager;

    private ImageView ivPersonal;
    private ImageView ivMallIcon;
    //private TextView ivMall;

    private LinearLayout rl_devicemanager;
    private LinearLayout rl_mall;

    private long clickTime = 0;

    private long firstClick;
    private long lastClick;
    // 计算点击的次数
    private int count;

    private Handler timeHandler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            addFragments();
            timeHandler.postDelayed(this, 6000);
        }
    };

    protected Integer getDialogThemeResId() {
        return R.style.load_dialog2;
    }

    protected boolean getCanceledOnTouch() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Log.d("MainActivity", dm.widthPixels + " | " + dm.heightPixels + " | " + dm.density + " | " + dm.densityDpi);

        prepare();

        mDevices = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        mTvTitle = (TextView)findViewById(R.id.tv_title);

        ivPersonal = (ImageView)findViewById(R.id.iv_personal);
        ivPersonal.setOnClickListener(this);

        //ivMall = (TextView)findViewById(R.id.iv_mall);
        //ivMall.setOnClickListener(this);

        ivMallIcon = (ImageView)findViewById(R.id.iv_mall_icon);
        ivMallIcon.setOnClickListener(this);

        rl_devicemanager = (LinearLayout)findViewById(R.id.rl_devicemanager);
        rl_devicemanager.setOnClickListener(this);

        rl_mall = (LinearLayout)findViewById(R.id.rl_mall);
        rl_mall.setOnClickListener(this);

        //mTvDeviceManager = (TextView)findViewById(R.id.tv_devicemanager);
        //mTvDeviceManager.setOnClickListener(this);

        mViewPager = (ViewPager)findViewById(R.id.viewPaper);
        mViewPagerAdapter = new DevicePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {
                            count = 0;
                        }
                        count++;
                        if (count == 1) {
                            firstClick = System.currentTimeMillis();
                        } else if (count == 2) {
                            lastClick = System.currentTimeMillis();
                            if (lastClick - firstClick < 300) {
                                addFragments();
                                timeHandler.removeCallbacks(runnable);
                                timeHandler.postDelayed(runnable, 6000);
                            }
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

        mPagerIndicator = (CirclePageIndicator)findViewById(R.id.pageindicator);
        mPagerIndicator.setViewPager(mViewPager);

        setViewPagerChanger();

        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, HeartbeatService.class);
        serviceIntent.putExtra("LoginUserID", application.getLoginUserID());
        startService(serviceIntent);

        ivMallIcon.setVisibility(View.INVISIBLE);
    }

    private void prepare() {
        if (!NetUtils.getNetConnect(this)) {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        } else {
            //boolean loginFlag = getIntent().getBooleanExtra("LoginFlag", false);
            //if(!loginFlag) {
                if (Build.VERSION.SDK_INT >= 11) {
                    new checkUpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    new checkUpdateTask().execute();
                }
            //}
        }
    }

    private class checkUpdateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Looper.prepare();
            // 检查软件更新
            //UpdateManager manager = new UpdateManager(MainActivity.this);
            manager.checkUpdate();

            Looper.loop();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    @Override
    protected void onStart() {
        addFragments();
        super.onStart();
    }

    @Override
    protected void onResume() {
        timeHandler.removeCallbacks(runnable);
        timeHandler.postDelayed(runnable, 6000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        timeHandler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        timeHandler.removeCallbacks(runnable);

        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, HeartbeatService.class);
        stopService(serviceIntent);
        super.onDestroy();
    }

    private void addFragments() {
        mLoadDialog.show();

        String url = basePath + "/user/" + application.getLoginUserID() + "/get_device";
        RequestQueue queue = application.getRequestQueue();
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("MainActivity", response.toString());
                removeFragments();

                if(response.length() < 1) {
                    mLoadDialog.dismiss();
                    return;
                }

                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.optJSONObject(i);

                    Device mDevice = new Device();
                    mDevice.set_id(json.optString("_id"));
                    mDevice.setMac(json.optString("mac"));
                    if(!json.optString("name").isEmpty()) {
                        mDevice.setName(json.optString("name"));
                    } else {
                        mDevice.setName(json.optString("mac"));
                    }
                    mDevice.setType(json.optInt("type"));
                    mDevice.setStatus(json.optInt("status"));
                    mDevice.setLast_updated(json.optLong("last_updated"));
                    mDevices.add(mDevice);

                    Monitor mMonitor = new Monitor();
                    JSONObject data = json.optJSONObject("data");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(data != null) {
                        String date  = dateFormat.format(new Date(data.optLong("created")));
                        mMonitor.setCreated(date);
                        mMonitor.setPm_data(data.optLong("x1"));
                        mMonitor.setPm03p01(data.optLong("x3"));
                        mMonitor.setFormaldehyde_data(data.optDouble("x9"));
                        mMonitor.setHumidity_data(data.optInt("x10"));
                        mMonitor.setTemperature_data(data.optInt("x11"));
                        mMonitor.setWindSpeed_data(data.optLong("x12"));
                        mMonitor.setElectricQuantity(data.optInt("x13"));
                        mMonitor.setLight(data.optLong("x14"));
                        mMonitor.setPriority1(data.optInt("p1"));
                        mMonitor.setPriority2(data.optInt("p2"));
                        mMonitor.setPriority3(data.optInt("p3"));
                        mMonitor.setPriority4(data.optInt("p4"));
                        mMonitor.setFeiLevel(data.optInt("fei"));
                    } else {
                        mMonitor.setCreated(null);
                        mMonitor.setPm_data(0l);
                        mMonitor.setPm03p01(0l);
                        mMonitor.setFormaldehyde_data(0d);
                        mMonitor.setHumidity_data(0);
                        mMonitor.setTemperature_data(0);
                        mMonitor.setWindSpeed_data(0l);
                        mMonitor.setElectricQuantity(0);
                        mMonitor.setLight(0l);
                        mMonitor.setPriority1(0);
                        mMonitor.setPriority2(0);
                        mMonitor.setPriority3(0);
                        mMonitor.setPriority4(0);
                        mMonitor.setFeiLevel(0);
                    }
                    mFragmentList.add(new RoomFragment().newInstance(mDevice, mMonitor));
                }
                //application.setDevices(mDevices);

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
                Toast.makeText(getApplicationContext(), AConstants.IS_DEBUG_MODE ? error.toString() : "网络故障，请稍候重试", Toast.LENGTH_SHORT).show();
                if (mLoadDialog.isShowing()) {
                    mLoadDialog.dismiss();
                }
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

            //case R.id.iv_mall:
            case R.id.rl_mall:
            case R.id.iv_mall_icon:
                startActivity(new Intent(this, ShopActivity.class));
                break;

            //case R.id.tv_devicemanager:
            case R.id.rl_devicemanager:
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
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

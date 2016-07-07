package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.moral.airtree.adapter.DeviceAdapter;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.AConstants;
import com.moral.airtree.model.Device;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceManagerActivity extends ABaseActivity {

    private DeviceAdapter mDevicesAdapter;
    private List<Device> mDevices = new ArrayList<>();

    private ImageView mIvLeft;
    private ImageView mIvRight;
    private ListView mLv;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_manager);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mIvRight = (ImageView)findViewById(R.id.right_btn);
        mTvTitle.setText("设备管理");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvRight.setImageResource(R.mipmap.add_contact);
        mIvLeft.setImageResource(R.mipmap.back);
        mIvLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLv = (ListView)findViewById(R.id.lv);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), DeviceInfoActivty.class);
                Bundle bundle = new Bundle();
                bundle.putInt("devicePosition", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeviceAddActivity.class));
            }
        });
    }

    private Handler timeHandler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            requestDeviceData();
            timeHandler.postDelayed(this, 6000);
        }
    };

    @Override
    protected void onStart() {
        requestDeviceData();
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
    protected void onDestroy() {
        timeHandler.removeCallbacks(runnable);
        super.onDestroy();
    }

//    private void initData() {
//        if(application.isDeviceChanged) {
//            requestDeviceData();
//        } else {
//            mDevices = application.getDevices();
//
//            mDevicesAdapter = new DeviceAdapter(this, mDevices);
//            mLv.setAdapter(mDevicesAdapter);
//            mDevicesAdapter.notifyDataSetChanged();
//        }
//    }

    private void requestDeviceData() {
        String url = basePath + "/user/" + application.getLoginUserID() + "/get_device_info";
        RequestQueue queue = application.getRequestQueue();
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DeviceManagerActivity", response.toString());

                mDevices.clear();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.optJSONObject(i);

                    Device device = new Device();
                    device.set_id(json.optString("_id"));
                    device.setMac(json.optString("mac"));
                    if(!json.optString("name").isEmpty()) {
                        device.setName(json.optString("name"));
                    } else {
                        device.setName(json.optString("mac"));
                    }
                    device.setType(json.optInt("type"));
                    device.setStatus(json.optInt("status"));
                    device.setLast_updated(json.optLong("last_updated"));
                    mDevices.add(device);
                }
                application.setDevices(mDevices);

                mDevicesAdapter = new DeviceAdapter(getApplicationContext(), mDevices);
                mLv.setAdapter(mDevicesAdapter);
                mDevicesAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(AConstants.IS_DEBUG_MODE){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
                mLoadDialog.dismiss();
            }
        });
        queue.add(jsonRequest);
    }
}

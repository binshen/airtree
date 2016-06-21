package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.Volley;
import com.moral.airtree.adapter.DeviceAdapter;
import com.moral.airtree.common.ABaseActivity;
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
                bundle.putSerializable("device", mDevices.get(position));
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

    @Override
    protected void onStart() {
        super.onStart();

        initData();
    }

    private void initData() {
        if(application.isDeviceChanged) {
            String url = basePath + "/user/" + application.getLoginUserID() + "/get_device";
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    application.setDeviceChanged(false);

                    mDevices.clear();
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
                        device.set_id(obj.optString("_id"));
                        mDevices.add(device);
                    }
                    application.setDevices(mDevices);
                    mDevicesAdapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonRequest);
        } else {
            mDevices = application.getDevices();
            mDevicesAdapter = new DeviceAdapter(this, mDevices);
            mLv.setAdapter(mDevicesAdapter);
            mDevicesAdapter.notifyDataSetChanged();
        }
    }
}

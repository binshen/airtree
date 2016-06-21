package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.Device;
import com.moral.airtree.model.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeviceDetailReviseActivity extends ABaseActivity {

    private static final int MAX_COUNT = 0x14;
    private Button mBtnOk;
    private String mDeviceId;
    ArrayList<Device> mDevises;
    private EditText mEtDeviceDetail;
    private ImageView mIvLeft;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail_revise);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("修改设备名称");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mEtDeviceDetail = (EditText)findViewById(R.id.et_devicedetail);
        mBtnOk = (Button)findViewById(R.id.btn_ok);

        Bundle bundle = getIntent().getExtras();
        mDeviceId = bundle.getString("deviceid");
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDeviceName(mDeviceId, mEtDeviceDetail.getText().toString());
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    public void changeDeviceName(String deviceId, String deviceName) {
        if(deviceName == null || deviceName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入设备名称", Toast.LENGTH_LONG).show();
        } else {
            mLoadDialog.show();

            String url = basePath + "/user/" + application.getLoginUserID() + "/device/" + deviceId + "/update_name";
            RequestQueue queue = Volley.newRequestQueue(this);

            final Map<String, String> params = new HashMap<String, String>();
            params.put("name", deviceName);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    boolean success = response.optBoolean("success");
                    if (success) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response.optString("error"), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(jsonRequest);
        }
    }
}

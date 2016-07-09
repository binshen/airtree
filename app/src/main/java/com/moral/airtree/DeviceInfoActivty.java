package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.AConstants;
import com.moral.airtree.model.Device;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeviceInfoActivty extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRemovebind;
    private Device mDevice;
    private int mPosition;
    private ImageView mIvLeft;
    private RelativeLayout mRl1;
    private RelativeLayout mRl4;
    private TextView mTvBianma;
    private TextView mTvMac;
    private TextView mTvStyle;
    private TextView mTvTitle;
    private TextView mTvWhere;
    private TextView mTvCheckStatus;
    private RelativeLayout mRlCheck;
    private View rl_check_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info_activty);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("设备信息");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mBtnRemovebind = (Button)findViewById(R.id.btn_removebind);
        mTvBianma = (TextView)findViewById(R.id.tv_bianma);
        mTvMac = (TextView)findViewById(R.id.tv_mac);
        mTvWhere = (TextView)findViewById(R.id.tv_where);
        mTvStyle = (TextView)findViewById(R.id.tv_style);
        mTvCheckStatus = (TextView)findViewById(R.id.tv_check_status);
        mRl1 = (RelativeLayout)findViewById(R.id.rl1);
        mRl1.setOnClickListener(this);
        mRl4 = (RelativeLayout)findViewById(R.id.rl4);
        mRl4.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mBtnRemovebind.setOnClickListener(this);
        mRlCheck = (RelativeLayout) findViewById(R.id.rl_check);
        rl_check_line = findViewById(R.id.rl_check_line);

        initData();
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

        mTvWhere.setText(application.getDevice().getName());

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

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl1:
                intent.setClass(this, DeviceDetailReviseActivity.class);
                startActivity(intent);
                break;

            case R.id.rl4:
                intent.setClass(this, HistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_removebind:
                unbindDeviceWithUser();
                break;
        }
    }

    public void unbindDeviceWithUser() {
        mLoadDialog.show();

        String url = basePath + "/user/" + application.getLoginUserID() + "/device/" + mDevice.get_id() + "/unbind";
        RequestQueue queue = application.getRequestQueue();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean success = response.optBoolean("success");
                if (success) {
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.optString("error"), Toast.LENGTH_SHORT).show();
                    mLoadDialog.dismiss();
                }
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        initData();
    }

    private void initData() {
        mDevice = application.getDevice();
        mTvMac.setText(mDevice.getMac().toUpperCase());
        mTvBianma.setText(mDevice.get_id());
        mTvWhere.setText(mDevice.getName());

        int type = mDevice.getType();
        if(type == 1) {
            mTvStyle.setText("主机");
            mRlCheck.setVisibility(View.VISIBLE);
            rl_check_line.setVisibility(View.VISIBLE);
        } else {
            mTvStyle.setText("从机");
            mRlCheck.setVisibility(View.GONE);
            rl_check_line.setVisibility(View.GONE);
        }

        requestDeviceData();
    }

    private void requestDeviceData() {
        String url = basePath + "/device/mac/" + mDevice.getMac() + "/get_test";
        RequestQueue queue = application.getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DeviceInfoActivty", response.toString());
                if(!response.isNull("mac")) {
                    int test = response.optInt("test");
                    String created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(response.optLong("created")));
                    String content = test == 1 ? "需要更换" : "无需更换";
                    mTvCheckStatus.setText(content + "(" + created + ")");
                } else {
                    mTvCheckStatus.setText("");
                }
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
}

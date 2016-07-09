package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rl1:
                intent.setClass(this, DeviceDetailReviseActivity.class);
                bundle.putString("deviceID", mDevice.get_id());
                bundle.putString("deviceName", mDevice.getName());
                bundle.putInt("devicePosition", mPosition);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.rl4:
                intent.setClass(this, HistoryActivity.class);
                bundle.putString("deviceMac", mDevice.getMac());
                bundle.putString("deviceName", mDevice.getName());
                bundle.putInt("devicePosition", mPosition);
                intent.putExtras(bundle);
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
                if(AConstants.IS_DEBUG_MODE){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
                mLoadDialog.dismiss();
            }
        });
        queue.add(jsonRequest);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        mPosition = bundle.getInt("devicePosition");
        mDevice = application.getDevices().get(mPosition);
        mTvMac.setText(mDevice.getMac().toUpperCase());
        mTvBianma.setText(mDevice.get_id());
        mTvWhere.setText(mDevice.getName());

        mTvCheckStatus.setText("需要更换");

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
    }
}

package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.AConstants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeviceAddLoadActivity extends ABaseActivity implements OnSmartLinkListener {

    private ImageView mIvLeft;
    private String mPwd;
    private String mSSID;
    private TextView mTvTitle;

    protected ISmartLinker mSnifferSmartLinker;
    private boolean mIsConncting = false;
    protected Handler mViewHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add_load);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("设备管理");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(getIntent() != null) {
            mSSID = getIntent().getStringExtra("ssid");
            mPwd = getIntent().getStringExtra("password");
        }

        initData();
    }

    private void initData() {
        int smartLinkVersion = getIntent().getIntExtra("EXTRA_SMARTLINK_VERSION", 7);
        if(smartLinkVersion == 7) {
            mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        }else {
            mSnifferSmartLinker = SnifferSmartLinker.getInstance();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDevice(mSSID, mPwd);
    }

    private void getDevice(String ssid, String password) {
        if(!mIsConncting){
            try {
                mSnifferSmartLinker.setOnSmartLinkListener(DeviceAddLoadActivity.this);
                mSnifferSmartLinker.start(getApplicationContext(), password, ssid);
                mIsConncting = true;
                mLoadDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSnifferSmartLinker.setOnSmartLinkListener(null);
    }

    @Override
    public void onLinked(final SmartLinkedModule module) {
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("DeviceAddLoadActivity", module.getMac());
                bindDeviceToUser(module.getMac());
            }
        });
    }

    @Override
    public void onCompleted() {
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "设备连接完成", Toast.LENGTH_SHORT).show();
                mLoadDialog.dismiss();
                mIsConncting = false;

                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
    }

    @Override
    public void onTimeOut() {
        mViewHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "设备无响应，请稍后再试", Toast.LENGTH_SHORT).show();
                mLoadDialog.dismiss();
                mIsConncting = false;
                finish();
            }
        });
    }

    private void bindDeviceToUser(String mac) {
        String url = basePath + "/user/add_device";
        RequestQueue queue = application.getRequestQueue();

        final Map<String, String> params = new HashMap<String, String>();
        params.put("mac", mac.toLowerCase());
        params.put("userID", application.getLoginUserID());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DeviceAddLoadActivity", response.toString());
                boolean success = response.optBoolean("success");
                if (success) {
                    int status = response.optInt("status");
                    if(status == 4) {
                        Toast.makeText(getApplicationContext(), "该设备已被其他人绑定过", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "绑定时发生异常，请稍候再试", Toast.LENGTH_SHORT).show();
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
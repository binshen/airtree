package com.moral.airtree;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.ABaseApplication;
import com.moral.airtree.model.Device;

public class DeviceAddLoadActivity extends ABaseActivity implements OnSmartLinkListener {

    public static final String EXTRA_SMARTLINK_VERSION = "EXTRA_SMARTLINK_VERSION";

    //private RelativeLayout mCommonTitle;
    //private Handler mHandler;
    private ImageView mIvLeft;
    private String mPwd;
    private String mSSID;
    private TextView mTvMsg;
    private TextView mTvPoint;
    private TextView mTvTitle;

    protected ISmartLinker mSnifferSmartLinker;
    private boolean mIsConncting = false;
    protected Handler mViewHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add_load);

        mTvPoint = (TextView)findViewById(R.id.tv_loading);
        mTvMsg = (TextView)findViewById(R.id.tv_msg);
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
        int smartLinkVersion = getIntent().getIntExtra(EXTRA_SMARTLINK_VERSION, 3);
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
                Device device = new Device();
                device.setMac(module.getMac());
                device.setIp(module.getModuleIP());
                device.setStatus(1);
                application.mDevices.add(device);
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
}

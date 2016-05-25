package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;

public class DeviceInfoActivty extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRemovebind;
    private Long mDeviceId;
    private Long mDevieId;
    private ImageView mIvLeft;
    private String mName;
    private RelativeLayout mRl1;
    private long mSubdomainid;
    private TextView mTvBianma;
    private TextView mTvMac;
    private TextView mTvParamthree;
    private TextView mTvTitle;
    private TextView mTvWhere;
    private Long mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info_activty);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mBtnRemovebind = (Button)findViewById(R.id.btn_removebind);
        mTvBianma = (TextView)findViewById(R.id.tv_bianma);
        mTvMac = (TextView)findViewById(R.id.tv_mac);
        mTvWhere = (TextView)findViewById(R.id.tv_where);
        //mTvParamthree = (TextView)findViewById(R.id.tv);
        mRl1 = (RelativeLayout)findViewById(R.id.rl1);
        mRl1.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mBtnRemovebind.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                Intent intent = new Intent();
                //intent.setClass(this, DeviceDetailRevise.class);
                Bundle tBundle = new Bundle();
                tBundle.putLong("subdomainid", mSubdomainid);
                tBundle.putLong("deviceid", mDeviceId.longValue());
                intent.putExtras(tBundle);
                startActivity(intent);
                break;

            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_removebind
                unbindDeviceWithUser(mDevieId.longValue());
                break;
        }
    }

    public void unbindDeviceWithUser(long deviceId) {
        mLoadDialog.show();
    }

    private void refreshDeviceList() {

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
        mDeviceId = Long.valueOf(bundle.getLong("deviceId"));
        /*
        mUserId = Long.valueOf(PreferencesUtils.getLong(this, "userId"));
        ArrayList<ACUserDevice> device = DeviceCache.getInstance(this).getDevicesFromCache();
        if(device.iterator().hasNext()) {
            ACUserDevice acUserDevice = (ACUserDevice)device.iterator().next();
            if(mDeviceId.longValue() == acUserDevice.deviceId) {
                mName = acUserDevice.getName();
                mSubdomainid = acUserDevice.getSubDomainId();
                mDevieId = Long.valueOf(acUserDevice.getDeviceId());
                mTvParamthree.setText(String.valueOf(mSubdomainid));
                mTvWhere.setText(mName);
                mTvBianma.setText(String.valueOf(mDevieId));
                mTvMac.setText(acUserDevice.getPhysicalDeviceId().substring(0x4, acUserDevice.getPhysicalDeviceId().length()));
            }
        }
        */
    }
}

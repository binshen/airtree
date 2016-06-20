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
import com.moral.airtree.model.Device;

public class DeviceInfoActivty extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRemovebind;
    private String mDeviceID;
    private ImageView mIvLeft;
    private RelativeLayout mRl1;
    private TextView mTvBianma;
    private TextView mTvMac;
//    private TextView mTvParamthree;
    private TextView mTvTitle;
    private TextView mTvWhere;

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
        //mTvParamthree = (TextView)findViewById(R.id.tv);
        mRl1 = (RelativeLayout)findViewById(R.id.rl1);
        mRl1.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mBtnRemovebind.setOnClickListener(this);

        initData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                Intent intent = new Intent();
                intent.setClass(this, DeviceDetailReviseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("deviceID", mDeviceID);
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

        mDeviceID = bundle.getString("deviceID");
        mTvMac.setText(bundle.getString("deviceMac").toUpperCase());
        mTvBianma.setText(mDeviceID);
        mTvWhere.setText(bundle.getString("deviceName"));
    }
}

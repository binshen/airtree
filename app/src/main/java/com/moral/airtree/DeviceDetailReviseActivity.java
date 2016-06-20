package com.moral.airtree;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.Device;

import java.util.ArrayList;

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
        mTvTitle.setText("xxxx");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mEtDeviceDetail = (EditText)findViewById(R.id.et_devicedetail);
        mBtnOk = (Button)findViewById(R.id.btn_ok);
        if(getIntent() == null) {
            return;
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
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

                }
            });
        }
    }

    protected void onStart() {
        super.onStart();
    }

    public void changeDeviceName(long mDeviceId, String name) {
        mLoadDialog.show();

    }
}

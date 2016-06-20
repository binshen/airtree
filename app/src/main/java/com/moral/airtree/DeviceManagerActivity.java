package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.moral.airtree.adapter.DeviceAdapter;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.Device;

import java.util.List;

public class DeviceManagerActivity extends ABaseActivity {

    private DeviceAdapter mDevicesAdapter;
    private List<Device> mDevices;

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

        initData();
    }

    private void initData() {

        mDevices = application.getDevices();
        mDevicesAdapter = new DeviceAdapter(this, mDevices);
        mLv.setAdapter(mDevicesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDevicesAdapter.notifyDataSetChanged();
    }
}

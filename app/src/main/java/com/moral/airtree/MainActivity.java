package com.moral.airtree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;

public class MainActivity extends ABaseActivity implements View.OnClickListener {

    TextView mTvTitle;
    private TextView mTvDeviceManager;
    private TextView mTvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivPersonal = (ImageView)findViewById(R.id.iv_personal);
        ivPersonal.setOnClickListener(this);
        ImageView ivMall = (ImageView)findViewById(R.id.iv_mall);
        ivMall.setOnClickListener(this);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvDeviceManager = (TextView)findViewById(R.id.tv_devicemanager);
        mTvDeviceManager.setOnClickListener(this);
        mTvHistory = (TextView)findViewById(R.id.tv_history);
        mTvHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.iv_personal:
                startActivity(new Intent(this, PersonalMainActivity.class));
                break;

            case R.id.iv_mall:
                startActivity(new Intent(this, ShopActivity.class));
                break;

            case R.id.tv_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;

            case R.id.tv_devicemanager:
                startActivity(new Intent(this, DeviceManagerActivity.class));
                break;
        }
    }
}

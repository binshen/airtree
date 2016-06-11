package com.moral.airtree;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hiflying.smartlink.ISmartLinker;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v3.SnifferSmartLinker;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.moral.airtree.common.ABaseActivity;

public class DeviceAddActivity extends ABaseActivity {

    private Button mBtnOk;
    private EditText mEtWifiPwd;
    private ImageView mIvLeft;
    private TextView mTvTitle;
    private TextView mTvWifiName;

    private BroadcastReceiver mWifiChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("添加设备");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mTvWifiName = (TextView)findViewById(R.id.tv_wifiname);
        mEtWifiPwd = (EditText)findViewById(R.id.et_wifipwd);
        mEtWifiPwd.setText("asdfasdf");
        mBtnOk = (Button)findViewById(R.id.btn_ok);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wifiName = mTvWifiName.getText().toString().trim();
                String wifipwd = mEtWifiPwd.getText().toString().trim();

                Intent intent = new Intent(getApplicationContext(), DeviceAddLoadActivity.class);
                intent.putExtra("ssid", wifiName);
                intent.putExtra("password", wifipwd);
                startActivityForResult(intent, 1);
            }
        });

        initData();
    }

    private void initData() {
        mTvWifiName.setText(getSSid());

        mWifiChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                //NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    mTvWifiName.setText(getSSid());
                    mEtWifiPwd.requestFocus();
                    mBtnOk.setEnabled(true);
                }else {
                    mTvWifiName.setText("");
                    mTvWifiName.requestFocus();
                    mBtnOk.setEnabled(false);
                    if (mLoadDialog.isShowing()) {
                        mLoadDialog.dismiss();
                    }
                }
            }
        };
        registerReceiver(mWifiChangedReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private String getSSid(){

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        if(wm != null){
            WifiInfo wi = wm.getConnectionInfo();
            if(wi != null){
                String ssid = wi.getSSID();
                if(ssid.length()>2 && ssid.startsWith("\"") && ssid.endsWith("\"")){
                    return ssid.substring(1,ssid.length()-1);
                }else{
                    return ssid;
                }
            }
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mWifiChangedReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                finish();
            }
        }
    }
}

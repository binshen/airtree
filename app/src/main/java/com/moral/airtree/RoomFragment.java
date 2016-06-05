package com.moral.airtree;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moral.airtree.model.Device;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;
import com.moral.airtree.utils.FlagUtils;
import com.moral.airtree.widget.LoadDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener {


    Context mContext;
    private long mExitTime;
    private ImageView mIvElectric;
    private LinearLayout mLayMainCenter;
    private LoadDialog mLoadDialog;
    private Monitor mMonitor;

    private RelativeLayout mRlltFormaldehyde;
    private RelativeLayout mRlltTemperature;
    private RelativeLayout mRllthumidity;
    private RelativeLayout mRlltpm25;
    private TextView mTvAirQuality;
    private TextView mTvFormaldehydeValue;
    private TextView mTvHumidityValue;
    private TextView mTvMain;
    private TextView mTvMainLabel;
    private TextView mTvPM25Value;
    private TextView mTvSuggest;
    private TextView mTvTemperature2;
    private View mView;
    private boolean mHasConnect = false;

    private Device mDevice;

    private Handler timeHandler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            timeHandler.postDelayed(this, 0xea60);
        }
    };

    public RoomFragment() {
    }

    public RoomFragment(Device device) {
        mDevice = device;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadDialog = new LoadDialog(getActivity());
        if((savedInstanceState != null) && (savedInstanceState.containsKey("mDevice"))) {
            mDevice = (Device)savedInstanceState.get("mDevice");
            mMonitor = (Monitor)savedInstanceState.get("monitor");
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("mDevice", mDevice);
        outState.putSerializable("monitor", mMonitor);
        super.onSaveInstanceState(outState);
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_room, container, false);
        requestMonitorData();
        if(FlagUtils.flag) {
            timeHandler.removeCallbacks(runnable);
            timeHandler.postDelayed(runnable, 0xea60);
            FlagUtils.flag = false;
        }
        return mView;
    }

    @Override
    public void onClick(View v) {
        if(mMonitor == null) {
            return;
        }
        if(mDevice.getStatus() == 0) {
            Toast.makeText(getActivity(), "\u8bf7\u542f\u52a8\u7a7a\u6c14\u6811\u8bbe\u5907", Toast.LENGTH_SHORT).show();
            return;
        }
        switch(v.getId()) {
            case 1:
                if(mMonitor.getPm() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.PM);
                    startActivity(intent);
                }
                break;
            case 7:
                if(mMonitor.getWindSpeed() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.TEMPERATURE);
                    startActivity(intent);
                }
                break;
            case 10:
                if(mMonitor.getHumidity() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.HUMIDITY);
                    startActivity(intent);
                }
                break;
            case 12:
                if(mMonitor.getFormaldehyde() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.FORMALDEHYDE);
                    startActivity(intent);
                }
                break;
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
    }

    public void onDestroy() {
        super.onDestroy();
        timeHandler.removeCallbacks(runnable);
    }

    private void initView() {

    }

    private void initData() {

    }

    private void clearData() {

    }

    private void initAirQuality() {

    }

    private void requestMonitorData() {

    }
}

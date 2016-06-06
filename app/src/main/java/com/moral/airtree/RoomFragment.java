package com.moral.airtree;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.moral.airtree.model.MonitorFormaldehyde;
import com.moral.airtree.model.MonitorHumidity;
import com.moral.airtree.model.MonitorPm;
import com.moral.airtree.model.MonitorTemperature;
import com.moral.airtree.model.MonitorWindSpeed;
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


        MonitorPm mPm = new MonitorPm("2", 2l, "2");
        MonitorWindSpeed mWs = new MonitorWindSpeed("3", 3l, "3");
        MonitorHumidity mHu = new MonitorHumidity("4", "4", "4");
        MonitorTemperature mTe = new MonitorTemperature("5", "5", "5");
        MonitorFormaldehyde mFo = new MonitorFormaldehyde(6l, "6", "6");
        mMonitor = new Monitor("1", "1", "1", 1l, "1", 1l, "1", "1", mPm, mWs, mHu, mTe, mFo);
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
            case R.id.rl_pm25:
                if(mMonitor.getPm() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.PM);
                    startActivity(intent);
                }
                break;
            case R.id.rl_temperature:
                if(mMonitor.getWindSpeed() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.TEMPERATURE);
                    startActivity(intent);
                }
                break;
            case R.id.rl_humidity:
                if(mMonitor.getHumidity() != null) {
                    Intent intent = new Intent(getActivity(), MonitorActivity.class);
                    intent.putExtra("monitor", mMonitor);
                    intent.putExtra("monitorType", MonitorEnum.HUMIDITY);
                    startActivity(intent);
                }
                break;
            case R.id.rl_formaldehyde:
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
        mRlltpm25 = (RelativeLayout)mView.findViewById(R.id.rl_pm25);
        mRlltTemperature = (RelativeLayout)mView.findViewById(R.id.rl_temperature);
        mRllthumidity = (RelativeLayout)mView.findViewById(R.id.rl_humidity);
        mRlltFormaldehyde = (RelativeLayout)mView.findViewById(R.id.rl_formaldehyde);
        mRlltpm25.setOnClickListener(this);
        mRlltTemperature.setOnClickListener(this);
        mRllthumidity.setOnClickListener(this);
        mRlltFormaldehyde.setOnClickListener(this);
        mTvAirQuality = (TextView)mView.findViewById(R.id.tv_air_quality);
        mTvMain = (TextView)mView.findViewById(R.id.tv_main);
        mTvMainLabel = (TextView)mView.findViewById(R.id.tv_main_label);
        mTvSuggest = (TextView)mView.findViewById(R.id.tv_suggest);
        mTvPM25Value = (TextView)mView.findViewById(R.id.tv_pm25_value);
        mTvHumidityValue = (TextView)mView.findViewById(R.id.tv_humidity_value);
        mTvFormaldehydeValue = (TextView)mView.findViewById(R.id.tv_formaldehyde_value);
        mTvTemperature2 = (TextView)mView.findViewById(R.id.tv_temperature2_value);
        mIvElectric = (ImageView)mView.findViewById(R.id.iv_electric_value);
        mLayMainCenter = (LinearLayout)mView.findViewById(R.id.lay_main_center);
        mLayMainCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if((System.currentTimeMillis() - mExitTime) > 0x5dc) {
                    mExitTime = System.currentTimeMillis();
                    return;
                }
            }
        });
    }

    private void initData() {
        int deviceStatus = mDevice.getStatus();
        int code = deviceStatus;
        String status = "";
        if(code == 0) {
            status = mMonitor.getCreate_date();
        } else if(code == 0x1) {
        } else if(code == 0x2) {
        } else if(code == 0x3) {
        }
        mTvSuggest.setText(status);
        if(mMonitor == null) {
            return;
        }
        if((mMonitor.getPm() != null) && (mMonitor.getPm().getPm_data() != null)) {
            mTvPM25Value.setText("ug/m\u00b3");
            mTvMain.setText(String.valueOf(mMonitor.getPm().getPm03p01()));
            mTvMainLabel.setText("0.3um\u9897\u7c92\u7269\u4e2a\u6570");
        } else {
            mTvPM25Value.setText(R.string.airquality_unknow);
            mTvMain.setText(R.string.airquality_unknow);
            mTvMainLabel.setVisibility(View.GONE);
        }
        if(mMonitor.getHumidity() != null) {
            if(!TextUtils.isEmpty(mMonitor.getHumidity().getHumidity_data())) {
                try {
                    int data = Integer.parseInt(mMonitor.getHumidity().getHumidity_data());
                    if(data == 0x6c) {
                        mTvHumidityValue.setText("?????");
                    } else {
                        mTvHumidityValue.setText("%");
                    }
                }  catch(Exception ex) {
                    Log.e("RoomFragment", ex.toString());
                    mTvHumidityValue.setText(R.string.airquality_unknow);
                }
            }
        } else {
            mTvHumidityValue.setText(R.string.airquality_unknow);
        }
        if(mMonitor.getTemperature() != null) {
            if(mMonitor.getTemperature().getTemperature_data() != null) {
                try {
                    String data = mMonitor.getTemperature().getTemperature_data();
                    if(Integer.valueOf(data).intValue() == -0xd6) {
                        mTvTemperature2.setText("?????");
                    } else {
                        mTvTemperature2.setText("\u2103");
                    }
                } catch(Exception ex) {
                    Log.e("RoomFragment", ex.toString());
                    mTvTemperature2.setText(R.string.airquality_unknow);
                }
            }
        }
        if(mMonitor.getFormaldehyde() != null) {
            if(mMonitor.getFormaldehyde().getFormaldehyde_data() != null) {
                mTvFormaldehydeValue.setText((String.valueOf(String.valueOf(mMonitor.getFormaldehyde()))));
                //localStringBuilder1 = (String.valueOf(String.valueOf(mMonitor.getFormaldehyde()))).append("mg/m\u00b3").toString();
            }
        } else {
            mTvFormaldehydeValue.setText(R.string.airquality_unknow);
        }
        if(!TextUtils.isEmpty(mMonitor.getElectricQuantity())) {
            int data = Integer.parseInt(mMonitor.getElectricQuantity());
            if(data >= 0x5a) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_5);
            } else if((data < 0x5a) && (data >= 0x50)) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_4);
            } else if((data >= 0x3c) && (data < 0x50)) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_3);
            } else if((data < 0x28) || (data >= 0x3c)) {
                if((data >= 0x14) && (data < 0x28)) {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_1);
                } else {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_2);
                }
            }
        }
        int chipLife = 0x0;
        try {
            chipLife = Integer.parseInt(mMonitor.getChipLife());
        } catch(Exception ex) {
            chipLife = 0x0;
        }
        if(getActivity() != null) {
            //PreferencesUtils.putInt(getActivity(), "chiplife", chipLife);
        }
        initAirQuality();
    }

    private void clearData() {
        mTvPM25Value.setText(R.string.airquality_unknow);
        mTvHumidityValue.setText(R.string.airquality_unknow);
        mTvMain.setText(R.string.airquality_unknow);
        mTvMainLabel.setVisibility(View.GONE);
        mTvFormaldehydeValue.setText(R.string.airquality_unknow);
        mTvAirQuality.setText(R.string.airquality_unknow);
    }

    private void initAirQuality() {
        if(mMonitor.getPm().getPm_data() != null) {
            long pmData = mMonitor.getPm().getPm_data().longValue();
            if((pmData > 0x0) && (pmData <= 0x23)) {
                mTvAirQuality.setText(R.string.airquality_good);
                return;
            }
            if((pmData > 0x23) && (pmData <= 0x4b)) {
                mTvAirQuality.setText(R.string.airquality_very);
                return;
            }
            if((pmData > 0x4b) && (pmData <= 0x96)) {
                mTvAirQuality.setText(R.string.airquality_general);
                return;
            }
            if(pmData > 0x96) {
                mTvAirQuality.setText(R.string.airquality_poor);
            }
        }
    }

    private void requestMonitorData() {

    }
}

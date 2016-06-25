package com.moral.airtree;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moral.airtree.model.Device;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener {

    private ImageView mIvElectric;
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
    private Device mDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mDevice = (Device) getArguments().getSerializable("device");
        this.mMonitor = (Monitor) getArguments().getSerializable("monitor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_room, container, false);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(mMonitor == null) {
            return;
        }
        if(mDevice.getStatus() != 1) {
            Toast.makeText(getActivity(), "请启动空气树设备", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), MonitorActivity.class);
        intent.putExtra("monitor", mMonitor);
        switch(v.getId()) {
            case R.id.rl_pm25:
                if(mMonitor.getPm_data() != null) {
                    intent.putExtra("monitorType", MonitorEnum.PM);
                    startActivity(intent);
                }
                break;
            case R.id.rl_temperature:
                if(mMonitor.getWindSpeed_data() != null) {
                    intent.putExtra("monitorType", MonitorEnum.TEMPERATURE);
                    startActivity(intent);
                }
                break;
            case R.id.rl_humidity:
                if(mMonitor.getHumidity_data() != null) {
                    intent.putExtra("monitorType", MonitorEnum.HUMIDITY);
                    startActivity(intent);
                }
                break;
            case R.id.rl_formaldehyde:
                if(mMonitor.getFormaldehyde_data() != null) {
                    intent.putExtra("monitorType", MonitorEnum.FORMALDEHYDE);
                    startActivity(intent);
                }
                break;
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
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
    }

    private void initData() {
        if(mMonitor == null) {
            return;
        }
        int status = mDevice.getStatus();
        if(mMonitor.getPm_data() != null) {
            if(status == 1) {
                mTvSuggest.setText("");
                mTvMainLabel.setText("云端在线");
            } else {
                mTvSuggest.setText("上次时间检测时间：\n" + mMonitor.getCreated());
                mTvMainLabel.setText("0.3um颗粒物个数");
            }
            mTvPM25Value.setText(mMonitor.getPm_data() + "ug/m³");
            mTvMain.setText(String.valueOf(mMonitor.getPm03p01()));
        } else {
            mTvPM25Value.setText(R.string.airquality_unknow);
            mTvMain.setText(R.string.airquality_unknow);
            mTvMainLabel.setVisibility(View.GONE);
        }
        if(mMonitor.getHumidity_data() != null) {
            mTvHumidityValue.setText(mMonitor.getHumidity_data() + "%");
        } else {
            mTvHumidityValue.setText(R.string.airquality_unknow);
        }
        if(mMonitor.getTemperature_data() != null) {
            mTvTemperature2.setText(mMonitor.getTemperature_data()  + "℃");
        }
        if(mMonitor.getFormaldehyde_data() != null) {
            mTvFormaldehydeValue.setText(String.valueOf(mMonitor.getFormaldehyde_data()) + "mg/m³");
        } else {
            mTvFormaldehydeValue.setText(R.string.airquality_unknow);
        }
        if(mDevice.getType() == 1) {
            int data = mMonitor.getElectricQuantity();
            if(data == 5) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_5);
            } else if(data == 4) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_4);
            } else if(data == 3) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_3);
            } else if(data == 2) {
                mIvElectric.setImageResource(R.mipmap.ic_ele_2);
            } else {
                mIvElectric.setImageResource(R.mipmap.ic_ele_1);
            }
        } else {
            mIvElectric.setImageResource(R.mipmap.ic_ele_5);
        }
//滤芯寿命
//        int chipLife = 0x0;
//        try {
//            chipLife = Integer.parseInt(mMonitor.getChipLife());
//        } catch(Exception ex) {
//            chipLife = 0x0;
//        }
//        if(getActivity() != null) {
//            //PreferencesUtils.putInt(getActivity(), "chiplife", chipLife);
//        }
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
        if(mMonitor.getPm_data() != null) {
            long pmData = mMonitor.getPm_data().longValue();
            if(pmData <= 35) {
                mTvAirQuality.setText(R.string.airquality_good);
                return;
            } else if(pmData <= 75) {
                mTvAirQuality.setText(R.string.airquality_very);
                return;
            } else if(pmData <= 150) {
                mTvAirQuality.setText(R.string.airquality_general);
                return;
            } else {
                mTvAirQuality.setText(R.string.airquality_poor);
            }
        }
    }
}

package com.moral.airtree;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener {

    private ImageView mIvElectric;
    private Device mDevice;
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

    public static RoomFragment newInstance(Device device, Monitor monitor) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putSerializable("device", device);
        args.putSerializable("monitor", monitor);
        fragment.setArguments(args);
        return fragment;
    }

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
        //if(status == 1 && new Date().getTime() - mDevice.getLast_updated() <= 60000) {
        if(status == 1) {
            mTvSuggest.setText("云端在线");
        } else {
            String created = mMonitor.getCreated();
            if(created != null) {
                mTvSuggest.setText("上次检测时间：\n" + created);
            } else {
                mTvSuggest.setText("无法获取最新数据");
            }
        }
        int priority1 = mMonitor.getPriority1();
        if(priority1 == 3) {//甲醛
            if(mMonitor.getFormaldehyde_data() != null) {
                mTvMainLabel.setText("当前甲醛浓度（mg/m³）");
                mTvMain.setText(String.valueOf(mMonitor.getFormaldehyde_data()));
            } else {
                mTvMainLabel.setVisibility(View.GONE);
                mTvMain.setText(R.string.airquality_unknow);
            }
        } else if(priority1 == 4) {//温度
            if(mMonitor.getTemperature_data() != null) {
                mTvMainLabel.setText("当前温度（℃）");
                mTvMain.setText(String.valueOf(mMonitor.getTemperature_data()));
            } else {
                mTvMainLabel.setVisibility(View.GONE);
                mTvMain.setText(R.string.airquality_unknow);
            }
        } else {
            if(mMonitor.getPm_data() != null) {
                mTvMainLabel.setText("0.3um颗粒物个数");
                mTvMain.setText(String.valueOf(mMonitor.getPm03p01()));
            } else {
                mTvMainLabel.setVisibility(View.GONE);
                mTvMain.setText(R.string.airquality_unknow);
            }
        }
        initAirQuality(priority1);

        if(mMonitor.getPm_data() != null) {
            mTvPM25Value.setText(mMonitor.getPm_data() + "ug/m³");
        } else {
            mTvPM25Value.setText(R.string.airquality_unknow);
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
    }

    private void clearData() {
        mTvPM25Value.setText(R.string.airquality_unknow);
        mTvHumidityValue.setText(R.string.airquality_unknow);
        mTvMain.setText(R.string.airquality_unknow);
        mTvMainLabel.setVisibility(View.GONE);
        mTvFormaldehydeValue.setText(R.string.airquality_unknow);
        mTvAirQuality.setText(R.string.airquality_unknow);
    }

    private void initAirQuality(int priority) {
        if(priority > 0) {
            int feiLevel = mMonitor.getFeiLevel();
            if(feiLevel == 1) {
                mTvAirQuality.setText(R.string.airquality_good);
            } else if(feiLevel == 2) {
                mTvAirQuality.setText(R.string.airquality_very);
            } else if(feiLevel == 3) {
                mTvAirQuality.setText(R.string.airquality_general);
            } else if(feiLevel == 4) {
                mTvAirQuality.setText(R.string.airquality_poor);
            } else {
                mTvAirQuality.setText(R.string.airquality_unknow);
            }
        } else {
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
            } else {
                mTvAirQuality.setText(R.string.airquality_unknow);
            }
        }
    }
}

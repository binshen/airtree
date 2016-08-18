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
import com.moral.airtree.utils.StringUtils;

import java.util.Date;

import pl.droidsonroids.gif.GifImageView;


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
    private GifImageView mTvMainImage;
    private ImageView mTvLightImage;

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
            Toast.makeText(getActivity(), "请启动FEI环境数设备", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), MonitorActivity.class);
        intent.putExtra("monitor", mMonitor);
        switch(v.getId()) {
            case R.id.rl_pm25:
                intent.putExtra("monitorType", MonitorEnum.PM);
                startActivity(intent);
                break;
            case R.id.rl_temperature:
                intent.putExtra("monitorType", MonitorEnum.TEMPERATURE);
                startActivity(intent);
                break;
            case R.id.rl_humidity:
                intent.putExtra("monitorType", MonitorEnum.HUMIDITY);
                startActivity(intent);
                break;
            case R.id.rl_formaldehyde:
                intent.putExtra("monitorType", MonitorEnum.FORMALDEHYDE);
                startActivity(intent);
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
        mTvMainImage = (GifImageView)mView.findViewById(R.id.iv_main_image);
        mTvLightImage = (ImageView)mView.findViewById(R.id.iv_light_image);
    }

    private void initData() {
        if(mMonitor == null) {
            return;
        }
        int status = mDevice.getStatus();
        //if(status == 1 && new Date().getTime() - mDevice.getLast_updated() <= 60000) {
        if(status == 1) {
            mTvSuggest.setText("云端在线");

            mIvElectric.setVisibility(View.VISIBLE);
            if(mDevice.getType() == 1) {
                int data = mMonitor.getElectricQuantity();
                if(data == 4) {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_n4);
                } else if(data == 3) {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_n3);
                } else if(data == 2) {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_n2);
                } else if(data == 1) {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_n1);
                } else {
                    mIvElectric.setImageResource(R.mipmap.ic_ele_n0);
                }
            } else {
                mIvElectric.setImageResource(R.mipmap.ic_ele_n4);
            }
        } else {
            String created = mMonitor.getCreated();
            if(created != null) {
                mTvSuggest.setText("上次检测时间：\n" + created);
            } else {
                mTvSuggest.setText("");
            }

            mIvElectric.setVisibility(View.INVISIBLE);
        }
        int priority1 = mMonitor.getPriority1();
        if(priority1 == 3) {//甲醛
            if(mMonitor.getFormaldehyde_data() > 0) {
                mTvMainLabel.setText("当前甲醛浓度（mg/m³）");
                mTvMain.setText(String.valueOf(mMonitor.getFormaldehyde_data()));
            } else {
                mTvMainLabel.setVisibility(View.GONE);
                mTvMain.setText(R.string.airquality_unknow);
            }
        } else if(priority1 == 4) {//温度
            if(mMonitor.getTemperature_data() > -274) {
                mTvMainLabel.setText("当前温度（℃）");
                mTvMain.setText(String.valueOf(mMonitor.getTemperature_data()));
            } else {
                mTvMainLabel.setVisibility(View.GONE);
                mTvMain.setText(R.string.airquality_unknow);
            }
        } else {
            mTvMainLabel.setText("0.3um颗粒物个数");
            if(mMonitor.getPm_data() > 0) {
                mTvMain.setText(String.valueOf(mMonitor.getPm03p01()));
            } else {
                mTvMain.setText("0");
            }
        }

        initLight();
        initAirQuality(priority1);

        mTvPM25Value.setText(mMonitor.getPm_data() + "ug/m³");
        mTvHumidityValue.setText(mMonitor.getHumidity_data() + "%");
        mTvTemperature2.setText(mMonitor.getTemperature_data()  + "℃");
        mTvFormaldehydeValue.setText(StringUtils.getFormaldehyde(mMonitor.getFormaldehyde_data()) + "mg/m³");
    }

    private void initLight() {
        long light = mMonitor.getLight();
        if(light <= 0) {
            return;
        }
        if(light > 500) {
            mTvLightImage.setImageResource(R.mipmap.light_01);
        } else if(light < 240) {
            mTvLightImage.setImageResource(R.mipmap.light_03);
        } else {
            mTvLightImage.setImageResource(R.mipmap.light_02);
        }
    }

    private void initAirQuality(int priority) {
        if(priority > 0) {
            int feiLevel = mMonitor.getFeiLevel();
            if(feiLevel == 1) {
                mTvAirQuality.setText(R.string.airquality_good);
                mTvMainImage.setImageResource(R.drawable.rank_1);
            } else if(feiLevel == 2) {
                mTvAirQuality.setText(R.string.airquality_very);
                mTvMainImage.setImageResource(R.drawable.rank_2);
            } else if(feiLevel == 3) {
                mTvAirQuality.setText(R.string.airquality_general);
                mTvMainImage.setImageResource(R.drawable.rank_3);
            } else if(feiLevel == 4) {
                mTvAirQuality.setText(R.string.airquality_poor);
                mTvMainImage.setImageResource(R.drawable.rank_4);
            } else {
                mTvAirQuality.setText(R.string.airquality_unknow);
            }
        } else {
            if(mMonitor.getPm_data() > 0) {
                long pmData = mMonitor.getPm_data();
                if(pmData <= 35) {
                    mTvAirQuality.setText(R.string.airquality_good);
                    mTvMainImage.setImageResource(R.drawable.rank_1);
                } else if(pmData <= 75) {
                    mTvAirQuality.setText(R.string.airquality_very);
                    mTvMainImage.setImageResource(R.drawable.rank_2);
                } else if(pmData <= 150) {
                    mTvAirQuality.setText(R.string.airquality_general);
                    mTvMainImage.setImageResource(R.drawable.rank_3);
                } else {
                    mTvAirQuality.setText(R.string.airquality_poor);
                    mTvMainImage.setImageResource(R.drawable.rank_4);
                }
            } else {
                mTvAirQuality.setText(R.string.airquality_unknow);
            }
        }
    }
}

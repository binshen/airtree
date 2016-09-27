package com.moral.airtree;


import com.moral.airtree.common.ABaseFragment;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;
import com.moral.airtree.widget.MyCircleView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import com.moral.airtree.utils.DisplayUtils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class MonitorFragment extends ABaseFragment {
    private Monitor mMonitor;
    private MonitorEnum mMonitorType;
    private MyCircleView mMyCircleView;
    private RelativeLayout mRlatMonitor;
    private TextView mTvBottomDanwei;
    private TextView mTvMonitortype;
    private TextView mTvRightDanwei;
    private TextView mTvTime;
    private TextView mTvValue;
    private View mView;

    public static MonitorFragment newInstance(MonitorEnum monitorType, Monitor monitor) {
        MonitorFragment fragment = new MonitorFragment();
        Bundle args = new Bundle();
        args.putSerializable("monitor", monitor);
        args.putSerializable("monitorType", monitorType);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mMonitor = (Monitor) getArguments().getSerializable("monitor");
        this.mMonitorType = (MonitorEnum) getArguments().getSerializable("monitorType");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_monitor, null);
        initView();
        return mView;
    }

    private void initView() {
        mTvTime = (TextView)mView.findViewById(R.id.tv_time);
        mTvValue = (TextView)mView.findViewById(R.id.tv_value);
        mTvMonitortype = (TextView)mView.findViewById(R.id.tv_monitortype);
        mTvBottomDanwei = (TextView)mView.findViewById(R.id.tv_bottomdanwei);
        mTvRightDanwei = (TextView)mView.findViewById(R.id.tv_rightdanwei);
        mRlatMonitor = (RelativeLayout)mView.findViewById(R.id.rlat_monitor);
        mMyCircleView = (MyCircleView)mView.findViewById(R.id.my_circle);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewData(mMonitorType);
    }

    public void initViewData(MonitorEnum monitorType) {

        mMyCircleView.setVisibility(View.GONE);
        mTvRightDanwei.setVisibility(View.GONE);
        mTvBottomDanwei.setVisibility(View.VISIBLE);

        mTvTime.setText(mMonitor.getCreated());
        //float size = (float) DisplayUtils.sp2px(getActivity(), 30.0f);
        //mTvValue.setTextSize(size);
        mTvValue.setTextColor(getResources().getColor(R.color.color_wendu));
        //mTvBottomDanwei.setTextColor(getResources().getColor(R.color.color_wendu_type));
        //mTvMonitortype.setTextColor(getResources().getColor(R.color.color_pm_type));
        if(monitorType == MonitorEnum.PM) {
            initPmPage();
        } else if(monitorType == MonitorEnum.TEMPERATURE) {
            initTemperaturePage();
        } else if(monitorType == MonitorEnum.HUMIDITY) {
            initHumidityPage();
        } else if(monitorType == MonitorEnum.FORMALDEHYDE) {
            initFormaldehydePage();
        }
        mLoadDialog.dismiss();
    }

    private void initFormaldehydePage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_jiaquan);
        mTvMonitortype.setText(R.string.monitor_formaldehyde);
        mTvBottomDanwei.setText("mg/m³");
        if(mMonitor.getFormaldehyde_data() > 0) {
            mTvValue.setText(String.valueOf(mMonitor.getFormaldehyde_data()));
        } else {
            mTvValue.setText("0");
        }
    }

    private void initTemperaturePage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_wendu);
        mTvMonitortype.setText(R.string.monitor_temperature);
        mTvBottomDanwei.setText("℃");
        if(mMonitor.getTemperature_data() > -274) {
            mTvValue.setText(String.valueOf(mMonitor.getTemperature_data()));
        } else {
            mTvValue.setText("0");
        }
    }

    private void initHumidityPage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_shidu);
        mTvMonitortype.setText(R.string.monitor_humidity);
        mTvBottomDanwei.setText("%");
        if(mMonitor.getHumidity_data() > 0) {
            mTvValue.setText(String.valueOf(mMonitor.getHumidity_data()));
        } else {
            mTvValue.setText("0");
        }
    }

    private void initPmPage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_pm);
        mTvMonitortype.setText(R.string.monitor_pm);
        mTvBottomDanwei.setText("ug/m³");
        if(mMonitor.getPm_data() > 0) {
            mTvValue.setText(String.valueOf(mMonitor.getPm_data()));
        } else {
            mTvValue.setText("0");
        }
    }
}
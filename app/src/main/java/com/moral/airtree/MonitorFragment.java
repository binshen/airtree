package com.moral.airtree;


import com.moral.airtree.common.ABaseFragment;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;
import com.moral.airtree.widget.MyCircleView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import com.moral.airtree.utils.DisplayUtils;

import android.text.TextUtils;
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

    public MonitorFragment() {
    }

    public MonitorFragment(MonitorEnum monitorType, Monitor monitor) {
        mMonitorType = monitorType;
        mMonitor = monitor;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(monitorType == MonitorEnum.PM) {
            initPmPage();
            return;
        }
        if(monitorType == MonitorEnum.TEMPERATURE) {
            initTemperaturePage();
            return;
        }
        if(monitorType == MonitorEnum.HUMIDITY) {
            initHumidityPage();
            return;
        }
        if(monitorType == MonitorEnum.FORMALDEHYDE) {
            initFormaldehydePage();
        }
    }

    private void initFormaldehydePage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_jiaquan);
        mTvMonitortype.setVisibility(View.GONE);
        mMyCircleView.setVisibility(View.GONE);
        mTvBottomDanwei.setText("mg/m³");
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.color_jiaquan_danwei));
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        if(mMonitor.getFormaldehyde() == null) {
            return;
        }
        mTvValue.setTextColor(getResources().getColor(R.color.color_jiaquan_value));
        float size = (float) DisplayUtils.sp2px(getActivity(), 30.0f);
        mTvValue.setTextSize(size);
        if((mMonitor.getFormaldehyde() != null) && (mMonitor.getFormaldehyde().getFormaldehyde_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getFormaldehyde().getFormaldehyde_data()));
        } else {
            mTvValue.setText(R.string.get_none);
        }
        mTvTime.setText(mMonitor.getFormaldehyde().getCreate_date());
    }

    private void initTemperaturePage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_wendu);
        mTvMonitortype.setVisibility(View.GONE);
        mMyCircleView.setVisibility(View.GONE);
        mTvRightDanwei.setVisibility(View.VISIBLE);
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        mTvBottomDanwei.setText("当前温度");
        mTvRightDanwei.setText("℃");
        mTvRightDanwei.setTextColor(getResources().getColor(R.color.color_wendu));
        mTvValue.setTextColor(getResources().getColor(R.color.color_wendu));
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.color_wendu_type));
        if(mMonitor.getTemperature() == null) {
            return;
        }
        if((mMonitor.getTemperature() != null) && (mMonitor.getTemperature().getTemperature_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getTemperature().getTemperature_data()));
        } else {
            mTvValue.setText(R.string.get_none);
        }
        mTvTime.setText(mMonitor.getWindSpeed().getCreate_date());
        mLoadDialog.dismiss();
    }

    private void initHumidityPage() {
        mTvMonitortype.setText(R.string.monitor_humidity);
        mTvRightDanwei.setVisibility(View.VISIBLE);
        mMyCircleView.setVisibility(View.VISIBLE);
        mTvBottomDanwei.setVisibility(View.INVISIBLE);
        mTvRightDanwei.setText("%");
        if(mMonitor.getHumidity() == null) {
            return;
        }
        if((mMonitor.getHumidity() != null) && (!TextUtils.isEmpty(mMonitor.getHumidity().getHumidity_data()))) {
            try {
                long data = Long.parseLong(mMonitor.getHumidity().getHumidity_data());
                mTvValue.setText(String.valueOf(data));
                mTvRightDanwei.setVisibility(View.VISIBLE);
            } catch(Exception ex) {
                mTvRightDanwei.setVisibility(View.GONE);
                mTvValue.setText(R.string.get_none);
            }
        } else {
            mTvRightDanwei.setVisibility(View.GONE);
            mTvValue.setText(R.string.get_none);
        }
        mTvTime.setText(mMonitor.getHumidity().getCreate_date());
        mLoadDialog.dismiss();
    }

    private void initPmPage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.bg_pm);
        mTvMonitortype.setText(R.string.monitor_pm);
        mMyCircleView.setVisibility(View.GONE);
        mTvMonitortype.setTextColor(getResources().getColor(R.color.color_pm_type));
        mTvBottomDanwei.setText("ug/m³");
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.color_pm_danwei));
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        if(mMonitor.getPm() == null) {
            return;
        }
        mTvValue.setTextColor(getResources().getColor(R.color.color_pm_value));
        if((mMonitor.getPm() != null) && (mMonitor.getPm().getPm_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getPm().getPm_data()));
        } else {
            mTvValue.setText(R.string.get_none);
        }
        mTvTime.setText(mMonitor.getPm().getCreate_date());
    }
}
package com.moral.airtree;


import com.moral.airtree.common.ABaseFragment;
import com.moral.airtree.model.Monitor;
import com.moral.airtree.model.MonitorEnum;
import com.moral.airtree.widget.MyCircleView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.content.res.Resources;
import com.moral.airtree.model.MonitorFormaldehyde;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import com.moral.airtree.utils.DisplayUtil;
import com.moral.airtree.model.MonitorHumidity;
import android.text.TextUtils;
import com.moral.airtree.widget.LoadDialog;
import com.moral.airtree.model.MonitorPm;
import com.moral.airtree.model.MonitorTemperature;
import com.moral.airtree.model.MonitorWindSpeed;
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
        mRlatMonitor.setBackgroundResource(R.color.black);
        mTvMonitortype.setVisibility(View.GONE);
        mMyCircleView.setVisibility(View.GONE);
        mTvBottomDanwei.setText("mg/m\u00b3");
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.bg_title));
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        if(mMonitor.getFormaldehyde() == null) {
            return;
        }
        mTvValue.setTextColor(getResources().getColor(R.color.bg_title));
        float size = (float)DisplayUtil.sp2px(getActivity(), 30.0f);
        mTvValue.setTextSize(size);
        if((mMonitor.getFormaldehyde() != null) && (mMonitor.getFormaldehyde().getFormaldehyde_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getFormaldehyde().getFormaldehyde_data()));
        } else {
            mTvValue.setText("XXXXX");
        }
        mTvTime.setText(mMonitor.getFormaldehyde().getCreate_date());
    }

    private void initTemperaturePage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.back);
        mTvMonitortype.setVisibility(View.GONE);
        mMyCircleView.setVisibility(View.GONE);
        mTvRightDanwei.setVisibility(View.VISIBLE);
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        mTvBottomDanwei.setText("\u5f53\u524d\u6e29\u5ea6");
        mTvRightDanwei.setText("\u2103");
        mTvRightDanwei.setTextColor(getResources().getColor(R.color.bg_title));
        mTvValue.setTextColor(getResources().getColor(R.color.bg_title));
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.bg_title));
        if(mMonitor.getTemperature() == null) {
            return;
        }
        if((mMonitor.getTemperature() != null) && (mMonitor.getTemperature().getTemperature_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getTemperature().getTemperature_data()));
        } else {
            mTvValue.setText("YYYYY");
        }
        mTvTime.setText(mMonitor.getWindSpeed().getCreate_date());
        mLoadDialog.dismiss();
    }

    private void initHumidityPage() {
        mTvMonitortype.setText("TTTTTT");
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
                mTvValue.setText("AAAAAA");
            }
//            else {
//                mTvRightDanwei.setVisibility(View.GONE);
//                mTvValue.setText("BBBBBB");
//            }
        }
        mTvTime.setText(mMonitor.getHumidity().getCreate_date());
        mLoadDialog.dismiss();
    }

    private void initPmPage() {
        mRlatMonitor.setBackgroundResource(R.mipmap.back);
        mTvMonitortype.setText("ZZZZZ");
        mMyCircleView.setVisibility(View.GONE);
        mTvMonitortype.setTextColor(getResources().getColor(R.color.bg_title));
        mTvBottomDanwei.setText("ug/m\u00b3");
        mTvBottomDanwei.setTextColor(getResources().getColor(R.color.bg_title));
        mTvBottomDanwei.setVisibility(View.VISIBLE);
        if(mMonitor.getPm() == null) {
            return;
        }
        mTvValue.setTextColor(getResources().getColor(R.color.bg_title));
        if((mMonitor.getPm() != null) && (mMonitor.getPm().getPm_data() != null)) {
            mTvValue.setText(String.valueOf(mMonitor.getPm().getPm_data()));
        } else {
            mTvValue.setText("CCCCCC");
        }
        mTvTime.setText(mMonitor.getPm().getCreate_date());
    }
}
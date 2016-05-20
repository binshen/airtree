package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;

public class HistoryActivity extends ABaseActivity implements View.OnClickListener {

    private String mDeviceId;
    private TextView mFormaldehyde;
    private TextView mHumidity;
    private ImageView mIvLeft;
    private ProgressBar mPb;
    private TextView mPm;
    private TextView mPurificationadd;
    private TextView mTemperature;
    private TextView mTvHistoryDate;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if(getIntent() != null) {
            mDeviceId = getIntent().getStringExtra("device_id");
        }
        initTitle();
        initView();
    }

    private void initTitle() {
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTvHistoryDate = (TextView)findViewById(R.id.tv_history_date);
        mPb = (ProgressBar)findViewById(R.id.pb);
        mPurificationadd = (TextView)findViewById(R.id.tv_purificationadd);
        mPm = (TextView)findViewById(R.id.tv_pm);
        mTemperature = (TextView)findViewById(R.id.tv_temperature);
        mHumidity = (TextView)findViewById(R.id.tv_humidity);
        mFormaldehyde = (TextView)findViewById(R.id.tv_formaldehyde);
        mTvHistoryDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_history:
            {
                showDatePicker();
                break;
            }
        }
    }

    private void showDatePicker() {
//        TimePopupWindow pwTime = new TimePopupWindow(this, TimePopupWindow.Type.YEAR_MONTH_DAY);
//        Date initDate = new Date();
//        pwTime.setTime(initDate);
//        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener(this) {
//
//            3(HistoryActivity p1) {
//            }
//
//            public void onTimeSelect(Date date) {
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                if(date != null) {
//                    mTvHistoryDate.setText(format.format(date));
//                    String time = format.format(date);
//                }
//            }
//        });
//        pwTime.showAtLocation(mTvHistoryDate, 0x50, 0x0, 0x0, new Date());
    }
}

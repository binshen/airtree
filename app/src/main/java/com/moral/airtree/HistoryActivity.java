package com.moral.airtree;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bigkoo.pickerview.TimePickerView;

import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.AConstants;
import com.moral.airtree.utils.StringUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryActivity extends ABaseActivity implements View.OnClickListener {


    private String mDeviceMac;
    private String mDeviceName;
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

        Bundle bundle = getIntent().getExtras();
        mDeviceMac = bundle.getString("deviceMac");
        mDeviceName = bundle.getString("deviceName");

        initTitle();
        initView();
    }

    private void initTitle() {
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(mDeviceName);
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));

        mIvLeft = (ImageView)findViewById(R.id.left_btn);
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

    protected void onStart() {
        super.onStart();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        mTvHistoryDate.setText(date);

        initData(date);

        int chipLife = 25;
        mPb.setProgress(chipLife);
    }

    private void initData(String date) {
        String url = basePath + "/device/mac/" + mDeviceMac + "/get_history?day=" + date.replaceAll("-", "");
        RequestQueue queue = application.getRequestQueue();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mPm.setText(StringUtils.getFormatData(response.optString("x1")));
                mTemperature.setText(StringUtils.getFormatData(response.optString("x11")));
                mHumidity.setText(StringUtils.getFormatData(response.optString("x10")));
                mFormaldehyde.setText(StringUtils.getFormatData(response.optString("x9")));
                mPurificationadd.setText(StringUtils.getFormatData(response.optString("x3")));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(AConstants.IS_DEBUG_MODE){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    mLoadDialog.dismiss();
                }
            }
        });
        queue.add(jsonRequest);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_history_date:
            {
                showDatePicker();
                break;
            }
        }
    }

    //https://github.com/saiwu-bigkoo/Android-PickerView/blob/master/app/src/main/java/com/bigkoo/pickerviewdemo/MainActivity.java
    private void showDatePicker() {
        TimePickerView pwTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pwTime.setTime(new Date());
        pwTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if(date != null) {
                    mTvHistoryDate.setText(format.format(date));
                    initData(format.format(date));
                }
            }
        });
        pwTime.show();
    }
}

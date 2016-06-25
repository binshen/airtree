package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.Volley;
import com.bigkoo.pickerview.TimePickerView;

import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.User;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("HistoryActivity", response.toString());
//                mPm.setText(response.optString("x1"));
//                mTemperature.setText(response.optString("x11"));
//                mHumidity.setText(response.optString("x10"));
//                mFormaldehyde.setText(response.optString("x9"));
//                mPurificationadd.setText(response.optString("x3"));
                mPm.setText(new BigDecimal(response.optString("x1")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                mTemperature.setText(new BigDecimal(response.optString("x11")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                mHumidity.setText(new BigDecimal(response.optString("x10")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                mFormaldehyde.setText(new BigDecimal(response.optString("x9")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                mPurificationadd.setText(new BigDecimal(response.optString("x3")).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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

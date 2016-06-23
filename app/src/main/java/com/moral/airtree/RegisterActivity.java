package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.ABaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnGetvalidate;
    private Button mBtnRegister;
    private EditText mEtInputpasswd;
    private EditText mEtInputvalidate;
    private EditText mEtPhonenum;
    private ImageView mIvBack;
    private CountDownTimer mTime;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEtPhonenum = (EditText)findViewById(R.id.et_phonenum);
        mEtInputpasswd = (EditText)findViewById(R.id.et_inputpasswd);
        mEtInputvalidate = (EditText)findViewById(R.id.et_inputvalidate);
        mIvBack = (ImageView)findViewById(R.id.left_btn);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("注 册");
        mBtnGetvalidate = (Button)findViewById(R.id.btn_getvalidate);
        mBtnRegister = (Button)findViewById(R.id.btn_register);
        mIvBack.setOnClickListener(this);
        mBtnGetvalidate.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mTime = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                mBtnGetvalidate.setClickable(false);
                mBtnGetvalidate.setText("剩余" + millisUntilFinished / 1000 + "秒");
            }

            public void onFinish() {
                mBtnGetvalidate.setClickable(true);
                mBtnGetvalidate.setText("获取验证码");
            }
        };
    }

    @Override
    public void onClick(View v) {
        String username = mEtPhonenum.getText().toString().trim();
        String inputvalidate = mEtInputvalidate.getText().toString().trim();
        String password = mEtInputpasswd.getText().toString().trim();
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_getvalidate:
                mTime.start();
                break;

            case R.id.btn_register:
                registerUser(username, inputvalidate, password);
                break;
        }
    }

    public void registerUser(String tel, String valid, String pwd) {
        String url = basePath + "/user/register";
        Log.d("debug", url);

        final Map<String, String> params = new HashMap<>();
        params.put("username", tel);
        params.put("password", pwd);
        params.put("validate", valid);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("debug", response.toString());
                boolean success = response.optBoolean("success");
                if (success) {
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.optString("error"), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonRequest);
    }
}

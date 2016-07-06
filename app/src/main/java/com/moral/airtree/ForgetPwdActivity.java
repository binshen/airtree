package com.moral.airtree;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
import com.moral.airtree.common.ABaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPwdActivity extends ABaseActivity implements View.OnClickListener {

    private ImageView mBtnBack;
    private Button mBtnGetvalidate;
    private Button mBtnResetpwd;
    private EditText mEtInputnewpasswd;
    private EditText mEtInputvalidate;
    private EditText mEtPhonenum;
    private CountDownTimer mTime;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("找回密码");
        mEtPhonenum = (EditText)findViewById(R.id.et_phonenum);
        mEtInputnewpasswd = (EditText)findViewById(R.id.et_inputnewpasswd);
        mEtInputvalidate = (EditText)findViewById(R.id.et_inputvalidate);
        mBtnBack = (ImageView)findViewById(R.id.left_btn);
        mBtnGetvalidate = (Button)findViewById(R.id.btn_getvalidate);
        mBtnResetpwd = (Button)findViewById(R.id.btn_resetpwd);
        mBtnBack.setOnClickListener(this);
        mBtnGetvalidate.setOnClickListener(this);
        mBtnResetpwd.setOnClickListener(this);

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
        String username  = mEtPhonenum.getText().toString().trim();
        String password  = mEtInputnewpasswd.getText().toString().trim();
        String input_cd = mEtInputvalidate.getText().toString().trim();
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_getvalidate:
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(username);
                break;

            case R.id.btn_resetpwd:
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(input_cd)) {
                    Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(username, password, input_cd);
                break;
        }
    }

    private void sendMessage(String tel) {
        String url = basePath + "/user/request_code";
        final Map<String, String> params = new HashMap<>();
        params.put("tel", tel);

        RequestQueue queue = application.getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ForgetPwdActivity", response.toString());
                boolean success = response.optBoolean("success");
                if (success) {
                    mTime.start();
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

    private void changePassword(String tel, String pwd, String code) {
        String url = basePath + "/user/forget_psw";
        final Map<String, String> params = new HashMap<>();
        params.put("username", tel);
        params.put("password", pwd);
        params.put("code", code);

        RequestQueue queue = application.getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ForgetPwdActivity", response.toString());
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

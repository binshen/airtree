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
import com.moral.airtree.common.AConstants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnGetvalidate;
    private Button mBtnRegister;
    private EditText mEtInputpasswd;
    private EditText mEtInputvalidate;
    private EditText mEtPhonenum;
    private EditText mEtInputemail;
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
        mEtInputemail = (EditText)findViewById(R.id.et_inputemail);
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
        String username  = mEtPhonenum.getText().toString().trim();
        String password  = mEtInputpasswd.getText().toString().trim();
        String input_cd = mEtInputvalidate.getText().toString().trim();
        String email = mEtInputemail.getText().toString().trim();
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

            case R.id.btn_register:
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
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "请输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isEmail(email)) {
                    Toast.makeText(getApplicationContext(), "您输入的邮箱格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(username, password, input_cd, email);
                break;
        }
    }

    private boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private void sendMessage(String tel) {
        String url = basePath + "/user/request_code";
        final Map<String, String> params = new HashMap<>();
        params.put("tel", tel);

        RequestQueue queue = application.getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RegisterActivity", response.toString());
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
                Toast.makeText(getApplicationContext(), AConstants.IS_DEBUG_MODE ? error.toString() : "网络故障，请稍候重试", Toast.LENGTH_SHORT).show();
                if (mLoadDialog.isShowing()) {
                    mLoadDialog.dismiss();
                }
            }
        });
        queue.add(jsonRequest);
    }

    private void registerUser(String tel, String pwd, String code, String email) {
        String url = basePath + "/user/register";
        final Map<String, String> params = new HashMap<>();
        params.put("username", tel);
        params.put("password", pwd);
        params.put("email", email);
        params.put("code", code);

        RequestQueue queue = application.getRequestQueue();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("RegisterActivity", response.toString());
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
                Toast.makeText(getApplicationContext(), AConstants.IS_DEBUG_MODE ? error.toString() : "网络故障，请稍候重试", Toast.LENGTH_SHORT).show();
                if (mLoadDialog.isShowing()) {
                    mLoadDialog.dismiss();
                }
            }
        });
        queue.add(jsonRequest);
    }
}

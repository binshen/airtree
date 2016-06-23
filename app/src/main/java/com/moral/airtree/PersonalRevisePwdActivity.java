package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.moral.airtree.model.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalRevisePwdActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRevise;
    private EditText mEtAginnewpwd;
    private EditText mEtNewpwd;
    private EditText mEtOldpwd;
    private ImageView mIvBack;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_revise_pwd);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("修改密码");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvBack = (ImageView)findViewById(R.id.left_btn);
        mIvBack.setImageResource(R.mipmap.back);
        mEtOldpwd = (EditText)findViewById(R.id.et_oldpwd);
        mEtNewpwd = (EditText)findViewById(R.id.et_newpwd);
        mEtAginnewpwd = (EditText)findViewById(R.id.et_aginnewpwd);
        mBtnRevise = (Button)findViewById(R.id.btn_revise);
        mIvBack.setOnClickListener(this);
        mBtnRevise.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_revise:
                String oldpwd = mEtOldpwd.getText().toString();
                if(TextUtils.isEmpty(oldpwd)) {
                    Toast.makeText(getApplicationContext(), "请输入原密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String newpwd = mEtNewpwd.getText().toString();
                if(TextUtils.isEmpty(newpwd)) {
                    Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String aginnewpwd = mEtAginnewpwd.getText().toString();
                if(TextUtils.isEmpty(aginnewpwd)) {
                    Toast.makeText(getApplicationContext(), "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpwd.equals(aginnewpwd)) {
                    Toast.makeText(getApplicationContext(), "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                changePassword(oldpwd, newpwd);
                break;
        }
    }

    public void changePassword(String oldPwd, String newPwd) {
        String url = basePath + "/user/" + application.getLoginUserID() + "/change_pwd";
        RequestQueue queue = Volley.newRequestQueue(this);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("password", oldPwd);
        params.put("new_password", newPwd);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

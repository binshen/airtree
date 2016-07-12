package com.moral.airtree;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class PersonalReviseNickNameActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRevise;
    private EditText mEtNickName;
    private ImageView mIvLeft;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_revise_nickname);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("修改昵称");
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mEtNickName = (EditText)findViewById(R.id.et_nickname);
        mBtnRevise = (Button)findViewById(R.id.btn_revise);
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mIvLeft.setOnClickListener(this);
        mBtnRevise.setOnClickListener(this);

        mEtNickName.setText(application.getLoginUserNickname());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_revise:
                changeNickName(mEtNickName.getText().toString());
                break;
        }
    }

    public void changeNickName(final String userName) {
        if(userName == null || userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入用户昵称", Toast.LENGTH_LONG).show();
        } else {
            mLoadDialog.show();

            String url = basePath + "/user/" + application.getLoginUserID() + "/update_name";
            RequestQueue queue = application.getRequestQueue();

            final Map<String, String> params = new HashMap<String, String>();
            params.put("nickname", userName);

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("PersonalReviseNickName", response.toString());
                    boolean success = response.optBoolean("success");
                    if (success) {
                        application.setLoginUserNickname(userName);

                        SharedPreferences sp = getSharedPreferences(AConstants.SP_LOGIN_USER_KEY, Context.MODE_PRIVATE);
                        sp.edit().putString("nickname", userName).commit();

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), response.optString("error"), Toast.LENGTH_SHORT).show();
                        mLoadDialog.dismiss();
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
}

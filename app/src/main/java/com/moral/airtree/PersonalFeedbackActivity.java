package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.ABaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PersonalFeedbackActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnCommit;
    private EditText mEtFeedback;
    private ImageView mIvLeft;
    private TextView mTvCount;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_feedback);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvTitle.setText("用户反馈");
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mBtnCommit = (Button)findViewById(R.id.btn_commit);
        mTvCount = (TextView)findViewById(R.id.tv_count);
        mEtFeedback = (EditText)findViewById(R.id.et_feedback);
        mIvLeft.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
        mTvCount.setText("200");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_commit:
                String feedback = mEtFeedback.getText().toString().trim();
                if(!TextUtils.isEmpty(feedback)) {
                    userFeedback(feedback);
                } else {
                    Toast.makeText(getApplicationContext(), "请填写反馈信息", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void userFeedback(String feedback) {
        String url = basePath + "/user/" + application.getLoginUserID() + "/feedback";

        final Map<String, String> params = new HashMap<>();
        params.put("feedback", feedback);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                finish();
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

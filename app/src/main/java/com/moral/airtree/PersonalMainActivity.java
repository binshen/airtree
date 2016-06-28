package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.User;
import com.moral.airtree.utils.ScreenManager;

import org.json.JSONObject;

public class PersonalMainActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnExit;
    private ImageView mIvLeft;
    private RelativeLayout mRlCheck;
    private RelativeLayout mRlNickname;
    private RelativeLayout mRlRevisepwd;
    private RelativeLayout mRlUserfeedback;
    private TextView mTvTitle;
    private TextView mTvUserName;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("用户信息");
        mRlNickname = (RelativeLayout)findViewById(R.id.rl_nickname);
        mRlRevisepwd = (RelativeLayout)findViewById(R.id.rl_revisepwd);
        mRlCheck = (RelativeLayout)findViewById(R.id.rl_check);
        mRlUserfeedback = (RelativeLayout)findViewById(R.id.rl_feedback);
        mIvLeft = (ImageView)findViewById(R.id.left_btn);
        mTvUserName = (TextView)findViewById(R.id.tv_username);
        mBtnExit = (Button)findViewById(R.id.btn_exit);
        mTvTitle.setTextColor(getResources().getColor(R.color.bg_title));
        mIvLeft.setImageResource(R.mipmap.back);
        mRlNickname.setOnClickListener(this);
        mRlRevisepwd.setOnClickListener(this);
        mRlCheck.setOnClickListener(this);
        mRlUserfeedback.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
    }

    protected void onStart() {
        super.onStart();
        mTvUserName.setText(application.getLoginUserNickname());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_exit:
                String url = basePath + "/user/" + application.getLoginUserID() + "/offline";
                RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("PersonalMainActivity", response.toString());
                        application.setLoginUser(null);
                        application.setDevices(null);
                        setResult(Activity.RESULT_OK, new Intent());
                        finish();;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(jsonRequest);
                break;

            case R.id.rl_nickname:
                startActivity(new Intent(getApplicationContext(), PersonalReviseNickNameActivity.class));
                break;

            case R.id.rl_revisepwd:
                startActivity(new Intent(getApplicationContext(), PersonalRevisePwdActivity.class));
                break;

            case R.id.rl_feedback:
                startActivity(new Intent(getApplicationContext(), PersonalFeedbackActivity.class));
                break;
        }
    }
}

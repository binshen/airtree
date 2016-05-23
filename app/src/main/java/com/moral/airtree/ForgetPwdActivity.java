package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moral.airtree.common.ABaseActivity;

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
        String phonenum = mEtPhonenum.getText().toString().trim();
        String inputvalidate = mEtInputvalidate.getText().toString().trim();
        String inputpasswd = mEtInputnewpasswd.getText().toString().trim();
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_getvalidate:
                mTime.start();
                break;

            case R.id.btn_resetpwd:
                Toast.makeText(getApplicationContext(), "重置密码", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

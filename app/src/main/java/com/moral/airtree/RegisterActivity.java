package com.moral.airtree;

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

public class RegisterActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnGetvalidate;
    private Button mBtnRegister;
    private EditText mEtInputpasswd;
    private EditText mEtInputvalidate;
    private EditText mEtPhonenum;
    private ImageView mIvBack;
    private CountDownTimer mTime;
    private TextView mTvTitle;
    TextWatcher mVerifyTextWatcher;

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
        mEtPhonenum.addTextChangedListener(mVerifyTextWatcher);

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
        String inputpasswd = mEtInputpasswd.getText().toString().trim();
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_getvalidate:
                mTime.start();
                break;

            case R.id.btn_register:
                Toast.makeText(getApplicationContext(), "注册账户", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

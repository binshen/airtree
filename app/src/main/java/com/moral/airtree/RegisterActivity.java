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

import com.moral.airtree.common.ABaseActivity;

public class RegisterActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnGetvalidate;
    private Button mBtnRegister;
    private EditText mEtInputpasswd;
    private EditText mEtInputvalidate;
    private EditText mEtPhonenum;
    private ImageView mIvBack;
    TextWatcher mTextWatcher;
    //private RegisterActivity.TimeCount mTime;
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
        mEtInputvalidate.addTextChangedListener(mTextWatcher);
        mEtInputpasswd.addTextChangedListener(mTextWatcher);
        mEtPhonenum.addTextChangedListener(mVerifyTextWatcher);
        //mTime = new RegisterActivity.TimeCount(this, 0xea60, 0x3e8);
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
                break;

            case R.id.btn_register:
                break;
        }
    }

//    class TimeCount extends CountDownTimer {
//
//        public TimeCount(RegisterActivity p1, long millisInFuture, long countDownInterval) {
//            // :( Parsing error. Please contact me.
//        }
//
//        public void onFinish() {
//            // :( Parsing error. Please contact me.
//        }
//
//        public void onTick(long millisUntilFinished) {
//            mBtnGetvalidate.setClickable(false);
//            mBtnGetvalidate.setText("S");
//            mBtnGetvalidate.setText("\u79d2\u540e\u91cd\u8bd5");
//        }
//    }
}

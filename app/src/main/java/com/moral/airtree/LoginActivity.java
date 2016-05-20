package com.moral.airtree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moral.airtree.common.ABaseActivity;

public class LoginActivity extends ABaseActivity implements View.OnClickListener {

    private String checkNet;
    private Button mBtnLogin;
    private EditText mEtPassword;
    private EditText mEtUsername;
//    TextWatcher mTextWatcher;
    private TextView mTvForgetpwd;
    private TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkNetWork();

        mEtUsername = (EditText)findViewById(R.id.et_username);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mTvForgetpwd = (TextView)findViewById(R.id.tv_forgetpwd);
        mTvRegister = (TextView)findViewById(R.id.tv_register);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mTvForgetpwd.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
//        mEtUsername.addTextChangedListener(mTextWatcher);
//        mEtPassword.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(false) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void checkNetWork() {
//        checkNet = NetUtils.getNetConnect(this);
//        if(checkNet.equals("NO")) {
//            localString1 = MyToast.makeText(this, getResources().getString(0x7f0c006d), 0x0);
//            getResources().getString(0x7f0c006d).show();
//        }
    }

    @Override
    public void onClick(View v) {

        String username = mEtUsername.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        switch(v.getId()) {
            case R.id.tv_forgetpwd:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;

            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                startActivity(new Intent(this, MainActivity.class));

//                if((!TextUtils.isEmpty(username)) && (!TextUtils.isEmpty(password))) {
//                    if(checkNet.equals("NO")) {
//                        //localString1 = MyToast.makeText(this, getResources().getString(0x7f0c006d), 0x0);
//                        //getResources().getString(0x7f0c006d).show();
//                        return;
//                    }
//                    if((username.startsWith("1")) && (username.length() == 0xb)) {
//                        login(username, password);
//                        return;
//                        //localint2 = MyToast.makeText(this, username.length(), 0x0);
//                    }
//                    //localString1 = getResources().getString(0x7f0c0014);
//                    //getResources().show();
//                    return;
//                }
//                if(TextUtils.isEmpty(username)) {
//                    //localResources3 = MyToast.makeText(this, TextUtils.isEmpty(username), 0x0);
//                    //localResources3 = getResources().getString(0x7f0c0016);
//                    //getResources().show();
//                    return;
//                }
//                if(TextUtils.isEmpty(password)) {
//                    //localString4 = MyToast.makeText(this, getResources().getString(0x7f0c001e), 0x0);
//                    //getResources().getString(0x7f0c001e).show();
//                    break;
//                }
                break;
        }
    }

    public void login(String tel, String pwd) {
        mLoadDialog.show();

    }
}

package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moral.airtree.common.ABaseActivity;

public class PersonalRevisePwdActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnRevise;
    private EditText mEtAginnewpwd;
    private EditText mEtNewpwd;
    private EditText mEtOldpwd;
    Runnable mExitRunnable;
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
                String newpwd = mEtNewpwd.getText().toString();
                String aginnewpwd = mEtAginnewpwd.getText().toString();
                if((!TextUtils.isEmpty(oldpwd)) && (!TextUtils.isEmpty(newpwd))) {
                    if(!TextUtils.isEmpty(aginnewpwd)) {
                        if(newpwd.equals(aginnewpwd)) {
                            if(!oldpwd.equals(newpwd)) {
                                changePassword(oldpwd, newpwd);
                                return;
                            }
                            Toast.makeText(PersonalRevisePwdActivity.this, "", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(PersonalRevisePwdActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                Toast.makeText(PersonalRevisePwdActivity.this, "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changePassword(String oldPwd, String newPwd) {

    }
}

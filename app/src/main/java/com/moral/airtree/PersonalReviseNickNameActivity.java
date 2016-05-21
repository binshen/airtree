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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_revise:
                String mUserName = "";//PreferencesUtils.getString(this, "userName", "");
                String nickName = mEtNickName.getText().toString();
                if(!TextUtils.isEmpty(nickName)) {
                    if(!nickName.equals(mUserName)) {
                        changeNickName(nickName);
                        return;
                    }
                    Toast.makeText(PersonalReviseNickNameActivity.this, "", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(PersonalReviseNickNameActivity.this, "", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changeNickName(String nickName) {

    }
}

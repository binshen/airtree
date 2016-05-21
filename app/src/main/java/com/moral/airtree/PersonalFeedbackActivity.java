package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.moral.airtree.common.ABaseActivity;

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
                    return;
                }
                break;
        }
    }

    private void userFeedback(String feedBack) {

    }
}

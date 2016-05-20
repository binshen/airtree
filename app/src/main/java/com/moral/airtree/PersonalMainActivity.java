package com.moral.airtree;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moral.airtree.utils.ScreenManager;

public class PersonalMainActivity extends Activity implements View.OnClickListener {

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

        ScreenManager.getScreenManager().pushActivity(this);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText("");
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.btn_exit:

                break;
        }
    }
}

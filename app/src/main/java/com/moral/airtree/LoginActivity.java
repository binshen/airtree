package com.moral.airtree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.common.AConstants;
import com.moral.airtree.model.User;
import com.moral.airtree.update.UpdateManager;
import com.moral.airtree.utils.NetUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends ABaseActivity implements View.OnClickListener {

    private Button mBtnLogin;
    private EditText mEtPassword;
    private EditText mEtUsername;
    private TextView mTvForgetpwd;
    private TextView mTvRegister;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepare();

        mEtUsername = (EditText)findViewById(R.id.et_username);
        mEtPassword = (EditText)findViewById(R.id.et_password);
        mTvForgetpwd = (TextView)findViewById(R.id.tv_forgetpwd);
        mTvRegister = (TextView)findViewById(R.id.tv_register);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mTvForgetpwd.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        mEtUsername.setText("13999999999");
        mEtPassword.setText("888888");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void prepare() {
        if(!NetUtils.getNetConnect(this)) {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        } else {
            if(Build.VERSION.SDK_INT >= 11) {
                new checkUpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                new checkUpdateTask().execute();
            }

            sp = getSharedPreferences(AConstants.SP_LOGIN_USER_KEY, Context.MODE_PRIVATE);
            if(!sp.getString("user_id", "").equals("")) {
                User loginUser = new User();
                loginUser.set_id(sp.getString("user_id", ""));
                loginUser.setUsername(sp.getString("username", ""));
                loginUser.setPassword(sp.getString("password", ""));
                loginUser.setNickname(sp.getString("nickname", ""));
                application.setLoginUser(loginUser);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }
    }

    private class checkUpdateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Looper.prepare();
            // 检查软件更新
            UpdateManager manager = new UpdateManager(LoginActivity.this);
            manager.checkUpdate();

            Looper.loop();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
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
                if((!TextUtils.isEmpty(username)) && (!TextUtils.isEmpty(password))) {
                    if(!NetUtils.getNetConnect(this)) {
                        Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if((username.startsWith("1")) && (username.length() == 11)) {
                        login(username, password);
                        return;
                    }
                    Toast.makeText(this, R.string.input_phonenum, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)) {
                    Toast.makeText(this, R.string.input_username, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(this, R.string.input_pwd, Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
        }
    }

    public void login(final String tel, final String pwd) {
        mLoadDialog.show();

        String url = basePath + "/user/login";
        RequestQueue queue = application.getRequestQueue();

        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", tel);
        params.put("password", pwd);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean success = response.optBoolean("success");
                JSONObject user = response.optJSONObject("user");
                if (success && user != null) {
                    User loginUser = new User();
                    loginUser.set_id(response.optJSONObject("user").optString("_id"));
                    loginUser.setUsername(response.optJSONObject("user").optString("username"));
                    loginUser.setPassword(response.optJSONObject("user").optString("password"));
                    loginUser.setNickname(response.optJSONObject("user").optString("nickname"));
                    application.setLoginUser(loginUser);

                    if(sp == null) {
                        sp = getSharedPreferences(AConstants.SP_LOGIN_USER_KEY, Context.MODE_PRIVATE);
                    }

                    sp.edit().putString("user_id", loginUser.get_id()).commit();
                    sp.edit().putString("username", loginUser.getUsername()).commit();
                    sp.edit().putString("password", loginUser.getPassword()).commit();
                    sp.edit().putString("nickname", loginUser.getNickname()).commit();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    SharedPreferences sp = getSharedPreferences(AConstants.SP_LOGIN_USER_KEY, Context.MODE_PRIVATE);
                    sp.edit().putString("user_id", "").commit();

                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), AConstants.IS_DEBUG_MODE ? error.toString() : "网络故障，请稍候重试", Toast.LENGTH_SHORT).show();
                if (mLoadDialog.isShowing()) {
                    mLoadDialog.dismiss();
                }
            }
        });
//        jsonRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 60000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 60000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
        queue.add(jsonRequest);

/*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        queue.add(stringRequest);
*/
    }
}

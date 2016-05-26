package com.moral.airtree;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.common.ABaseActivity;
import com.moral.airtree.model.User;
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

        mEtUsername.setText("13999999999");
        mEtPassword.setText("888888");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(application.getLoginUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void checkNetWork() {
        if(!NetUtils.getNetConnect(this)) {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
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
/*
                User loginUser = new User();
                loginUser.setUsername(username);
                loginUser.setPassword(password);
                application.setLoginUser(loginUser);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
*/

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
        RequestQueue queue = Volley.newRequestQueue(this);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", tel);
        params.put("password", pwd);

        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean success = response.optBoolean("success");
                if (success) {
                    //Toast.makeText(getApplicationContext(), response.optJSONObject("userInfo").toString(), Toast.LENGTH_LONG).show();
                    User loginUser = new User();
                    loginUser.setUsername(tel);
                    loginUser.setPassword(pwd);
                    application.setLoginUser(loginUser);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjRequest);

/*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                User loginUser = new User();
                loginUser.setUsername(tel);
                loginUser.setPassword(pwd);
                application.setLoginUser(loginUser);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

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

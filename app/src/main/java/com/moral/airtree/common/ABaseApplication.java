package com.moral.airtree.common;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.moral.airtree.model.Device;
import com.moral.airtree.model.User;

/**
 * Created by bin.shen on 5/20/16.
 */
public class ABaseApplication extends Application {

    private RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    private User loginUser = null;

    public User getLoginUser() {
        return loginUser;
    }

    public String getLoginUserID() {
        return loginUser == null ? null : loginUser.get_id();
    }

    public String getLoginUserNickname() {
        if(loginUser != null) {
            if(!loginUser.getNickname().isEmpty()) {
                return loginUser.getNickname();
            } else if(!loginUser.getUsername().isEmpty()) {
                return loginUser.getUsername();
            }
        }
        return null;
    }

    public void setLoginUserNickname(String nickname) {
        if(loginUser != null) {
            loginUser.setNickname(nickname);
        }
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
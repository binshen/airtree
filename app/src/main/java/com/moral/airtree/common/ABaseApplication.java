package com.moral.airtree.common;

import android.app.Application;

import com.moral.airtree.model.Device;
import com.moral.airtree.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bin.shen on 5/20/16.
 */
public class ABaseApplication extends Application {

    public User loginUser = null;

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public List<Device> mDevices = new ArrayList<Device>();

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
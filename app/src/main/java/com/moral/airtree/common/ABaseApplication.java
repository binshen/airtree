package com.moral.airtree.common;

import android.app.Application;

import com.moral.airtree.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bin.shen on 5/20/16.
 */
public class ABaseApplication extends Application {

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
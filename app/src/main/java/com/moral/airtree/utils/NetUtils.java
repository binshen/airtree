package com.moral.airtree.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.IOException;

/**
 * Created by bin.shen on 5/18/16.
 */
public class NetUtils {

    public static boolean getNetConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//            if(activeNetInfo.getType()==ConnectivityManager.TYPE_WIFI){ //判断WIFI网
//
//            } else if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
//
//            }
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean getWifiConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){ //WIFI网
                return true;
            }
        }
        return false;
    }
}

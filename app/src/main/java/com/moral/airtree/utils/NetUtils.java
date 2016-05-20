package com.moral.airtree.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.IOException;

/**
 * Created by bin.shen on 5/18/16.
 */
public class NetUtils {

    public static String getNetConnect(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo.State mobile = connectivityManager.getNetworkInfo(0x0).getState();
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(0x1).getState();
        if((mobile == NetworkInfo.State.CONNECTED) || (mobile == NetworkInfo.State.CONNECTING)) {

        }
        if((wifi == NetworkInfo.State.CONNECTED) || (wifi == NetworkInfo.State.CONNECTING)) {

        }
        //return netWorkString;
        return "No";
    }

    public static void pingNet(Context context) {
        // :( Parsing error. Please contact me.
    }
}

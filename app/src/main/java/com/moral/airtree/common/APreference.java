package com.moral.airtree.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bin.shen on 6/22/16.
 */
public class APreference {
    private static final String IS_DEVICE_CHANGE = "device_changed";
    private static final String KEY_WIFI = "wifi";
    private static final String PREFERENCE = "setting";
    private static APreference mHaierPreference;
    private static SharedPreferences mPreferences;

    private APreference(Context context) {
        if(context != null) {
            mPreferences = context.getSharedPreferences("setting", 0x0);
        }
    }

    public static APreference getInstance(Context context) {
        if(mHaierPreference == null) {
            mHaierPreference = new APreference(context);
        }
        return mHaierPreference;
    }

    public boolean setDeviceChanged(boolean isChanged) {
        if(mPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("device_changed", isChanged);
        return editor.commit();
    }

    public boolean getDeviceChanged() {
        if(mPreferences == null) {
            return false;
        }
        boolean isFirst = mPreferences.getBoolean("device_changed", false);
        return isFirst;
    }

//    public boolean setWifiPwd(String wifiSSID, String wifiPwd) {
//        if(mPreferences == null) {
//            return false;
//        }
//        String allWifiStr = mPreferences.getString("wifi", "");
//        Log.d("AirTree", allWifiStr);
//        PaserJson<WifiBean> pJson = new PaserJson<WifiBean>();
//        List<WifiBean> wifiList = pJson.getBeanList(allWifiStr, WifiBean.class);
//        if(wifiList != null) {
//            if(!wifiList.iterator().hasNext()) {
//            }
//            WifiBean bean = (WifiBean)wifiList.iterator().next();
//            if(bean.getSsid().equals(wifiSSID)) {
//                bean.setPwd(wifiPwd);
//            }
//        } else {
//            ArrayList wifiList = new ArrayList();
//            WifiBean bean = new WifiBean();
//            bean.setSsid(wifiSSID);
//            bean.setPwd(wifiPwd);
//            wifiList.add(bean);
//        }
//        Gson gson = new Gson();
//        allWifiStr = gson.toJson(wifiList);
//        Log.d("AirTree", allWifiStr);
//        SharedPreferences.Editor editor = mPreferences.edit();
//        editor.putString("wifi", allWifiStr);
//        return editor.commit();
//    }
//
//    public String getWifiPwd(String wifiSSID) {
//        if(mPreferences == null) {
//            return "";
//        }
//        String allWifiStr = mPreferences.getString("wifi", "");
//        PaserJson<WifiBean> pJson = new PaserJson<WifiBean>();
//        List<WifiBean> wifiList = pJson.getBeanList(allWifiStr, WifiBean.class);
//        if(wifiList != null) {
//            if(!wifiList.iterator().hasNext()) {
//            }
//            WifiBean bean = (WifiBean)wifiList.iterator().next();
//            if(bean.getSsid().equals(wifiSSID)) {
//                return bean.getPwd();
//            }
//        }
//        return "";
//    }
}

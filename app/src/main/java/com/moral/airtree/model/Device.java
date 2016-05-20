package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/20/16.
 */
public class Device {

    private String mac;

    private String ip;

    private int status;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

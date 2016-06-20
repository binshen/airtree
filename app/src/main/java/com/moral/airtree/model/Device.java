package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/20/16.
 */
public class Device implements Serializable {

    private String name;

    private String mac;

    private String userID;

    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

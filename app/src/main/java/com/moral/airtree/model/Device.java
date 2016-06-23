package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/20/16.
 */
public class Device implements Serializable {

    private String _id;

    private String mac;

    private int type;

    private String userID;

    private String name;

    private int status;

    private long last_updated;

    private int app_status;

    private long app_last_updated;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(long last_updated) {
        this.last_updated = last_updated;
    }

    public int getApp_status() {
        return app_status;
    }

    public void setApp_status(int app_status) {
        this.app_status = app_status;
    }

    public long getApp_last_updated() {
        return app_last_updated;
    }

    public void setApp_last_updated(long app_last_updated) {
        this.app_last_updated = app_last_updated;
    }
}

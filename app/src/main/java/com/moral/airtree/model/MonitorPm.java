package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/24/16.
 */
public class MonitorPm implements Serializable {
    private String create_date;
    private int pm03p01;
    private String pmId;
    private Long pm_data;

    public MonitorPm(String create_date, Long pm_data, String pmId) {
        this.create_date = create_date;
        this.pm_data = pm_data;
        this.pmId = pmId;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        create_date = create_date;
    }

    public Long getPm_data() {
        return pm_data;
    }

    public void setPm_data(Long pm_data) {
        pm_data = pm_data;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        pmId = pmId;
    }

    public int getPm03p01() {
        return pm03p01;
    }

    public void setPm03p01(int pm03p01) {
        pm03p01 = pm03p01;
    }
}
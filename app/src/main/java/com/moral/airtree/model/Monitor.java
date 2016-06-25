package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 6/25/16.
 */
public class Monitor implements Serializable {

    private String created;
    private Integer electricQuantity;
    private Long light;
    private Long pm03p01;
    private Long pm_data;
    private Integer temperature_data;
    private Long windSpeed_data;
    private Integer humidity_data;
    private Long formaldehyde_data;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(Integer electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public Long getLight() {
        return light;
    }

    public void setLight(Long light) {
        this.light = light;
    }

    public Long getPm03p01() {
        return pm03p01;
    }

    public void setPm03p01(Long pm03p01) {
        this.pm03p01 = pm03p01;
    }

    public Long getPm_data() {
        return pm_data;
    }

    public void setPm_data(Long pm_data) {
        this.pm_data = pm_data;
    }

    public Integer getTemperature_data() {
        return temperature_data;
    }

    public void setTemperature_data(Integer temperature_data) {
        this.temperature_data = temperature_data;
    }

    public Long getWindSpeed_data() {
        return windSpeed_data;
    }

    public void setWindSpeed_data(Long windSpeed_data) {
        this.windSpeed_data = windSpeed_data;
    }

    public Integer getHumidity_data() {
        return humidity_data;
    }

    public void setHumidity_data(Integer humidity_data) {
        this.humidity_data = humidity_data;
    }

    public Long getFormaldehyde_data() {
        return formaldehyde_data;
    }

    public void setFormaldehyde_data(Long formaldehyde_data) {
        this.formaldehyde_data = formaldehyde_data;
    }
}

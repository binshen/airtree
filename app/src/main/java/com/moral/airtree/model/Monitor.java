package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 6/25/16.
 */
public class Monitor implements Serializable {

    private String created;
    private int electricQuantity;
    private long light;
    private long pm03p01;
    private long pm_data;
    private int temperature_data;
    private long windSpeed_data;
    private int humidity_data;
    private double formaldehyde_data;

    private int priority1;
    private int priority2;
    private int priority3;
    private int priority4;
    private int feiLevel;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(int electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public long getLight() {
        return light;
    }

    public void setLight(long light) {
        this.light = light;
    }

    public long getPm03p01() {
        return pm03p01;
    }

    public void setPm03p01(long pm03p01) {
        this.pm03p01 = pm03p01;
    }

    public long getPm_data() {
        return pm_data;
    }

    public void setPm_data(long pm_data) {
        this.pm_data = pm_data;
    }

    public int getTemperature_data() {
        return temperature_data;
    }

    public void setTemperature_data(int temperature_data) {
        this.temperature_data = temperature_data;
    }

    public long getWindSpeed_data() {
        return windSpeed_data;
    }

    public void setWindSpeed_data(long windSpeed_data) {
        this.windSpeed_data = windSpeed_data;
    }

    public int getHumidity_data() {
        return humidity_data;
    }

    public void setHumidity_data(int humidity_data) {
        this.humidity_data = humidity_data;
    }

    public double getFormaldehyde_data() {
        return formaldehyde_data;
    }

    public void setFormaldehyde_data(double formaldehyde_data) {
        this.formaldehyde_data = formaldehyde_data;
    }

    public int getPriority1() {
        return priority1;
    }

    public void setPriority1(int priority1) {
        this.priority1 = priority1;
    }

    public int getPriority2() {
        return priority2;
    }

    public void setPriority2(int priority2) {
        this.priority2 = priority2;
    }

    public int getPriority3() {
        return priority3;
    }

    public void setPriority3(int priority3) {
        this.priority3 = priority3;
    }

    public int getPriority4() {
        return priority4;
    }

    public void setPriority4(int priority4) {
        this.priority4 = priority4;
    }

    public int getFeiLevel() {
        return feiLevel;
    }

    public void setFeiLevel(int feiLevel) {
        this.feiLevel = feiLevel;
    }
}

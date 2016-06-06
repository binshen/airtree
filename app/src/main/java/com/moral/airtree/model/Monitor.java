package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/24/16.
 */
public class Monitor implements Serializable {
    private String chipLife;
    private String create_date;
    private String dataId;
    private String deviceId;
    private Long deviceStatus;
    private String electricQuantity;
    private MonitorFormaldehyde formaldehyde;
    private MonitorHumidity humidity;
    private Long light;
    private MonitorPm pm;
    private String purification;
    private MonitorTemperature temperature;
    private MonitorWindSpeed windSpeed;

    public Monitor(String dataId, String electricQuantity, String chipLife, Long light, String deviceId, Long deviceStatus, String purification, String create_date, MonitorPm pm, MonitorWindSpeed windSpeed, MonitorHumidity humidity, MonitorTemperature temperature, MonitorFormaldehyde formaldehyde) {
        this.dataId = dataId;
        this.electricQuantity = electricQuantity;
        this.chipLife = chipLife;
        this.light = light;
        this.deviceId = deviceId;
        this.deviceStatus = deviceStatus;
        this.purification = purification;
        this.create_date = create_date;
        this.pm = pm;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.temperature = temperature;
        this.formaldehyde = formaldehyde;
    }

    public String getChipLife() {
        return chipLife;
    }

    public void setChipLife(String chipLife) {
        this.chipLife = chipLife;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Long deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(String electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public MonitorFormaldehyde getFormaldehyde() {
        return formaldehyde;
    }

    public void setFormaldehyde(MonitorFormaldehyde formaldehyde) {
        this.formaldehyde = formaldehyde;
    }

    public MonitorHumidity getHumidity() {
        return humidity;
    }

    public void setHumidity(MonitorHumidity humidity) {
        this.humidity = humidity;
    }

    public Long getLight() {
        return light;
    }

    public void setLight(Long light) {
        this.light = light;
    }

    public MonitorPm getPm() {
        return pm;
    }

    public void setPm(MonitorPm pm) {
        this.pm = pm;
    }

    public String getPurification() {
        return purification;
    }

    public void setPurification(String purification) {
        this.purification = purification;
    }

    public MonitorTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(MonitorTemperature temperature) {
        this.temperature = temperature;
    }

    public MonitorWindSpeed getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(MonitorWindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }
}
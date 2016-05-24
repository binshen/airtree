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
        dataId = dataId;
        electricQuantity = electricQuantity;
        chipLife = chipLife;
        light = light;
        deviceId = deviceId;
        deviceStatus = deviceStatus;
        purification = purification;
        create_date = create_date;
        pm = pm;
        windSpeed = windSpeed;
        humidity = humidity;
        temperature = temperature;
        formaldehyde = formaldehyde;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        dataId = dataId;
    }

    public String getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(String electricQuantity) {
        electricQuantity = electricQuantity;
    }

    public String getChipLife() {
        return chipLife;
    }

    public void setChipLife(String chipLife) {
        chipLife = chipLife;
    }

    public Long getLight() {
        return light;
    }

    public void setLight(Long light) {
        light = light;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        deviceId = deviceId;
    }

    public Long getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Long deviceStatus) {
        deviceStatus = deviceStatus;
    }

    public String getPurification() {
        return purification;
    }

    public void setPurification(String purification) {
        purification = purification;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        create_date = create_date;
    }

    public MonitorPm getPm() {
        return pm;
    }

    public void setPm(MonitorPm pm) {
        pm = pm;
    }

    public MonitorWindSpeed getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(MonitorWindSpeed windSpeed) {
        windSpeed = windSpeed;
    }

    public MonitorHumidity getHumidity() {
        return humidity;
    }

    public void setHumidity(MonitorHumidity humidity) {
        humidity = humidity;
    }

    public MonitorTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(MonitorTemperature temperature) {
        temperature = temperature;
    }

    public MonitorFormaldehyde getFormaldehyde() {
        return formaldehyde;
    }

    public void setFormaldehyde(MonitorFormaldehyde formaldehyde) {
        formaldehyde = formaldehyde;
    }
}
package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/24/16.
 */
public class MonitorOther implements Serializable {
    private String Uid;
    private String airQuality;
    private String create_date;
    private String dataId;
    private String electricQuantity;
    private String formaldehydeId;
    private String humidityId;
    private String place;
    private String pmId;
    private String purification;
    private String temperatureId;
    private String tips;
    private String windSpeedId;

    public MonitorOther(String uid, String airQuality, String create_date, String dataId, String electricQuantity, String formaldehydeId, String humidityId, String place, String pmId, String purification, String temperatureId, String tips, String windSpeedId) {
        this.Uid = uid;
        this.airQuality = airQuality;
        this.create_date = create_date;
        this.dataId = dataId;
        this.electricQuantity = electricQuantity;
        this.formaldehydeId = formaldehydeId;
        this.humidityId = humidityId;
        this.place = place;
        this.pmId = pmId;
        this.purification = purification;
        this.temperatureId = temperatureId;
        this.tips = tips;
        this.windSpeedId = windSpeedId;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
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

    public String getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(String electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public String getFormaldehydeId() {
        return formaldehydeId;
    }

    public void setFormaldehydeId(String formaldehydeId) {
        this.formaldehydeId = formaldehydeId;
    }

    public String getHumidityId() {
        return humidityId;
    }

    public void setHumidityId(String humidityId) {
        this.humidityId = humidityId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPurification() {
        return purification;
    }

    public void setPurification(String purification) {
        this.purification = purification;
    }

    public String getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(String temperatureId) {
        this.temperatureId = temperatureId;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getWindSpeedId() {
        return windSpeedId;
    }

    public void setWindSpeedId(String windSpeedId) {
        this.windSpeedId = windSpeedId;
    }
}
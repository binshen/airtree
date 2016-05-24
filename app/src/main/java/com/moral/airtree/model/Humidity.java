package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/24/16.
 */
public class Humidity {
    private String dataId;
    private String humidity;
    private String humidityId;
    private String humidity_data;
    private String humidity_date;
    private String humidity_tips;

    public Humidity(String humidity_data, String humidity_tips, String humidityId, String dataId, String humidity_date, String humidity) {
        this.humidity_data = humidity_data;
        this.humidity_tips = humidity_tips;
        this.humidityId = humidityId;
        this.dataId = dataId;
        this.humidity_date = humidity_date;
        this.humidity = humidity;
    }

    public String getHumidity_data() {
        return humidity_data;
    }

    public void setHumidity_data(String humidity_data) {
        humidity_data = humidity_data;
    }

    public String getHumidity_tips() {
        return humidity_tips;
    }

    public void setHumidity_tips(String humidity_tips) {
        humidity_tips = humidity_tips;
    }

    public String getHumidityId() {
        return humidityId;
    }

    public void setHumidityId(String humidityId) {
        humidityId = humidityId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        dataId = dataId;
    }

    public String getHumidity_date() {
        return humidity_date;
    }

    public void setHumidity_date(String humidity_date) {
        humidity_date = humidity_date;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        humidity = humidity;
    }
}
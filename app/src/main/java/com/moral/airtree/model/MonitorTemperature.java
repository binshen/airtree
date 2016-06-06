package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/24/16.
 */
public class MonitorTemperature implements Serializable {
    private String create_date;
    private String temperatureId;
    private String temperature_data;

    public MonitorTemperature(String temperature_data, String create_date, String temperatureId) {
        this.temperature_data = temperature_data;
        this.create_date = create_date;
        this.temperatureId = temperatureId;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTemperatureId() {
        return temperatureId;
    }

    public void setTemperatureId(String temperatureId) {
        this.temperatureId = temperatureId;
    }

    public String getTemperature_data() {
        return temperature_data;
    }

    public void setTemperature_data(String temperature_data) {
        this.temperature_data = temperature_data;
    }
}
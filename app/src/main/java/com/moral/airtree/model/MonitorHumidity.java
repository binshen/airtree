package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/24/16.
 */
import java.io.Serializable;

public class MonitorHumidity implements Serializable {
    private String create_date;
    private String humidityId;
    private String humidity_data;

    public MonitorHumidity(String humidity_data, String create_date, String humidityId) {
        this.humidity_data = humidity_data;
        this.create_date = create_date;
        this.humidityId = humidityId;
    }

    public String getHumidity_data() {
        return humidity_data;
    }

    public void setHumidity_data(String humidity_data) {
        this.humidity_data = humidity_data;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getHumidityId() {
        return humidityId;
    }

    public void setHumidityId(String humidityId) {
        this.humidityId = humidityId;
    }
}

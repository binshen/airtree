package com.moral.airtree.model;

import java.io.Serializable;

/**
 * Created by bin.shen on 5/24/16.
 */
public class MonitorWindSpeed implements Serializable {
    private String create_date;
    private String windSpeedId;
    private Long windSpeed_data;

    public MonitorWindSpeed(String create_date, Long windSpeed_data, String windSpeedId) {
        this.create_date = create_date;
        this.windSpeed_data = windSpeed_data;
        this.windSpeedId = windSpeedId;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        create_date = create_date;
    }

    public Long getWindSpeed_data() {
        return windSpeed_data;
    }

    public void setWindSpeed_data(Long windSpeed_data) {
        windSpeed_data = windSpeed_data;
    }

    public String getWindSpeedId() {
        return windSpeedId;
    }

    public void setWindSpeedId(String windSpeedId) {
        windSpeedId = windSpeedId;
    }
}
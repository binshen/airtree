package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/24/16.
 */
public class History {
    private String formaldehyde_dataMean;
    private String humidity_dataMean;
    private String pm_dataMean;
    private String purificationAdd;
    private String temperature_dataMean;
    private String windSpeed_dataMean;

    public History(String formaldehyde_dataMean, String humidity_dataMean, String pm_dataMean, String temperature_dataMean, String windSpeed_dataMean, String purificationAdd) {
        this.formaldehyde_dataMean = formaldehyde_dataMean;
        this.humidity_dataMean = humidity_dataMean;
        this.pm_dataMean = pm_dataMean;
        this.temperature_dataMean = temperature_dataMean;
        this.windSpeed_dataMean = windSpeed_dataMean;
        this.purificationAdd = purificationAdd;
    }

    public String getFormaldehyde_dataMean() {
        return formaldehyde_dataMean;
    }

    public void setFormaldehyde_dataMean(String formaldehyde_dataMean) {
        formaldehyde_dataMean = formaldehyde_dataMean;
    }

    public String getHumidity_dataMean() {
        return humidity_dataMean;
    }

    public void setHumidity_dataMean(String humidity_dataMean) {
        humidity_dataMean = humidity_dataMean;
    }

    public String getPm_dataMean() {
        return pm_dataMean;
    }

    public void setPm_dataMean(String pm_dataMean) {
        pm_dataMean = pm_dataMean;
    }

    public String getTemperature_dataMean() {
        return temperature_dataMean;
    }

    public void setTemperature_dataMean(String temperature_dataMean) {
        temperature_dataMean = temperature_dataMean;
    }

    public String getWindSpeed_dataMean() {
        return windSpeed_dataMean;
    }

    public void setWindSpeed_dataMean(String windSpeed_dataMean) {
        windSpeed_dataMean = windSpeed_dataMean;
    }

    public String getPurificationAdd() {
        return purificationAdd;
    }

    public void setPurificationAdd(String purificationAdd) {
        purificationAdd = purificationAdd;
    }
}
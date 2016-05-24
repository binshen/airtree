package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/24/16.
 */
public class Formaldehyde {
    private String dataId;
    private String formaldehyde;
    private String formaldehydeId;
    private String formaldehyde_data;
    private String formaldehyde_date;
    private String formaldehyde_tips;

    public Formaldehyde(String formaldehyde_data, String formaldehyde_tips, String formaldehydeId, String dataId, String formaldehyde_date, String formaldehyde) {
        this.formaldehyde_data = formaldehyde_data;
        this.formaldehyde_tips = formaldehyde_tips;
        this.formaldehydeId = formaldehydeId;
        this.dataId = dataId;
        this.formaldehyde_date = formaldehyde_date;
        this.formaldehyde = formaldehyde;
    }

    public String getFormaldehyde_data() {
        return formaldehyde_data;
    }

    public void setFormaldehyde_data(String formaldehyde_data) {
        formaldehyde_data = formaldehyde_data;
    }

    public String getFormaldehyde_tips() {
        return formaldehyde_tips;
    }

    public void setFormaldehyde_tips(String formaldehyde_tips) {
        formaldehyde_tips = formaldehyde_tips;
    }

    public String getFormaldehydeId() {
        return formaldehydeId;
    }

    public void setFormaldehydeId(String formaldehydeId) {
        formaldehydeId = formaldehydeId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        dataId = dataId;
    }

    public String getFormaldehyde_date() {
        return formaldehyde_date;
    }

    public void setFormaldehyde_date(String formaldehyde_date) {
        formaldehyde_date = formaldehyde_date;
    }

    public String getFormaldehyde() {
        return formaldehyde;
    }

    public void setFormaldehyde(String formaldehyde) {
        formaldehyde = formaldehyde;
    }
}

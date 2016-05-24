package com.moral.airtree.model;

/**
 * Created by bin.shen on 5/24/16.
 */
import java.io.Serializable;

public class MonitorFormaldehyde implements Serializable {
    private String create_date;
    private String formaldehydeId;
    private Long formaldehyde_data;

    public MonitorFormaldehyde(Long formaldehyde_data, String create_date, String formaldehydeId) {
        this.formaldehyde_data = formaldehyde_data;
        this.create_date = create_date;
        this.formaldehydeId = formaldehydeId;
    }

    public Long getFormaldehyde_data() {
        return formaldehyde_data;
    }

    public void setFormaldehyde_data(Long formaldehyde_data) {
        formaldehyde_data = formaldehyde_data;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        create_date = create_date;
    }

    public String getFormaldehydeId() {
        return formaldehydeId;
    }

    public void setFormaldehydeId(String formaldehydeId) {
        formaldehydeId = formaldehydeId;
    }
}
package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaResponse {

    @SerializedName("areaId")
    @Expose
    private String areaId;
    @SerializedName("areaName")
    @Expose
    private String areaName;
    @SerializedName("areaStatus")
    @Expose
    private String areaStatus;


    //All Getter and Setter

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }
}

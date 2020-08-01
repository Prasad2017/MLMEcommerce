package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateResponse {

    @SerializedName("stateId")
    @Expose
    private String stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("stateStatus")
    @Expose
    private String stateStatus;


    //All Getter and Setter

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
    }
}

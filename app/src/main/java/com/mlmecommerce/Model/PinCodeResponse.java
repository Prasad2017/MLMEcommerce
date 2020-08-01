package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinCodeResponse {


    @SerializedName("pincodeId")
    @Expose
    private String pincodeId;
    @SerializedName("pincodeName")
    @Expose
    private String pincodeName;
    @SerializedName("pincodeStatus")
    @Expose
    private String pincodeStatus;


    //All Getter and Setter


    public String getPincodeId() {
        return pincodeId;
    }

    public void setPincodeId(String pincodeId) {
        this.pincodeId = pincodeId;
    }

    public String getPincodeName() {
        return pincodeName;
    }

    public void setPincodeName(String pincodeName) {
        this.pincodeName = pincodeName;
    }

    public String getPincodeStatus() {
        return pincodeStatus;
    }

    public void setPincodeStatus(String pincodeStatus) {
        this.pincodeStatus = pincodeStatus;
    }
}

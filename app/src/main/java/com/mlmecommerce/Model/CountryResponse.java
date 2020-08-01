package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {

    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("countryStatus")
    @Expose
    private String countryStatus;


    //All getter and Setter Method

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryStatus() {
        return countryStatus;
    }

    public void setCountryStatus(String countryStatus) {
        this.countryStatus = countryStatus;
    }
}

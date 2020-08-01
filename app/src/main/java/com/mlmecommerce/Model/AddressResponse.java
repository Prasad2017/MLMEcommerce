package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressResponse {

    @SerializedName("customerAddressId")
    @Expose
    private String customerAddressId;
    @SerializedName("customerAddressType")
    @Expose
    private String customerAddressType;
    @SerializedName("customerAddressFullAddress")
    @Expose
    private String customerAddressFullAddress;
    @SerializedName("customerAddressStatus")
    @Expose
    private String customerAddressStatus;
    @SerializedName("pincodeId")
    @Expose
    private String pincodeId;
    @SerializedName("pincodeName")
    @Expose
    private String pincodeName;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("cityName")
    @Expose
    private String cityName;
    @SerializedName("stateId")
    @Expose
    private String stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("areaId")
    @Expose
    private String areaId;
    @SerializedName("areaName")
    @Expose
    private String areaName;
    @SerializedName("customerAddressLandmark")
    @Expose
    private String customerAddressLandmark;
    @SerializedName("customerAddressHouseNo")
    @Expose
    private String customerAddressHouseNo;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("buildingName")
    @Expose
    private String buildingName;


    private boolean isSelected;


    //All getter and Setter


    public String getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(String customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public String getCustomerAddressType() {
        return customerAddressType;
    }

    public void setCustomerAddressType(String customerAddressType) {
        this.customerAddressType = customerAddressType;
    }

    public String getCustomerAddressFullAddress() {
        return customerAddressFullAddress;
    }

    public void setCustomerAddressFullAddress(String customerAddressFullAddress) {
        this.customerAddressFullAddress = customerAddressFullAddress;
    }

    public String getCustomerAddressStatus() {
        return customerAddressStatus;
    }

    public void setCustomerAddressStatus(String customerAddressStatus) {
        this.customerAddressStatus = customerAddressStatus;
    }

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

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

    public String getCustomerAddressLandmark() {
        return customerAddressLandmark;
    }

    public void setCustomerAddressLandmark(String customerAddressLandmark) {
        this.customerAddressLandmark = customerAddressLandmark;
    }

    public String getCustomerAddressHouseNo() {
        return customerAddressHouseNo;
    }

    public void setCustomerAddressHouseNo(String customerAddressHouseNo) {
        this.customerAddressHouseNo = customerAddressHouseNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }
}

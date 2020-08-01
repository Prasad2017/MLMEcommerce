package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SizeWiseProductResponse {

    @SerializedName("sizeWiseProductId")
    @Expose
    private String sizeWiseProductId;
    @SerializedName("sizeId")
    @Expose
    private String sizeId;
    @SerializedName("sizeName")
    @Expose
    private String sizeName;
    @SerializedName("unitId")
    @Expose
    private String unitId;
    @SerializedName("unitName")
    @Expose
    private String unitName;
    @SerializedName("sizeWiseProductAmount")
    @Expose
    private String sizeWiseProductAmount;
    @SerializedName("sizeWiseProductDiscountAmount")
    @Expose
    private String sizeWiseProductDiscountAmount;
    @SerializedName("sizeWiseProductDiscountPercentage")
    @Expose
    private String sizeWiseProductDiscountPercentage;
    @SerializedName("sizeWiseProductFinalAmount")
    @Expose
    private String sizeWiseProductFinalAmount;
    @SerializedName("sizeWiseProductStatus")
    @Expose
    private String sizeWiseProductStatus;


    //All Getter and Setter method

    public String getSizeWiseProductId() {
        return sizeWiseProductId;
    }

    public void setSizeWiseProductId(String sizeWiseProductId) {
        this.sizeWiseProductId = sizeWiseProductId;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getSizeWiseProductAmount() {
        return sizeWiseProductAmount;
    }

    public void setSizeWiseProductAmount(String sizeWiseProductAmount) {
        this.sizeWiseProductAmount = sizeWiseProductAmount;
    }

    public String getSizeWiseProductDiscountAmount() {
        return sizeWiseProductDiscountAmount;
    }

    public void setSizeWiseProductDiscountAmount(String sizeWiseProductDiscountAmount) {
        this.sizeWiseProductDiscountAmount = sizeWiseProductDiscountAmount;
    }

    public String getSizeWiseProductDiscountPercentage() {
        return sizeWiseProductDiscountPercentage;
    }

    public void setSizeWiseProductDiscountPercentage(String sizeWiseProductDiscountPercentage) {
        this.sizeWiseProductDiscountPercentage = sizeWiseProductDiscountPercentage;
    }

    public String getSizeWiseProductFinalAmount() {
        return sizeWiseProductFinalAmount;
    }

    public void setSizeWiseProductFinalAmount(String sizeWiseProductFinalAmount) {
        this.sizeWiseProductFinalAmount = sizeWiseProductFinalAmount;
    }

    public String getSizeWiseProductStatus() {
        return sizeWiseProductStatus;
    }

    public void setSizeWiseProductStatus(String sizeWiseProductStatus) {
        this.sizeWiseProductStatus = sizeWiseProductStatus;
    }
}

package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductImageResponse {

    @SerializedName("productImageId")
    @Expose
    private String productImageId;
    @SerializedName("productImagePath")
    @Expose
    private String productImagePath;
    @SerializedName("productImageStatus")
    @Expose
    private String productImageStatus;


    //All Getter an Setter Method

    public String getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(String productImageId) {
        this.productImageId = productImageId;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductImageStatus() {
        return productImageStatus;
    }

    public void setProductImageStatus(String productImageStatus) {
        this.productImageStatus = productImageStatus;
    }
}

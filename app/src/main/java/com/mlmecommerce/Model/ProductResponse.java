package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("subCategoryId")
    @Expose
    private String subCategoryId;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("productImageList")
    @Expose
    private List<ProductImageResponse> productImageResponseList = null;
    @SerializedName("sizeWiseProductList")
    @Expose
    private List<SizeWiseProductResponse> sizeWiseProductResponseList = null;
    @SerializedName("wishListStatus")
    @Expose
    private Boolean wishListStatus;
    @SerializedName("wishListId")
    @Expose
    private String wishListId;
    @SerializedName("sizeWiseProductId")
    @Expose
    private String sizeWiseProductId;



    //All Getter and Setter Method


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductImageResponse> getProductImageResponseList() {
        return productImageResponseList;
    }

    public void setProductImageResponseList(List<ProductImageResponse> productImageResponseList) {
        this.productImageResponseList = productImageResponseList;
    }

    public List<SizeWiseProductResponse> getSizeWiseProductResponseList() {
        return sizeWiseProductResponseList;
    }

    public void setSizeWiseProductResponseList(List<SizeWiseProductResponse> sizeWiseProductResponseList) {
        this.sizeWiseProductResponseList = sizeWiseProductResponseList;
    }

    public Boolean getWishListStatus() {
        return wishListStatus;
    }

    public void setWishListStatus(Boolean wishListStatus) {
        this.wishListStatus = wishListStatus;
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public String getSizeWiseProductId() {
        return sizeWiseProductId;
    }

    public void setSizeWiseProductId(String sizeWiseProductId) {
        this.sizeWiseProductId = sizeWiseProductId;
    }

}

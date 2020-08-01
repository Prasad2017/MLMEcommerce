package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryResponse {

    @SerializedName("subCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("subCategoryStatus")
    @Expose
    private String subCategoryStatus;
    @SerializedName("subCategoryImage")
    @Expose
    private String subCategoryImage;
    @SerializedName("categoryMaster")
    @Expose
    private CategoryResponse categoryResponse;

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryImage() {
        return subCategoryImage;
    }

    public void setSubCategoryImage(String subCategoryImage) {
        this.subCategoryImage = subCategoryImage;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryStatus() {
        return subCategoryStatus;
    }

    public void setSubCategoryStatus(String subCategoryStatus) {
        this.subCategoryStatus = subCategoryStatus;
    }

    public CategoryResponse getCategoryResponse() {
        return categoryResponse;
    }

    public void setCategoryResponse(CategoryResponse categoryResponse) {
        this.categoryResponse = categoryResponse;
    }
}

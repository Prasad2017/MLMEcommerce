package com.mlmecommerce.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterResponse {

    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("filterBrandList")
    @Expose
    private List<String> filterBrandList = null;
    @SerializedName("filterCategoryList")
    @Expose
    private List<String> filterCategoryList = null;
    @SerializedName("filterDiscount")
    @Expose
    private FilterDiscount filterDiscount;
    @SerializedName("filterPrice")
    @Expose
    private FilterPrice filterPrice;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<String> getFilterBrandList() {
        return filterBrandList;
    }

    public void setFilterBrandList(List<String> filterBrandList) {
        this.filterBrandList = filterBrandList;
    }

    public List<String> getFilterCategoryList() {
        return filterCategoryList;
    }

    public void setFilterCategoryList(List<String> filterCategoryList) {
        this.filterCategoryList = filterCategoryList;
    }

    public FilterDiscount getFilterDiscount() {
        return filterDiscount;
    }

    public void setFilterDiscount(FilterDiscount filterDiscount) {
        this.filterDiscount = filterDiscount;
    }

    public FilterPrice getFilterPrice() {
        return filterPrice;
    }

    public void setFilterPrice(FilterPrice filterPrice) {
        this.filterPrice = filterPrice;
    }
}


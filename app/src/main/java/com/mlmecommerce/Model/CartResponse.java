package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {

    @SerializedName("cartId")
    @Expose
    private String cartId;
    @SerializedName("customerId")
    @Expose
    private String customerId;
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
    @SerializedName("wishListStatus")
    @Expose
    private Boolean wishListStatus;
    @SerializedName("wishListId")
    @Expose
    private String wishListId;
    @SerializedName("cartAmount")
    @Expose
    private String cartAmount;
    @SerializedName("cartDiscountAmount")
    @Expose
    private String cartDiscountAmount;
    @SerializedName("cartDiscountPercentage")
    @Expose
    private String cartDiscountPercentage;
    @SerializedName("cartFinalAmount")
    @Expose
    private String cartFinalAmount;
    @SerializedName("cartQty")
    @Expose
    private String cartQty;
    @SerializedName("productImage")
    @Expose
    private ProductImageResponse productImageList;
    @SerializedName("sizeWiseProductId")
    @Expose
    private String sizeWiseProductId;
    @SerializedName("cartSizeWiseProductResDto")
    @Expose
    private SizeWiseProductResponse sizeWiseProductResponse;



    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public String getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(String cartAmount) {
        this.cartAmount = cartAmount;
    }

    public String getCartDiscountAmount() {
        return cartDiscountAmount;
    }

    public void setCartDiscountAmount(String cartDiscountAmount) {
        this.cartDiscountAmount = cartDiscountAmount;
    }

    public String getCartDiscountPercentage() {
        return cartDiscountPercentage;
    }

    public void setCartDiscountPercentage(String cartDiscountPercentage) {
        this.cartDiscountPercentage = cartDiscountPercentage;
    }

    public String getCartFinalAmount() {
        return cartFinalAmount;
    }

    public void setCartFinalAmount(String cartFinalAmount) {
        this.cartFinalAmount = cartFinalAmount;
    }

    public String getCartQty() {
        return cartQty;
    }

    public void setCartQty(String cartQty) {
        this.cartQty = cartQty;
    }

    public ProductImageResponse getProductImageList() {
        return productImageList;
    }

    public void setProductImageList(ProductImageResponse productImageList) {
        this.productImageList = productImageList;
    }

    public String getSizeWiseProductId() {
        return sizeWiseProductId;
    }

    public void setSizeWiseProductId(String sizeWiseProductId) {
        this.sizeWiseProductId = sizeWiseProductId;
    }

    public SizeWiseProductResponse getSizeWiseProductResponse() {
        return sizeWiseProductResponse;
    }

    public void setSizeWiseProductResponse(SizeWiseProductResponse sizeWiseProductResponse) {
        this.sizeWiseProductResponse = sizeWiseProductResponse;
    }
}


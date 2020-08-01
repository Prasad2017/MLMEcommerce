package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;

public class PostOrderResponse {

    @SerializedName("cartResDtoList")
    @Expose
    private List<CartResponse> cartResponseList = null;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("orderBillingAddress")
    @Expose
    private String orderBillingAddress;
    @SerializedName("orderCouponAmount")
    @Expose
    private String orderCouponAmount;
    @SerializedName("orderCouponCode")
    @Expose
    private String orderCouponCode;
    @SerializedName("orderDeliveryAddress")
    @Expose
    private String orderDeliveryAddress;
    @SerializedName("orderFinalAmount")
    @Expose
    private String orderFinalAmount;
    @SerializedName("orderPaymentMode")
    @Expose
    private String orderPaymentMode;
    @SerializedName("orderTotalCartAmount")
    @Expose
    private String orderTotalCartAmount;

    public List<CartResponse> getCartResponseList() {
        return cartResponseList;
    }

    public void setCartResponseList(List<CartResponse> cartResponseList) {
        this.cartResponseList = cartResponseList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderBillingAddress() {
        return orderBillingAddress;
    }

    public void setOrderBillingAddress(String orderBillingAddress) {
        this.orderBillingAddress = orderBillingAddress;
    }

    public String getOrderCouponAmount() {
        return orderCouponAmount;
    }

    public void setOrderCouponAmount(String orderCouponAmount) {
        this.orderCouponAmount = orderCouponAmount;
    }

    public String getOrderCouponCode() {
        return orderCouponCode;
    }

    public void setOrderCouponCode(String orderCouponCode) {
        this.orderCouponCode = orderCouponCode;
    }

    public String getOrderDeliveryAddress() {
        return orderDeliveryAddress;
    }

    public void setOrderDeliveryAddress(String orderDeliveryAddress) {
        this.orderDeliveryAddress = orderDeliveryAddress;
    }

    public String getOrderFinalAmount() {
        return orderFinalAmount;
    }

    public void setOrderFinalAmount(String orderFinalAmount) {
        this.orderFinalAmount = orderFinalAmount;
    }

    public String getOrderPaymentMode() {
        return orderPaymentMode;
    }

    public void setOrderPaymentMode(String orderPaymentMode) {
        this.orderPaymentMode = orderPaymentMode;
    }

    public String getOrderTotalCartAmount() {
        return orderTotalCartAmount;
    }

    public void setOrderTotalCartAmount(String orderTotalCartAmount) {
        this.orderTotalCartAmount = orderTotalCartAmount;
    }


}

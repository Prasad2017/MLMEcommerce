package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("customerFName")
    @Expose
    private String customerFName;
    @SerializedName("customerLName")
    @Expose
    private String customerLName;
    @SerializedName("customerMail")
    @Expose
    private String customerMail;
    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;
    @SerializedName("customerPassword")
    @Expose
    private String customerPassword;
    @SerializedName("customerOPT")
    @Expose
    private String customerOPT;
    @SerializedName("customerStatus")
    @Expose
    private String customerStatus;
    @SerializedName("customerPhoto")
    @Expose
    private String customerPhoto;
    @SerializedName("customerGender")
    @Expose
    private String customerGender;
    @SerializedName("customerDOB")
    @Expose
    private String customerDOB;
    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerLName() {
        return customerLName;
    }

    public void setCustomerLName(String customerLName) {
        this.customerLName = customerLName;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public String getCustomerMobileNo() {
        return customerMobileNo;
    }

    public void setCustomerMobileNo(String customerMobileNo) {
        this.customerMobileNo = customerMobileNo;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerOPT() {
        return customerOPT;
    }

    public void setCustomerOPT(String customerOPT) {
        this.customerOPT = customerOPT;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerPhoto() {
        return customerPhoto;
    }

    public void setCustomerPhoto(String customerPhoto) {
        this.customerPhoto = customerPhoto;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(String customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerResponse {
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("customerDOB")
    @Expose
    private String customerDOB;
    @SerializedName("customerFName")
    @Expose
    private String customerFName;
    @SerializedName("customerMName")
    @Expose
    private String customerMName;
    @SerializedName("customerLName")
    @Expose
    private String customerLName;
    @SerializedName("customerGender")
    @Expose
    private String customerGender;
    @SerializedName("customerMail")
    @Expose
    private String customerMail;
    @SerializedName("customerPhoto")
    @Expose
    private String customerPhoto;
    @SerializedName("customerMobileNo")
    @Expose
    private String customerMobileNo;
    @SerializedName("customerPassword")
    @Expose
    private String customerPassword;
    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("customerAddressStatus")
    @Expose
    private String customerAddressStatus;
    @SerializedName("customerAddressFullAddress")
    @Expose
    private String customerAddressFullAddress;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerDOB() {
        return customerDOB;
    }

    public void setCustomerDOB(String customerDOB) {
        this.customerDOB = customerDOB;
    }

    public String getCustomerFName() {
        return customerFName;
    }

    public void setCustomerFName(String customerFName) {
        this.customerFName = customerFName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getCustomerMName() {
        return customerMName;
    }

    public void setCustomerMName(String customerMName) {
        this.customerMName = customerMName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerAddressStatus() {
        return customerAddressStatus;
    }

    public void setCustomerAddressStatus(String customerAddressStatus) {
        this.customerAddressStatus = customerAddressStatus;
    }

    public String getCustomerAddressFullAddress() {
        return customerAddressFullAddress;
    }

    public void setCustomerAddressFullAddress(String customerAddressFullAddress) {
        this.customerAddressFullAddress = customerAddressFullAddress;
    }

    public String getCustomerPhoto() {
        return customerPhoto;
    }

    public void setCustomerPhoto(String customerPhoto) {
        this.customerPhoto = customerPhoto;
    }
}
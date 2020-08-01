package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletResponse {


    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("referAmount")
    @Expose
    private Integer referAmount;
    @SerializedName("bonusPoint")
    @Expose
    private Integer bonusPoint;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getReferAmount() {
        return referAmount;
    }

    public void setReferAmount(Integer referAmount) {
        this.referAmount = referAmount;
    }

    public Integer getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(Integer bonusPoint) {
        this.bonusPoint = bonusPoint;
    }
}

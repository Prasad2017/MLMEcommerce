package com.mlmecommerce.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterDiscount {

    @SerializedName("fromDisc")
    @Expose
    private String fromDisc;
    @SerializedName("toDisc")
    @Expose
    private String toDisc;


    public String getFromDisc() {
        return fromDisc;
    }

    public void setFromDisc(String fromDisc) {
        this.fromDisc = fromDisc;
    }

    public String getToDisc() {
        return toDisc;
    }

    public void setToDisc(String toDisc) {
        this.toDisc = toDisc;
    }

}

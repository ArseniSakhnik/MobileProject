package com.example.authorization.CouponService.Requests;

import com.google.gson.annotations.SerializedName;

public class CouponRequest {
    @SerializedName("targetX")
    public Double targetX;
    @SerializedName("targetY")
    public Double targetY;

    public CouponRequest(Double targetX, Double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }
}

package com.example.authorization.Services.Requests;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CouponCreatorRequest {

    @SerializedName("targetX")
    public Double targetX;
    @SerializedName("targetY")
    public Double targetY;
    @SerializedName("radius")
    public Double radius;
    @SerializedName("endOfCoupon")
    public Date endOfCoupon;
    @SerializedName("description")
    public String description;

    public CouponCreatorRequest(Double targetX, Double targetY, Double radius, Date endOfCoupon, String description) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.radius = radius;
        this.endOfCoupon = endOfCoupon;
        this.description = description;
    }
}

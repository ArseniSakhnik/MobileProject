package com.example.authorization.CouponCreatorService.Responses;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class CouponCreatorResponse {
    @SerializedName("id")
    public int id;
    @SerializedName("targetX")
    public Double targetX;
    @SerializedName("targetY")
    public Double targetY;
    @SerializedName("radius")
    public Double radius;
    @SerializedName("isActive")
    public boolean isActive;
    @SerializedName("endOfCoupon")
    public Date endOfCoupon;
    @SerializedName("description")
    private String description = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return "CouponCreatorResponse{" +
                "id=" + id +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                ", radius=" + radius +
                ", isActive=" + isActive +
                ", endOfCoupon=" + endOfCoupon +
                ", description='" + description + '\'' +
                '}';
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getDescription() {
        return new String(description.getBytes(StandardCharsets.UTF_8));
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

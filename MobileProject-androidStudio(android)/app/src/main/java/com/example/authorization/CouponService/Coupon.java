package com.example.authorization.CouponService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Coupon {

    @SerializedName("id")
    public int id;
    @SerializedName("couponUserUsername")
    public String couponUserUsername;
    @SerializedName("couponCreatorId")
    public int couponCreatorId;
    @SerializedName("isActive")
    public boolean isActive;
    @SerializedName("description")
    public String description;
    @SerializedName("endOfCoupon")
    public Date endOfCoupon;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponUserUsername='" + couponUserUsername + '\'' +
                ", couponCreatorId=" + couponCreatorId +
                ", isActive=" + isActive +
                ", description='" + getDescription() + '\'' +
                ", endOfCoupon=" + endOfCoupon +
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

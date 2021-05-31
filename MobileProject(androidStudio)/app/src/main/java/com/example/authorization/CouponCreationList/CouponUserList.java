package com.example.authorization.CouponCreationList;

import java.util.Date;

public class CouponUserList {
    int id;
    String description;
    String couponUserUsername;
    int couponCreatorId;
    boolean inActive;
    Date endOfCoupon;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCouponUserUsername() {
        return couponUserUsername;
    }

    public int getCouponCreatorId() {
        return couponCreatorId;
    }

    public boolean isInActive() {
        return inActive;
    }

    public Date getEndOfCoupon() {
        return endOfCoupon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCouponCreatorId(int couponCreatorId) {
        this.couponCreatorId = couponCreatorId;
    }

    public CouponUserList(int id, String description, String couponUserUsername, int couponCreatorId, boolean inActive, Date endOfCoupon) {
        this.id = id;
        this.description = description;
        this.couponUserUsername = couponUserUsername;
        this.couponCreatorId = couponCreatorId;
        this.inActive = inActive;
        this.endOfCoupon = endOfCoupon;
    }
}

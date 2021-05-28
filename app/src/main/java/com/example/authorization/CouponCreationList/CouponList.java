package com.example.authorization.CouponCreationList;

import java.util.Date;

public class CouponList {
    int id;
    String description;

    Double targetX;
    Double targetY;
    Double radius;
    Date endOfCoupon;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getTargetX() {
        return targetX;
    }

    public Double getTargetY() {
        return targetY;
    }

    public Double getRadius() {
        return radius;
    }

    public Date getEndOfCoupon() {
        return endOfCoupon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CouponList(int id, String description, Double targetX, Double targetY, Double radius, Date endOfCoupon) {
        this.id = id;
        this.description = description;
        this.targetX = targetX;
        this.targetY = targetY;
        this.radius = radius;
        this.endOfCoupon = endOfCoupon;
    }
}

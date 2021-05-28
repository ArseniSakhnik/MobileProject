package com.example.authorization.CouponCreatorService.Responses;

public class GetCouponListResponse {
    public int targetX;
    public int targetY;

    public GetCouponListResponse(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }
}

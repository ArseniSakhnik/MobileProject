package com.TestApiJava;


import ServerService.CouponCreatorService.CouponCreatorService;
import ServerService.Models.User;
import ServerService.UsersService.UsersService;


public class TestApiJava {
    public static void main(String[] args) {
        CouponCreatorService couponCreatorService = new CouponCreatorService();
        couponCreatorService.getCouponCreators(1, 100, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6InRlc3QiLCJyb2xlIjoiQ291bnRlcnBhcnR5IiwibmJmIjoxNjIxNzY1MTk1LCJleHAiOjE2MjIzNjk5OTUsImlhdCI6MTYyMTc2NTE5NX0.Rv8wY0jCknbepq5CKGiudhG1T7MLRQK5S6ai4G0PTWA");
//        UsersService usersService = new UsersService();
//        usersService.authenticate("admin", "admin");
    }
}

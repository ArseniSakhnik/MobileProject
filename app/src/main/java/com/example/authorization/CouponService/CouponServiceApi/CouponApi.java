package com.example.authorization.CouponService.CouponServiceApi;

import com.example.authorization.CouponService.Responses.CouponResponse;
import com.example.authorization.CouponService.Requests.CouponRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CouponApi {

    @POST("/Coupon/addCouponToUser/{couponId}")
    Call<List<CouponResponse>> addCouponToUser(@Path("couponId") int couponId, @Body CouponRequest couponRequest, @Header("Authorization") String authHeader);

    @GET("/Coupon/getUserCoupons")
    Call<List<CouponResponse>> getCouponToUser(@Header("Authorization") String authHeader);

    @DELETE("/Coupon/removeCouponFromUser/{couponId}")
    Call<List<CouponResponse>> deleteCouponToUser(@Path("couponId") int couponId, @Header("Authorization") String authHeader);
}

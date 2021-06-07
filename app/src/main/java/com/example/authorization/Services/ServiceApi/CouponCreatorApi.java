package com.example.authorization.Services.ServiceApi;

import com.example.authorization.Services.Requests.CouponCreatorRequest;
import com.example.authorization.Services.Requests.CouponRequest;
import com.example.authorization.Services.Responses.CouponCreatorResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CouponCreatorApi {

    @POST("/CouponCreator/getCouponCreatorListByIdAndSearch/{count}")
    Call<List<CouponCreatorResponse>> getCouponCreatorsBySearch(@Path("count") int count, @Query("search") String search, @Body CouponRequest couponRequest, @Header("Authorization") String authHeader);

    @POST("/CouponCreator/getCouponListByCount/{startId}/{count}")
    Call<List<CouponCreatorResponse>> getCouponCreators(@Path("startId") int startId, @Path("count") int count, @Body CouponRequest couponRequest, @Header("Authorization") String authHeader);

    @DELETE("/CouponCreator/RemoveCouponCreator/{id}")
    Call<List<CouponCreatorResponse>> deleteCouponCreators(@Path("id") int id, @Header("Authorization") String authHeader);

    @PUT("/CouponCreator/changeCouponCreator/{id}")
    Call<CouponCreatorResponse> changeCouponCreators(@Path("id") int id, @Body CouponCreatorRequest changeCouponCreatorRequest, @Header("Authorization") String authHeader);

    @POST("/CouponCreator/addCouponCreator")
    Call<CouponCreatorResponse> addCouponCreators(@Body CouponCreatorRequest changeCouponCreatorRequest, @Header("Authorization") String authHeader);
}

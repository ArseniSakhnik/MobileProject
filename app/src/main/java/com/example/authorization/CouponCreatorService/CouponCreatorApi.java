package com.example.authorization.CouponCreatorService;

import com.example.authorization.CouponCreatorService.Request.CouponCreatorRequest;
import com.example.authorization.CouponCreatorService.Responses.CouponCreatorResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CouponCreatorApi {

    @GET("/CouponCreator/getCouponCreatorListByIdAndSearch/{count}")
    Call<List<CouponCreatorResponse>> getCouponCreatorsBySearch(@Path("count") int count, @Query("search") String search, @Header("Authorization") String authHeader);

    @GET("/CouponCreator/getCouponListByCount/{startId}/{count}")
    Call<List<CouponCreatorResponse>> getCouponCreators(@Path("startId") int startId, @Path("count") int count, @Header("Authorization") String authHeader);

    @DELETE("/CouponCreator/RemoveCouponCreator/{id}")
    Call<List<CouponCreatorResponse>> deleteCouponCreators(@Path("id") int id, @Header("Authorization") String authHeader);

    @PUT("/CouponCreator/changeCouponCreator/{id}")
    Call<CouponCreatorResponse> changeCouponCreators(@Path("id") int id, @Body CouponCreatorRequest changeCouponCreatorRequest, @Header("Authorization") String authHeader);

    @POST("/CouponCreator/addCouponCreator")
    Call<CouponCreatorResponse> addCouponCreators(@Body CouponCreatorRequest changeCouponCreatorRequest, @Header("Authorization") String authHeader);



}

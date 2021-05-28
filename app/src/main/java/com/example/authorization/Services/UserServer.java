package com.example.authorization.Services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServer {
    @POST("users/authenticate")
    Call<AuthenticateResponse> authentication(@Body AuthenticateRequest request);
    @POST("/users/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}

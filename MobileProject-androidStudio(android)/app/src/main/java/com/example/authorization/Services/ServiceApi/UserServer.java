package com.example.authorization.Services.ServiceApi;

import com.example.authorization.Services.Requests.AuthenticateRequest;
import com.example.authorization.Services.Requests.RegisterRequest;
import com.example.authorization.Services.Responses.AuthenticateResponse;
import com.example.authorization.Services.Responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserServer {
    @POST("users/authenticate")
    Call<AuthenticateResponse> authentication(@Body AuthenticateRequest request);
    @POST("/users/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}

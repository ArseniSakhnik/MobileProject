package com.example.authorization.Services;

import com.example.authorization.Services.Requests.AuthenticateRequest;
import com.example.authorization.Services.Requests.RegisterRequest;
import com.example.authorization.Services.Responses.AuthenticateResponse;
import com.example.authorization.Services.Responses.RegisterResponse;
import com.example.authorization.Services.ServiceApi.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService extends Service {
    private UserApi service;
    public String username;
    public String role;
    public int check;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserService()
    {
        super();
        this.service = retrofit.create(UserApi.class);
    }

    public void authenticate(String username, String password) {

        Call<AuthenticateResponse> call = this.server.authentication(new AuthenticateRequest(username, password));

        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                if (response.isSuccessful()) {
                    AuthenticateResponse authenticateResponse = response.body();
                    setUsername(authenticateResponse.token);
                    setRole(authenticateResponse.role);
                    setCheck(1);
                } else {
                    System.out.println("Сервер вернул ошибку");
                    setCheck(0);
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                System.out.println("Ошибка подключения к серверу");
            }
        });
    }

    public void register(String username, String firstName, String lastName, String password) {

        Call<RegisterResponse> call = this.server.register(new RegisterRequest(username, firstName, lastName, password));

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
            }
        });
    }
}

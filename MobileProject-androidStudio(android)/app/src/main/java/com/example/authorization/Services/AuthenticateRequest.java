package com.example.authorization.Services;

import com.google.gson.annotations.SerializedName;

public class AuthenticateRequest {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    public AuthenticateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

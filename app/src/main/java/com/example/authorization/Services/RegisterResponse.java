package com.example.authorization.Services;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("password")
    public String password;
    @SerializedName("role")
    public String role;
}

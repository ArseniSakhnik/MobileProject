package com.example.authorization.Services;
import com.google.gson.annotations.SerializedName;

public class AuthenticateResponse {
    @SerializedName("username")
    public String username;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("role")
    public String role;
    @SerializedName("token")
    public String token;
    @SerializedName("password")
    public String password;
}

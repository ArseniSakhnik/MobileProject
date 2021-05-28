package com.example.authorization.Services.Responses;

import com.google.gson.annotations.SerializedName;

public class TestResponse {
    @SerializedName("message")
    public String name;

    public String getName() {
        return name;
    }
}

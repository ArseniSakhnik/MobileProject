package com.example.authorization.Services;

import android.app.AppComponentFactory;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.R;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    protected Retrofit retrofit;
    protected UserServer server;

    public Service() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://localhost:44313")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        server = retrofit.create(UserServer.class);
        this.retrofit = retrofit;
    }

}

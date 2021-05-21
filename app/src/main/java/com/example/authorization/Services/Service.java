package com.example.authorization.Services;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.MainActivity;
import com.example.authorization.R;
import com.example.authorization.Registration;

import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service extends AppCompatActivity{
    private final static String FILE_NAME = "content.txt";
    private TextView tvTest;
    public String username;
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

    protected Retrofit retrofit;
    protected UserServer server;
    SyncResult syncResult = new SyncResult();

     public Service() {
         OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:5000/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         server = retrofit.create(UserServer.class);
         this.retrofit = retrofit;
     }


     public Service (TextView textView)
     {
         this.tvTest = textView;
     }

    public void authenticate(String username, String password) {

        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        server = retrofit.create(UserServer.class);
        this.retrofit = retrofit;

        Call<AuthenticateResponse> call = this.server.authentication(new AuthenticateRequest(username, password));

        call.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("Запрос удался");
                    AuthenticateResponse authenticateResponse = response.body();
                    System.out.println("Имя пользователя "
                            + authenticateResponse.username
                            + " Токен " + authenticateResponse.token);

                    setUsername(authenticateResponse.token);
                    setCheck(1);


                } else {
                    System.out.println("Сервер вернул ошибку");
                    //tvTest.setText("Сервер вернул ошибку");
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                System.out.println("Ошибка подключения к серверу");

            }
        });

    }

    public void test() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        server = retrofit.create(UserServer.class);
        this.retrofit = retrofit;


        Call<TestResponse> call = this.server.test();
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    syncResult.setResult("Запрос удался");
                    //tvTest.setText("Запрос удался");
                } else {
                    syncResult.setResult("Сервер вернул ошибку");
                    //tvTest.setText("Сервер вернул ошибку");
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                syncResult.setResult("Нет подключения к серверу");
                //tvTest.setText("Нет подключения к серверу");
            }
        });
    }

    public void register(String username, String firstName, String lastName, String password)
    {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        server = retrofit.create(UserServer.class);
        this.retrofit = retrofit;
        Call<RegisterResponse> call = this.server.register(new RegisterRequest(username,firstName, lastName, password));
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });



    }
}

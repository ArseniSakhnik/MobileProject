package com.example.authorization.Services;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.Services.Helpers.UnsafeOkHttpClient;
import com.example.authorization.Services.Requests.AuthenticateRequest;
import com.example.authorization.Services.Requests.RegisterRequest;
import com.example.authorization.Services.Responses.AuthenticateResponse;
import com.example.authorization.Services.Responses.RegisterResponse;
import com.example.authorization.Services.ServiceApi.UserServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public Retrofit retrofit;
    public UserServer server;
    //SyncResult syncResult = new SyncResult();

     public Service() {
         OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

         Gson gson = new GsonBuilder()
                 .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                 .create();

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:5000/")
                 .addConverterFactory(GsonConverterFactory.create(gson))
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
                    setCheck(0);
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                System.out.println("Ошибка подключения к серверу");

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

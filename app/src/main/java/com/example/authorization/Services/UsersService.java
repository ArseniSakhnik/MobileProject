package com.example.authorization.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.authorization.MainActivity;
import com.example.authorization.R;
import com.example.authorization.Registration;

public class UsersService extends Service {

    public UsersService() {
        super();
    }

    public void authenticate(String username, String password) {

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

                    //tvTest.setText("Имя пользователя: " + authenticateResponse.username + "Токен: " + authenticateResponse.token);
                    //Intent intent = new Intent("android.intent.action.Registration");
                    //Intent intent = new Intent(MainActivity.this, Registration.class);
                    //startActivity(intent);


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

        final SyncResult syncResult = new SyncResult();
        Call<TestResponse> call = this.server.test();
        syncResult.setResult("1");
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
}

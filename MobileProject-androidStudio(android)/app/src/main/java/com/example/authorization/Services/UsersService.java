package com.example.authorization.Services;

import com.example.authorization.Services.Requests.AuthenticateRequest;
import com.example.authorization.Services.Responses.AuthenticateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                } else {
                    System.out.println("Сервер вернул ошибку");
                }
            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                System.out.println("Ошибка подключения к серверу");

            }
        });
    }
}

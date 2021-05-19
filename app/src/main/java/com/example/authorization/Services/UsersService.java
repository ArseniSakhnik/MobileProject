package com.example.authorization.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersService extends Service {

    public UsersService() {
        super();
    }

    public boolean authenticate(String username, String password) {
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
        return true;
    }

    public String test() {
        final SyncResult syncResult = new SyncResult();
        Call<TestResponse> call = this.server.test();
        syncResult.setResult("1");
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    syncResult.setResult("Запрос удался");
                } else {
                    syncResult.setResult("Сервер вернул ошибку");
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                syncResult.setResult("Нет подключения к серверу");
            }
        });

        return syncResult.getResult();
    }

    //public String test() {
    //    final SyncResult syncResult = new SyncResult();
    //    final UserServer userServer = retrofit.create(UserServer.class);
    //    Call<TestResponse> call = userServer.test();
    //    call.enqueue(new Callback<TestResponse>() {
    //        @Override
    //        public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
//
    //        }
//
    //        @Override
    //        public void onFailure(Call<TestResponse> call, Throwable t) {
//
    //        }
    //    });
    //}

}

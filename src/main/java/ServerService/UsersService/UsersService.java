package ServerService.UsersService;

import ServerService.Models.User;
import ServerService.ServerConnector;
import ServerService.UsersService.Requests.AuthenticateRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersService extends ServerConnector {

    private String error = null;
    private User user;

    private UsersServiceApi usersServiceApi;

    public UsersService() {
        super();
        usersServiceApi = retrofit.create(UsersServiceApi.class);
    }

    public void authenticate(String username, String password) {
        Call<User> call = usersServiceApi.authenticate(new AuthenticateRequest(username, password));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    error = null;
                    user = response.body();
                    System.out.println(user);
                } else {
                    System.out.println("Сервер вернул ошибку");
                    error = null;
                    error = response.message();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Ошибка связи с сервером");
                error = "Не удалось связаться с сервером";
            }
        });
    }

    public void register(String username, String password) {
        Call<User> call = usersServiceApi.register(new AuthenticateRequest(username, password));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    System.out.println("Пользователь был зарегестрирован " + response.body().getUsername());
                } else {
                    System.out.println("Ошибка");
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
                    System.out.println(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}

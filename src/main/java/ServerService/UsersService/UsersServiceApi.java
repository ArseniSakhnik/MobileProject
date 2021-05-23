package ServerService.UsersService;

import ServerService.Models.User;
import ServerService.UsersService.Requests.AuthenticateRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersServiceApi {
    @POST("users/authenticate")
    Call<User> authenticate(@Body AuthenticateRequest request);
    @POST("users/register")
    Call<User> register(@Body AuthenticateRequest request);
}

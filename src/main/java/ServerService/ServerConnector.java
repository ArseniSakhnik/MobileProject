package ServerService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ServerConnector {

    public Retrofit retrofit;

    public ServerConnector() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}

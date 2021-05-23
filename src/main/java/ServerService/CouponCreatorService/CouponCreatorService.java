package ServerService.CouponCreatorService;

import ServerService.CouponCreatorService.Responses.GetCouponListResponse;
import ServerService.Models.CouponCreator;
import ServerService.ServerConnector;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CouponCreatorService extends ServerConnector {

    private CouponCreatorApi service;

    public CouponCreatorService() {
        super();
        this.service = retrofit.create(CouponCreatorApi.class);
    }

    public void getCouponCreators(int startId, int endId, String token) {
        String authHeader = "Bearer " + token;

        Call<List<CouponCreator>> call = service.getCouponCreators(startId, endId, authHeader);

        call.enqueue(new Callback<List<CouponCreator>>() {
            @Override
            public void onResponse(Call<List<CouponCreator>> call, Response<List<CouponCreator>> response) {
                if (response.isSuccessful()) {
                    List<CouponCreator> couponCreatorList = response.body();
                    for (CouponCreator a : couponCreatorList) {
                        System.out.println(a);
                    }
                } else {
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);
                    System.out.println(error.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<CouponCreator>> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getMessage());
            }
        });
    }
}

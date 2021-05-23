package ServerService.CouponCreatorService;

import ServerService.CouponCreatorService.Responses.GetCouponListResponse;
import ServerService.Models.CouponCreator;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CouponCreatorApi {
    @GET("/CouponCreator/getCouponList/{startId}/{endId}")
    Call<List<CouponCreator>> getCouponCreators(@Path("startId") int startId, @Path("endId") int endId, @Header("Authorization") String authHeader);
}

package com.example.authorization.CouponCreatorService;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.authorization.CouponCreationList.CouponCreatorAdapter;
import com.example.authorization.CouponCreationList.CouponList;
import com.example.authorization.CouponCreatorService.Request.CouponCreatorRequest;
import com.example.authorization.CouponCreatorService.Responses.CouponCreatorResponse;
import com.example.authorization.Error.Error;
import com.example.authorization.R;
import com.example.authorization.Services.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponCreatorService extends Service {
    private CouponCreatorApi service;

    public CouponCreatorService() {
        super();
        this.service = retrofit.create(CouponCreatorApi.class);
    }

    public void getCouponCreators(int startId, int count, String token, Context context, ListView couponList) {
        String authHeader = "Bearer " + token;
        Call<List<CouponCreatorResponse>> call = service.getCouponCreators(startId, count, authHeader);

        call.enqueue(new Callback<List<CouponCreatorResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<CouponCreatorResponse>> call, Response<List<CouponCreatorResponse>> response) {
                if (response.isSuccessful()) {
                    List<CouponCreatorResponse> couponCreatorList = response.body();

                    ArrayList<CouponList> coupons = new ArrayList();

                    if (count <= couponCreatorList.size()) {
                        for (int i = 0; i < count; i++) {
                            //Log.d("ABOBA", couponCreatorList.get(i).getDescription());
                            CouponList couponList = new CouponList(couponCreatorList.get(i).id, couponCreatorList.get(i).getDescription(), couponCreatorList.get(i).targetX,
                                    couponCreatorList.get(i).targetY, couponCreatorList.get(i).radius, couponCreatorList.get(i).endOfCoupon);
                            coupons.add(couponList);

                        }
                    }
                    else {
                        for (int i = 0; i < couponCreatorList.size(); i++) {
                            Log.d("ABOBA", couponCreatorList.get(i).getDescription());
                            CouponList couponList = new CouponList(couponCreatorList.get(i).id, couponCreatorList.get(i).getDescription(), couponCreatorList.get(i).targetX,
                                    couponCreatorList.get(i).targetY, couponCreatorList.get(i).radius, couponCreatorList.get(i).endOfCoupon);
                            coupons.add(couponList);
                        }
                    }

                    CouponCreatorAdapter couponCreatorAdapter = new CouponCreatorAdapter(context, R.layout.list_item, coupons);
                    couponList.setAdapter(couponCreatorAdapter);

                } else {
                    Log.d("Asp.net core", "Сервер вернул ошибку + " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CouponCreatorResponse>> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getCause());
            }
        });
    }

    public void deleteCouponCreators(int id, String token, Context context) {
        String authHeader = "Bearer " + token;

        Call<List<CouponCreatorResponse>> call = service.deleteCouponCreators(id, authHeader);

        call.enqueue(new Callback<List<CouponCreatorResponse>>() {
            @Override
            public void onResponse(Call<List<CouponCreatorResponse>> call, Response<List<CouponCreatorResponse>> response) {
                if (response.isSuccessful()) {
                } else {
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(error.getMessage())
                            .setCancelable(false)
                            .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Ошибка");
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<List<CouponCreatorResponse>> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getMessage());
            }
        });
    }

    public void changeCouponCreatorRequest (int id, String token, Double targetX, Double targetY, Double radius, Date endOfCoupon, String description,Context context)
    {
        String authHeader = "Bearer " + token;

        Call<CouponCreatorResponse> call = service.changeCouponCreators(id, new CouponCreatorRequest(targetX, targetY, radius, endOfCoupon, description), authHeader);

        call.enqueue(new Callback<CouponCreatorResponse>() {
            @Override
            public void onResponse(Call <CouponCreatorResponse> call, Response<CouponCreatorResponse> response) {
                if (response.isSuccessful()) {
                } else {
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(error.getMessage())
                            .setCancelable(false)
                            .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Ошибка");
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<CouponCreatorResponse> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getMessage());
            }
        });
    }

    public void addCouponCreators(String token, Double targetX, Double targetY, Double radius, Date endOfCoupon, String description,Context context)
    {
        String authHeader = "Bearer " + token;

        Call<CouponCreatorResponse> call = service.addCouponCreators(new CouponCreatorRequest(targetX, targetY, radius, endOfCoupon, description), authHeader);

        call.enqueue(new Callback<CouponCreatorResponse>() {
            @Override
            public void onResponse(Call<CouponCreatorResponse> call, Response<CouponCreatorResponse> response) {
                if (response.isSuccessful()) {
                } else {
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(error.getMessage())
                            .setCancelable(false)
                            .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Ошибка");
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<CouponCreatorResponse> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getMessage());
            }
        });
    }

    public void getCouponCreatorsBySearch (int count, String search, String token, Context context,ListView couponList)
    {
        String authHeader = "Bearer " + token;

        Call<List<CouponCreatorResponse>> call = service.getCouponCreatorsBySearch(count, search, authHeader);

        call.enqueue(new Callback<List<CouponCreatorResponse>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<CouponCreatorResponse>> call, Response<List<CouponCreatorResponse>> response) {
                if (response.isSuccessful()) {
                    List<CouponCreatorResponse> couponCreatorList = response.body();

                    ArrayList<CouponList> coupons = new ArrayList();

                    if (count <= couponCreatorList.size()) {
                        for (int i = 0; i < count; i++) {
                            //if (couponCreatorList.get(count).id)
                            //Log.d("ABOBA", couponCreatorList.get(i).getDescription());
                            CouponList couponList = new CouponList(couponCreatorList.get(i).id, couponCreatorList.get(i).getDescription(), couponCreatorList.get(i).targetX,
                                    couponCreatorList.get(i).targetY, couponCreatorList.get(i).radius, couponCreatorList.get(i).endOfCoupon);
                            coupons.add(couponList);

                        }
                    }
                    else {
                        for (int i = 0; i < couponCreatorList.size(); i++) {
                            Log.d("ABOBA", couponCreatorList.get(i).getDescription());
                            CouponList couponList = new CouponList(couponCreatorList.get(i).id, couponCreatorList.get(i).getDescription(), couponCreatorList.get(i).targetX,
                                    couponCreatorList.get(i).targetY, couponCreatorList.get(i).radius, couponCreatorList.get(i).endOfCoupon);
                            coupons.add(couponList);

                        }
                    }

                    CouponCreatorAdapter couponCreatorAdapter = new CouponCreatorAdapter(context, R.layout.list_item, coupons);
                    couponList.setAdapter(couponCreatorAdapter);

                } else {
                    Gson gson = new Gson();
                    Error error = gson.fromJson(response.errorBody().charStream(), Error.class);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(error.getMessage())
                            .setCancelable(false)
                            .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Ошибка");
                    alert.show();
                }
            }

            @Override
            public void onFailure(Call<List<CouponCreatorResponse>> call, Throwable t) {

            }
        });
    }

}

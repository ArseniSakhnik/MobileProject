package com.example.authorization.CouponService;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.authorization.CouponCreationList.CouponAdapter;
import com.example.authorization.CouponCreationList.CouponUserList;
import com.example.authorization.CouponService.Request.CouponRequest;
import com.example.authorization.Error.Error;
import com.example.authorization.R;
import com.example.authorization.Services.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponService extends Service {
    private CouponApi service;

    public CouponService()
    {
        super();
        this.service = retrofit.create(CouponApi.class);
    }

    public void addCouponToUser(int couponId, String token, double targetX, double targetY, Context context)
    {
        String authHeader = "Bearer " + token;

        Call<List<Coupon>> call = service.addCouponToUser(couponId, new CouponRequest(targetX,targetY), authHeader);

        call.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
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
            public void onFailure(Call<List<Coupon>> call, Throwable t) {

            }
        });

    }

    public void getCouponToUser (String token, Context context, int check, ListView couponUserList)
    {
        String authHeader = "Bearer " + token;

        Call<List<Coupon>> call = service.getCouponToUser(authHeader);

        call.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (response.isSuccessful()) {
                    List<Coupon> coupon = response.body();

                    ArrayList<CouponUserList> coupons = new ArrayList();

                    if (check <= coupon.size()) {
                        for (int i = 0; i < check; i++) {
                            CouponUserList couponUserList = new CouponUserList(coupon.get(i).id, coupon.get(i).description, coupon.get(i).couponUserUsername,
                                    coupon.get(i).couponCreatorId, coupon.get(i).isActive, coupon.get(i).endOfCoupon);
                            coupons.add(couponUserList);
                        }
                    }
                    else {
                        for (int i = 0; i < coupon.size(); i++) {
                            CouponUserList couponUserList = new CouponUserList(coupon.get(i).id, coupon.get(i).description, coupon.get(i).couponUserUsername,
                                    coupon.get(i).couponCreatorId, coupon.get(i).isActive, coupon.get(i).endOfCoupon);
                            coupons.add(couponUserList);
                        }
                    }

                    CouponAdapter couponAdapter = new CouponAdapter(context, R.layout.list_item, coupons);
                    couponUserList.setAdapter(couponAdapter);

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
            public void onFailure(Call<List<Coupon>> call, Throwable t) {

            }
        });


    }

    public void  deleteCouponToUser (String token, Context context, int id)
    {
        String authHeader = "Bearer " + token;

        Call<List<Coupon>> call = service.deleteCouponToUser(id,authHeader);

        call.enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
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
            public void onFailure(Call<List<Coupon>> call, Throwable t) {
                System.out.println("Ошибка связи с сервером " + t.getMessage());
            }
        });
    }
}
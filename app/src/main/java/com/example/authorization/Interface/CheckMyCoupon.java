package com.example.authorization.Interface;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.CouponCreationList.CouponUserList;
import com.example.authorization.R;
import com.example.authorization.Services.CouponService.CouponService;

public class CheckMyCoupon extends AppCompatActivity {

    private String token;
    private int check = 2;
    private Context context;

    ListView couponUserList;
    CouponService couponService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_coupon);

        this.context = this;

        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");

        couponUserList = (ListView) findViewById(R.id.couponList);
        this.couponUserList = couponUserList;

        CouponService couponService = new CouponService();
        this.couponService = couponService;

        couponService.getCouponToUser(token,this, check, couponUserList);

        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponUserList couponsUserList = (CouponUserList) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(CheckMyCoupon.this);
                builder.setMessage("Вы точно хотите удалить данный купон?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                check -= 1;
                                couponService.deleteCouponToUser(token, context, couponsUserList.getId());

                                try {
                                    Thread.sleep(1000); //Приостанавливает поток на 1 секунду
                                } catch (Exception e) {

                                }

                                couponService.getCouponToUser(token, context, check, couponUserList);
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Удаление купона");
                alert.show();
            }
        };
        couponUserList.setOnItemClickListener(itemListener);
    }

    public void couponAdd(View view) {
        check += 2;
        couponService.getCouponToUser(token,this, check, couponUserList);
    }

    public void exit(View view) {
        Intent intent = new Intent(CheckMyCoupon.this, ViewCouponCreator.class);
        startActivity(intent);
        this.finish();
    }
}
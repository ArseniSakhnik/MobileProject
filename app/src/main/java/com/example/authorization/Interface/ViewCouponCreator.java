package com.example.authorization.Interface;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.authorization.CouponCreationList.CouponList;
import com.example.authorization.Services.CouponCreatorService;
import com.example.authorization.R;
import com.example.authorization.Services.CouponService;
import com.example.authorization.checkGPS.LocListenerInterface;
import com.example.authorization.checkGPS.MyLocListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class ViewCouponCreator extends AppCompatActivity implements LocListenerInterface {

    private final static String FILE_NAME = "content.txt";
    private final static String FILE_ROLE = "role.txt";
    private String token;
    private String role;
    private double targetX, targetY;
    private int count = 2;

    ListView couponList;
    CouponCreatorService couponCreatorService;
    SearchView searchView;

    private LocationManager locationManager;
    private MyLocListener myLocListener;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        this.context = this;

        FileInputStream fos = null;
        try {
            fos = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fos.available()];
            fos.read(bytes);
            String text = new String(bytes);
            token = text.trim();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        fos = null;

        try {
            fos = openFileInput(FILE_ROLE);
            byte[] bytes = new byte[fos.available()];
            fos.read(bytes);
            String text = new String(bytes);
            role = text.trim();
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(this);
        checkPermissions();

        searchView = findViewById(R.id.search);
        couponList = (ListView) findViewById(R.id.couponList);

        CouponCreatorService couponCreatorService = new CouponCreatorService();
        this.couponCreatorService = couponCreatorService;

        couponCreatorService.getCouponCreators(1, count, token, this, couponList);

        menu(couponCreatorService);


        searchView(couponCreatorService);
    }

    private void searchView(CouponCreatorService couponCreatorService) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    couponCreatorService.getCouponCreators(1, count, token, ViewCouponCreator.this, couponList);
                } else {
                    couponCreatorService.getCouponCreatorsBySearch(count, newText, token, context, couponList);
                }
                return false;
            }
        });
    }

    private void menu(CouponCreatorService couponCreatorService) {
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponList couponsList = (CouponList) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewCouponCreator.this);
                final String[] buttons = {"Добавить себе купон", "Изменить", "Удалить"};
                builder.setTitle("Что вы хотите сделать с купоном")
                        .setItems(buttons, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (buttons[which].equals("Добавить себе купон")) {
                                    CouponService couponService = new CouponService();
                                    couponService.addCouponToUser(couponsList.getId(), token, targetX, targetY, ViewCouponCreator.this);
                                }
                                if (buttons[which].equals("Изменить")) {
                                    if (role.equals("Counterparty")) {
                                        Intent intent = new Intent(ViewCouponCreator.this, ChangeCouponCreator.class);
                                        intent.putExtra("idCoupon", couponsList.getId());
                                        intent.putExtra("targetX", couponsList.getTargetX());
                                        intent.putExtra("targetY", couponsList.getTargetY());
                                        intent.putExtra("radius", couponsList.getRadius());

                                        if (couponsList.getEndOfCoupon() != null) {
                                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                            intent.putExtra("endOfCoupon", format.format(couponsList.getEndOfCoupon()).toString());
                                        } else {
                                            intent.putExtra("endOfCoupon", "null");
                                        }

                                        intent.putExtra("description", couponsList.getDescription());
                                        intent.putExtra("token", token);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("У вас недостаточно прав!")
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
                                if (buttons[which].equals("Удалить")) {
                                    if (role.equals("Counterparty")) {
                                        couponCreatorService.deleteCouponCreators(couponsList.getId(), token);

                                        try {
                                            Thread.sleep(1000); //Приостанавливает поток на 1 секунду
                                        } catch (Exception e) {
                                        }

                                        count -= 1;
                                        couponCreatorService.getCouponCreators(1, count, token, ViewCouponCreator.this, couponList);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("У вас недостаточно прав!")
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
                            }
                        })
                        .setNegativeButton("Выйти", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Получение купона");
                alert.show();
            }
        };
        couponList.setOnItemClickListener(itemListener);
    }

    public void couponAdd(View view) {
        count += 2;
        couponCreatorService.getCouponCreators(1, count, token, this, couponList);
    }

    public void addCouponCreator(View view) {

        if (role.equals("Counterparty")) {
            Intent intent = new Intent(ViewCouponCreator.this, AddCouponCreator.class);
            intent.putExtra("token", token);
            startActivity(intent);
            this.finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("У вас недостаточно прав!")
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

    public void goToMyCoupons(View view) {
        Intent intent = new Intent(ViewCouponCreator.this, CheckMyCoupon.class);
        intent.putExtra("token", token);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onLocationChanged(Location loc) {
        targetX = loc.getLatitude();
        targetY = loc.getAltitude();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, myLocListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2, 1, myLocListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == RESULT_OK) {
            checkPermissions();
        }
    }

    public void goToAuthorisation(View view) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write("".getBytes());
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        Intent intent = new Intent(ViewCouponCreator.this, ViewAuthorization.class);
        startActivity(intent);
    }
}
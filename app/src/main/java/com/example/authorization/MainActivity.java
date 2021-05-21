 package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.authorization.Services.UsersService;
import com.example.authorization.Services.AuthenticateRequest;
import com.example.authorization.Services.AuthenticateResponse;
import com.example.authorization.Services.OnRequestFinishedListener;
import com.example.authorization.Services.Service;
import com.example.authorization.Services.SyncResult;
import com.example.authorization.Services.TestResponse;
import com.example.authorization.Services.UnsafeOkHttpClient;
import com.example.authorization.Services.UserServer;
import com.example.authorization.Services.UsersService;
import com.example.authorization.checkGPS.LocListenerInterface;
import com.example.authorization.checkGPS.MyLocListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class MainActivity extends AppCompatActivity implements LocListenerInterface {

     private final static String FILE_NAME = "content.txt";
     private LocationManager locationManager;
     private MyLocListener myLocListener;
     private TextView output, tvTest, aboba;
     private String outs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        tvTest = findViewById(R.id.tvTest);
        aboba = findViewById(R.id.aboba);
        init();
        //UsersService usersService = new UsersService(output);
        //usersService.authenticate("admin", "admin");
        Service service = new Service(output);
        service.authenticate("admin", "admin");
        aboba.setText(output.getText().toString().trim());
        //try {
        //    Thread.sleep(50000); //Приостанавливает поток на 1 секунду
        //} catch (Exception e) {
        //}

    }

    private  void init()
    {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(this);
        checkPermissions();
    }

    public void check(View view) {
        EditText receivingLogin = findViewById(R.id.login);
        EditText receivingPassword = findViewById(R.id.password);
        String login = receivingLogin.getText().toString().trim();
        String password = receivingPassword.getText().toString().trim();


        //// Сюда вводим логин
        //String checkLogin = "login";
        //// Сюда вводим пароль
        //String checkPassword = "password";
        //if(login.equals(checkLogin) && password.equals(checkPassword))
        //{
        //   textOutput();
        //}
        //else
        //{
        //    AlertDialog.Builder y_builder = new AlertDialog.Builder(MainActivity.this);
        //    y_builder.setMessage("Неправильный логин или пароль.")
        //            .setCancelable(false)
        //            .setNeutralButton("ОК", new DialogInterface.OnClickListener() {
        //                @Override
        //                public void onClick(DialogInterface dialog, int which) {
        //                    dialog.cancel();
        //                }
        //            });
        //    AlertDialog alert = y_builder.create();
        //    alert.show();
        //}
        //UsersService usersService = new UsersService(output);
        //usersService.authenticate("admin", "admin");
        //Service service = new Service(output,view,context);
        //try {
        //    Thread.sleep(1000); //Приостанавливает поток на 1 секунду
        //} catch (Exception e) {
//
        //}
        //service.authenticate("admin","admin");
    }

    // Отедльный метод для вывода текста в TextView, который называется output.
    public void textOutput ()
    {
        TextView output = findViewById(R.id.output);
        output.setText("Выводим что нибудь");
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(MainActivity.this,Registration.class);
        startActivity(intent);
    }

     @Override
     public void onLocationChanged(Location loc) {
        output.setText(String.valueOf(loc.getAltitude()));
     }

     private void checkPermissions()
     {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                 && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
         {
             requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                     100);
         }
         else
         {
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2,1, myLocListener);
             locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2,1, myLocListener);
         }
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == 100 && grantResults[0] == RESULT_OK)
         {
             checkPermissions();
         }
         //else
         //{
         //    Toast.makeText(this,"No GPS permissions", Toast.LENGTH_SHORT).show();
         //}
     }

 }
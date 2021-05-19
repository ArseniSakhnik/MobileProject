 package com.example.authorization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.EditText;
import android.widget.TextView;

//import com.example.authorization.Services.UsersService;
import com.example.authorization.Services.UsersService;
import com.example.authorization.checkGPS.LocListenerInterface;
import com.example.authorization.checkGPS.MyLocListener;

 public class MainActivity extends AppCompatActivity implements LocListenerInterface {

     private LocationManager locationManager;
     private MyLocListener myLocListener;
     private TextView output, tvTest;
     private String outs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        tvTest = findViewById(R.id.tvTest);
        init();

        UsersService usersService = new UsersService();
        usersService.authenticate("admin","admin");
        tvTest.setText(usersService.test());

        //UserService2 userService2 = new UserService2();
        //userService2.authenticate("admin", "admin");
        //tvTest.setText(String.valueOf(userService2.test()));

        //tvTest.setText(String.valueOf(usersService.test()));
        //outs = usersService.test();
        //tvTest.setText(usersService.test());
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
        // Сюда вводим логин
        String checkLogin = "login";
        // Сюда вводим пароль
        String checkPassword = "password";
        if(login.equals(checkLogin) && password.equals(checkPassword))
        {
           textOutput();
        }
        else
        {
            AlertDialog.Builder y_builder = new AlertDialog.Builder(MainActivity.this);
            y_builder.setMessage("Неправильный логин или пароль.")
                    .setCancelable(false)
                    .setNeutralButton("ОК", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = y_builder.create();
            alert.show();
        }
    }

    // Отедльный метод для вывода текста в TextView, который называется output.
    public void textOutput ()
    {
        TextView output = findViewById(R.id.output);
        output.setText("Выводим что нибудь");
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent("android.intent.action.Registration");
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
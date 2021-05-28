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

import com.example.authorization.Services.Service;
import com.example.authorization.checkGPS.LocListenerInterface;
import com.example.authorization.checkGPS.MyLocListener;

import java.io.FileOutputStream;
import java.io.IOException;

 public class MainActivity extends AppCompatActivity implements LocListenerInterface {

     private final static String FILE_NAME = "content.txt";
     private LocationManager locationManager;
     private MyLocListener myLocListener;
     private TextView output;
     private String loginCheck, passwordCheck;
     private Service service;


     private EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        init();

        Service service = new Service();
        this.service = service;

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus){
                    passwordCheck = password.getText().toString().trim();
                    loginCheck = login.getText().toString().trim();
                    service.authenticate(loginCheck,passwordCheck);
                }
            }
        });

    }

    private  void init()
    {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(this);
        checkPermissions();
    }

    public void check(View view) {
        if (service.getCheck() == 1)
        {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(service.getUsername().getBytes());
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            }
            catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    if(fos!=null)
                        fos.close();
                }
                catch(IOException ex){

                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        }
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
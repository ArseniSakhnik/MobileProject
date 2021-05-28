package com.example.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.Services.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ViewAuthorization extends AppCompatActivity {

    private final static String FILE_NAME = "content.txt";
    private String loginCheck, passwordCheck;
    private Service service;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    private EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        Service service = new Service();
        this.service = service;

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!login.getText().toString().trim().equals("")) {
                                passwordCheck = password.getText().toString().trim();
                                loginCheck = login.getText().toString().trim();
                                service.authenticate(loginCheck, passwordCheck);
                            }
                        }
                    }, DELAY);
                }
            }
        });

        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!password.getText().toString().trim().equals("")) {
                                passwordCheck = password.getText().toString().trim();
                                loginCheck = login.getText().toString().trim();
                                service.authenticate(loginCheck, passwordCheck);
                            }
                        }
                    }, DELAY);
                }
            }
        });


    }

    public void check(View view) {
        try {
            Thread.sleep(125); //Приостанавливает поток на 125 мс
        } catch (Exception e) {
        }

        if (service.getCheck() == 1) {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(service.getUsername().getBytes());
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
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

            Intent intent = new Intent(ViewAuthorization.this, ViewCouponCreator.class);
            startActivity(intent);
            this.finish();
            service.setCheck(0);
        } else {
            Toast.makeText(this, "Вы неправильно ввели логин или пароль", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(ViewAuthorization.this, Registration.class);
        startActivity(intent);
    }
}
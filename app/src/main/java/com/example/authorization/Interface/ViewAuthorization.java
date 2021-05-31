package com.example.authorization.Interface;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.R;
import com.example.authorization.Services.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ViewAuthorization extends AppCompatActivity {

    private final static String FILE_NAME = "content.txt";
    private final static String FILE_ROLE = "role.txt";
    private String loginCheck, passwordCheck;
    private UserService userService;

    private Timer timer = new Timer();
    private final long DELAY = 600; // in ms

    private EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        UserService userService = new UserService();
        this.userService = userService;

        checkLoginAndPassword(userService);
    }

    private void checkLoginAndPassword(UserService userService) {
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
                                userService.authenticate(loginCheck, passwordCheck);
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
                                userService.authenticate(loginCheck, passwordCheck);
                            }
                        }
                    }, DELAY);
                }
            }
        });
    }

    public void check(View view) {
        try {
            Thread.sleep(700); //Приостанавливает поток на 700 мс
        } catch (Exception e) {
        }

        if (userService.getCheck() == 1) {
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(userService.getUsername().getBytes());
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
                fos = openFileOutput(FILE_ROLE, MODE_PRIVATE);
                fos.write(userService.getRole().getBytes());
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
            userService.setCheck(0);
        } else {
            Toast.makeText(this, "Вы неправильно ввели логин или пароль", Toast.LENGTH_SHORT).show();
        }

    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(ViewAuthorization.this, Registration.class);
        startActivity(intent);
    }
}
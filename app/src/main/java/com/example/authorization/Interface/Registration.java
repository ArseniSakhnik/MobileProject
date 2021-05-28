package com.example.authorization.Interface;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.R;
import com.example.authorization.Services.Service;

import java.util.Timer;
import java.util.TimerTask;

public class Registration extends AppCompatActivity {

    private EditText tvFirstName;
    private EditText tvLastName;
    private EditText oneLogin;
    private EditText onePassword;
    private EditText twoPassword;

    private Timer timer = new Timer();
    private final long DELAY = 2000; // in ms

    private String onePass, twoPass, firstName, lastName, login;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        oneLogin = findViewById(R.id.oneLogin);
        onePassword = findViewById(R.id.onePassword);
        twoPassword = findViewById(R.id.twoPassword);

        context = Registration.this;

        twoPassword.addTextChangedListener(new TextWatcher() {
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
                if (s.length() >= 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            onePass = onePassword.getText().toString().trim();
                            twoPass = twoPassword.getText().toString().trim();

                            if (!onePass.equals(twoPass)) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        twoPassword.setTextColor(Color.rgb(255, 0, 0));
                                    }
                                });

                                Looper.prepare();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Пароли не совпадают.")
                                        .setCancelable(false)
                                        .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                }
                                        );
                                AlertDialog alert = builder.create();
                                alert.setTitle("Ошибка регистрации");
                                alert.show();
                                Looper.loop();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        twoPassword.setTextColor(Color.rgb(154, 154, 154));
                                    }
                                });
                            }
                        }
                    }, DELAY);

                }
            }
        });

        onePassword.addTextChangedListener(new TextWatcher() {
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
                if (s.length() >= 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            onePass = onePassword.getText().toString().trim();
                            twoPass = twoPassword.getText().toString().trim();

                            if (!twoPassword.equals(onePass) && (!twoPass.equals(""))) {
                                Looper.prepare();
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Пароли не совпадают.")
                                        .setCancelable(false)
                                        .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                }
                                        );
                                AlertDialog alert = builder.create();
                                alert.setTitle("Ошибка регистрации");
                                alert.show();
                                Looper.loop();
                            }
                        }
                    }, DELAY);

                }
            }
        });

    }

    public void Registration(View view) {
        onePass = onePassword.getText().toString().trim();
        twoPass = twoPassword.getText().toString().trim();
        firstName = tvFirstName.getText().toString().trim();
        lastName = tvLastName.getText().toString().trim();
        login = oneLogin.getText().toString().trim();

        if ((onePass.equals("") || twoPass.equals("") || firstName.equals("") || lastName.equals("") || login.equals("")) || (!onePass.equals(twoPass))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Вы ввели не все данные или пароли не совпадают.")
                    .setCancelable(false)
                    .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.setTitle("Ошибка регистрации");
            alert.show();
        } else {
            Service service = new Service();
            service.register(login, firstName, lastName, onePass);
            Toast.makeText(context, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Registration.this, ViewAuthorization.class);
            startActivity(intent);
            this.finish();
        }
    }

}
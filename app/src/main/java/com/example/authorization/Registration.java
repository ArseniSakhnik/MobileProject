package com.example.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authorization.Services.Service;

public class Registration extends AppCompatActivity {

    private EditText tvFirstName;
    private EditText tvLastName;
    private EditText oneLogin;
    private EditText onePassword;
    private EditText twoPassword;

    private String onePass, twoPass, firstName, lastName, login;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        oneLogin = findViewById(R.id.oneLogin);
        onePassword = findViewById(R.id.onePassword);
        twoPassword = findViewById(R.id.twoPassword);

        //context.getApplicationContext();

        twoPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    onePass = onePassword.getText().toString().trim();
                    twoPass = twoPassword.getText().toString().trim();
                    if (onePass.equals(twoPass))
                    {
                    }
                    else
                    {
                        onePassword.setText("");
                        twoPassword.setText("");
                        //Toast.makeText(context, "Пароли не правильные", Toast.LENGTH_SHORT).show();
                    }
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

        if ( onePass.equals("") || twoPass.equals("") || firstName.equals("") || lastName.equals("") || login.equals(""))
        {
            //Toast.makeText(context, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Service service = new Service();
            service.register(login,firstName,lastName,onePass);
            Intent intent = new Intent(Registration.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
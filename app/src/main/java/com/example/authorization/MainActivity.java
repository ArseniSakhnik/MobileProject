package com.example.authorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}
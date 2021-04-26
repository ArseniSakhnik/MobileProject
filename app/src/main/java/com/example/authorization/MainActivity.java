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
        EditText editText1 = findViewById(R.id.login);
        EditText editText2 = findViewById(R.id.password);
        TextView textView = findViewById(R.id.em);
        String prov = editText1.getText().toString().trim();
        String prov2 = editText2.getText().toString().trim();
        String logCheck = "login";
        String pasCheck = "1q";
        if(prov.equals(logCheck) && prov2.equals(pasCheck))
        {
            textView.setText("Вход");
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
            textView.setText(prov);
        }

    }

}
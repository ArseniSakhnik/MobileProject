package com.example.authorization.Interface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authorization.Services.CouponCreatorService;
import com.example.authorization.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCouponCreator extends AppCompatActivity {

    private Double targetX, targetY, range;
    private Date date;
    public String description, token, moveTime;

    private EditText placeX;
    private EditText placeY;
    private EditText radius;
    private EditText text;

    private CheckBox setPlaceNull;
    private CheckBox setDateNull;

    private TextView calendar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    public static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon_creator);

        placeX = findViewById(R.id.placeX);
        placeY = findViewById(R.id.placeY);
        radius = findViewById(R.id.radius);
        text = findViewById(R.id.description);

        setPlaceNull = findViewById(R.id.setPlaceNull);
        setDateNull = findViewById(R.id.setDateNull);

        calendar = findViewById(R.id.time);

        Bundle arguments = getIntent().getExtras();
        token = arguments.getString("token");

        setDateTime();

        checkBoxEvents();
    }

    private void setDateTime() {
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setDateNull.isChecked()) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(AddCouponCreator.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth, onDateSetListener, year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                calendar.setText(date);
            }
        };
    }

    private void checkBoxEvents() {
        setPlaceNull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    placeX.setRawInputType(0x00000000);
                    placeY.setRawInputType(0x00000000);
                    placeX.setTextColor(Color.rgb(28, 28, 28));
                    placeY.setTextColor(Color.rgb(28, 28, 28));
                    targetX = null;
                    targetY = null;
                    radius.setRawInputType(0x00000000);
                    radius.setTextColor(Color.rgb(28, 28, 28));
                    range = null;
                } else {
                    placeX.setInputType(InputType.TYPE_CLASS_NUMBER);
                    placeY.setInputType(InputType.TYPE_CLASS_NUMBER);
                    placeX.setTextColor(Color.rgb(154, 154, 154));
                    placeY.setTextColor(Color.rgb(154, 154, 154));
                    targetX = Double.parseDouble(placeX.getText().toString().trim());
                    targetY = Double.parseDouble(placeY.getText().toString().trim());
                    radius.setInputType(InputType.TYPE_CLASS_NUMBER);
                    radius.setTextColor(Color.rgb(154, 154, 154));
                    range = Double.parseDouble(radius.getText().toString().trim());
                }
            }
        });

        setDateNull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    calendar.setTextColor(Color.rgb(28, 28, 28));
                } else {
                    calendar.setTextColor(Color.rgb(154, 154, 154));
                }
            }
        });
    }

    public void exit(View view) {
        Intent intent = new Intent(AddCouponCreator.this, ViewCouponCreator.class);
        startActivity(intent);
    }

    public void addCoupon(View view) throws ParseException {
        if (!text.getText().toString().trim().equals("")) {
            if ((placeX.getText().toString().trim().equals("") && !setPlaceNull.isChecked()) || (placeY.getText().toString().trim().equals("") && !setPlaceNull.isChecked())
                    || (radius.getText().toString().trim().equals("") && !setPlaceNull.isChecked()) || (calendar.getText().toString().trim().equals("null") && !setDateNull.isChecked())) {
                Toast.makeText(this, "???? ?????? ???????????? ???????? ??????????????", Toast.LENGTH_SHORT).show();
            } else {
                if (!setPlaceNull.isChecked()) {
                    targetX = Double.parseDouble(placeX.getText().toString().trim());
                    targetY = Double.parseDouble(placeY.getText().toString().trim());
                    range = Double.parseDouble(radius.getText().toString().trim());
                }

                if (!setDateNull.isChecked()) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    moveTime = calendar.getText().toString().trim();
                    date = format.parse(moveTime);
                } else {
                    date = null;
                }

                description = text.getText().toString().trim();

                CouponCreatorService couponCreatorService = new CouponCreatorService();
                couponCreatorService.addCouponCreators(token, targetX, targetY, range, date, description);

                Intent intent = new Intent(AddCouponCreator.this, ViewCouponCreator.class);
                startActivity(intent);
                this.finish();
            }
        } else {
            Toast.makeText(this, "???? ???????????????? ??????????????????????", Toast.LENGTH_SHORT).show();
        }
    }
}
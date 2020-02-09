package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {


    CalendarView calendarView;
    TextView mydate;
    Button Confirm_app;
    public static String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        calendarView = (CalendarView) findViewById(R.id.calendarView);
        mydate = (TextView)findViewById(R.id.mydate);
        Confirm_app = (Button)findViewById(R.id.confirm_app);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (month+1) + "/"+ dayOfMonth + "/" + year;
                mydate.setText(date);
            }
        });

        Confirm_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intopayment = new Intent(AppointmentActivity.this,paymentActivity.class);
                startActivity(intopayment);
            }
        });


    }
}

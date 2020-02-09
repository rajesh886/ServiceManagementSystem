package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DateChange extends AppCompatActivity {

    TextView newdate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Button change;

    public static String changeddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_change);

        newdate = (TextView)findViewById(R.id.newdate);
        change = (Button) findViewById(R.id.change);

        newdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DateChange.this,
                        android.R.style.Theme_Holo_Light_Dialog,mDateSetListener
                         ,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;

                changeddate = month + "/" + dayOfMonth + "/" + year;

                newdate.setText(changeddate);

            }
        };

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointments appointments = new Appointments(
                        AppointmentViewHolder.sprovidername,
                        AppointmentViewHolder.sclientemail,
                        changeddate,AppointmentViewHolder.stotalpaid,
                        AppointmentViewHolder.sservicename,
                        AppointmentViewHolder.sclientp
                );

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                        .getReference();

                databaseReference.child("Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .orderByChild("date").equalTo(AppointmentViewHolder.sdate)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Toast.makeText(getApplicationContext(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                        databaseReference.child("Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(dataSnapshot.getKey()).child("date").setValue(changeddate);
                        Toast.makeText(getApplicationContext(),"Appointment changed successfully.",Toast.LENGTH_SHORT).show();
                        Intent intohome = new Intent(DateChange.this,ClientHomeService.class);
                        startActivity(intohome);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}

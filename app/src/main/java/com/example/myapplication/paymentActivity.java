package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class paymentActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference ref;
    DatabaseReference databaseReference;
    FirebaseDatabase database;

    public static String paytype;
    Spinner spinner3;
    Button pay;

    public static final int PAYPAL_REQUEST_CODE = 9999;

    static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        databaseReference=database.getReference();

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        pay = (Button)findViewById(R.id.pay);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(adapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paytype = spinner3.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(paytype.equals("Cash or Cheque")){
                    Toast.makeText(getApplicationContext(),"You will pay on service date.",Toast.LENGTH_SHORT).show();
                    final String userinfo=mAuth.getCurrentUser().getUid();
                    //Toast.makeText(getApplicationContext(),userinfo,Toast.LENGTH_SHORT).show();
                    ref = ref.child("Customers").child(userinfo);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String email = dataSnapshot.child("email").getValue().toString();
                            //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
                            Appointments appointments = new Appointments(BidViewHolder.vname,email,AppointmentActivity.date,BidViewHolder.totalbid,BidViewHolder.vservice,BidViewHolder.vproblem);
                            databaseReference.child("Appointments").child(userinfo).push().setValue(appointments);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent intoservice = new Intent(paymentActivity.this,ClientHomeService.class);
                    startActivity(intoservice);
                }
                else{
                    processPayment();
                }
            }
        });


    }

    private void processPayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(BidViewHolder.totalbid),"USD","SMS APP",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent2 = new Intent(getApplicationContext(), PaymentActivity.class);
        intent2.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent2.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent2,PAYPAL_REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try{
                        String paymentdetails = confirmation.toJSONObject().toString(4);
                        final String userinfo=mAuth.getCurrentUser().getUid();
                        Toast.makeText(getApplicationContext(),userinfo,Toast.LENGTH_SHORT).show();
                        ref = ref.child("Customers").child(userinfo);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String email = dataSnapshot.child("email").getValue().toString();
                                Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
                                Appointments appointments = new Appointments(BidViewHolder.vname,email,AppointmentActivity.date,BidViewHolder.totalbid,BidViewHolder.vservice,BidViewHolder.vproblem);
                                databaseReference.child("Appointments").child(userinfo).push().setValue(appointments);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(this,"Your payment is confirmed and appointment has been confirmed.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ClientHomeService.class));
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }


                }
            }
            else if(resultCode== Activity.RESULT_CANCELED){
                Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show();
            }
        }

        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
        }
    }
}

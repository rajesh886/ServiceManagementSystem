package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BidRequest extends AppCompatActivity {

    TextView biddisplay,problemname;
    EditText amount,days;
    Button bid;
    double damount1;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_request);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Bids");

        biddisplay = (TextView)findViewById(R.id.biddisplay);
        problemname = (TextView)findViewById(R.id.problemname);
        amount = (EditText)findViewById(R.id.amount);
        days = (EditText)findViewById(R.id.days);
        bid = (Button)findViewById(R.id.bid);

        problemname.setText(RequestViewHolder.probleminfo);


        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String amount1 = amount.getText().toString();
                damount1 = Double.parseDouble(amount1) + 0.2 * Double.parseDouble(amount1);
                biddisplay.setText("Final bid amount: "+damount1+"$ (with 20% fee).");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String amount2 = Double.toString(damount1);
                final String daysf = days.getText().toString();
                if(amount.equals("")||days.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    String vendor_id = mAuth.getCurrentUser().getUid();


                    String user_id =RequestViewHolder.idinfo;

                    Bid bid = new Bid(amount2,daysf,vendor_id,user_id,RequestList.vendoremail,RequestViewHolder.probleminfo,RequestViewHolder.serviceinfo);

                    ref.child(user_id).push().setValue(bid);

                    Toast.makeText(getApplicationContext(),"Bid has been submitted to the customer "+RequestViewHolder.emailinfo,Toast.LENGTH_SHORT).show();

                    Intent intToHome = new Intent(BidRequest.this, VendorHome.class);
                    startActivity(intToHome);

                }
            }
        });


    }
}

package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.uiClient.home.clienthomefragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Requests extends AppCompatActivity {


    EditText fullname, email, contact, address, problem;

    Button send;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Requests");

        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        contact = (EditText) findViewById(R.id.contact);
        address = (EditText) findViewById(R.id.address);
        problem = (EditText) findViewById(R.id.problem);

        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=fullname.getText().toString();
                String email1=email.getText().toString();
                String contact1=contact.getText().toString();
                final String address1=address.getText().toString();
                String problem1=problem.getText().toString();



                if(name.equals("")||email1.equals("")||contact1.equals("")||address1.equals("")||problem1.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    String user_id = mAuth.getCurrentUser().getUid();

                    Request request = new Request(name,email1,contact1,address1, clienthomefragment.serviceselected, problem1,user_id);

                    ref.child(user_id).push().setValue(request);

                    Toast.makeText(getApplicationContext(),"Request has been submitted to the vendors",Toast.LENGTH_SHORT).show();

                    Intent intToHome = new Intent(Requests.this, ClientHomeService.class);
                    startActivity(intToHome);

                }

            }

        });


    }


}



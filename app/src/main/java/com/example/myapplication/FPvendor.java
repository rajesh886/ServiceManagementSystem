package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FPvendor extends AppCompatActivity {

    String vusername;
    EditText vemailInput;
    Button vchangebutton;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpvendor);


        vemailInput = (EditText) findViewById(R.id.vemailInput);

        vchangebutton = (Button) findViewById(R.id.vchangeButton);

        vchangebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String vemail = vemailInput.getText().toString();



                if(vemail.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }

                else{

                    ref = FirebaseDatabase.getInstance().getReference().child("Customers");
                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                vusername = dataSnapshot1.getValue().toString();

                                if(vusername.equals(vemail)){
                                    String id = dataSnapshot.getKey();
                                    ref = FirebaseDatabase.getInstance().getReference().child("Customers").child(id);
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            vusername = dataSnapshot.child("email").getValue().toString();

                                                mAuth.getInstance().sendPasswordResetEmail(vemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getApplicationContext(),"Password reset link send to your email",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent j = new Intent(FPvendor.this,VendorLogin.class);
                                                            startActivity(j);
                                                        }
                                                        else{
                                                            Toast.makeText(getApplicationContext(),"Unsuccessful",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
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
            }
        });

    }



}



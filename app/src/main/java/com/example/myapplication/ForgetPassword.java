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

public class ForgetPassword extends AppCompatActivity {

    String username, qusn1, qusn2;
    EditText emailInput, sec1, sec2;
    Button changebutton;

    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        emailInput = (EditText) findViewById(R.id.emailInput);
        sec1 = (EditText) findViewById(R.id.sec1);
        sec2 = (EditText) findViewById(R.id.sec2);

        changebutton = (Button) findViewById(R.id.changeButton);

        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailInput.getText().toString();
                final String security1 = sec1.getText().toString();
                final String security2 = sec2.getText().toString();


                if(email.equals("")||security1.equals("")||security2.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }

                else{

                    ref = FirebaseDatabase.getInstance().getReference().child("Customers");
                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                username = dataSnapshot1.getValue().toString();

                                if(username.equals(email)){
                                    String id = dataSnapshot.getKey();
                                    ref = FirebaseDatabase.getInstance().getReference().child("Customers").child(id);
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            username = dataSnapshot.child("email").getValue().toString();
                                            qusn1 = dataSnapshot.child("sec1").getValue().toString();
                                            qusn2 = dataSnapshot.child("sec2").getValue().toString();

                                            if(security1.equals(qusn1)&&security2.equals(qusn2)){
                                                mAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                       if(task.isSuccessful()){
                                                           Toast.makeText(getApplicationContext(),"Password reset link send to your email",
                                                                   Toast.LENGTH_SHORT).show();
                                                           Intent j = new Intent(ForgetPassword.this,UserLogin.class);
                                                           startActivity(j);
                                                       }
                                                       else{
                                                           Toast.makeText(getApplicationContext(),"Unsuccessful",
                                                                   Toast.LENGTH_SHORT).show();
                                                       }
                                                    }
                                                });
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Security answers are incorrect.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
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



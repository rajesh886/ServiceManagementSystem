package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VendorLogin extends AppCompatActivity implements View.OnClickListener {
    String email, password;
    int counter=0;

    EditText emailInput;
    EditText passwordInput;
    TextView vforget_pw;

    Button loginButton;
    Button registerButton;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);


        mAuth=FirebaseAuth.getInstance();



        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        vforget_pw = (TextView) findViewById(R.id.vforget_pw);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        vforget_pw.setOnClickListener(this);

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(VendorLogin.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(VendorLogin.this, ClientHomeService.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(VendorLogin.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginButton:
                email=emailInput.getText().toString();
                password=passwordInput.getText().toString();



                if(counter!=4) {
                    if(email.isEmpty()){
                        emailInput.setError("Please enter email id");
                        emailInput.requestFocus();
                    }
                    else  if(password.isEmpty()){
                        passwordInput.setError("Please enter your password");
                        passwordInput.requestFocus();
                    }
                    else  if(email.isEmpty() && password.isEmpty()){
                        Toast.makeText(VendorLogin.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                    }
                    else  if(!(email.isEmpty() && password.isEmpty())){
                        ref = FirebaseDatabase.getInstance().getReference().child("Customers");
                        ref.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                if(dataSnapshot.child("IsVendor").getValue().equals(true)){
                                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(VendorLogin.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(!task.isSuccessful()){
                                                Toast.makeText(VendorLogin.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                                                counter++;
                                            }
                                            else{

                                                Toast.makeText(VendorLogin.this,"You are logged in",Toast.LENGTH_SHORT).show();
                                                Intent intToVendorHome = new Intent(VendorLogin.this, VendorHome.class);
                                                startActivity(intToVendorHome);
                                            }
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(VendorLogin.this,"Please login with vendor id",Toast.LENGTH_SHORT).show();
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
                    else{
                        Toast.makeText(VendorLogin.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
                }

                break;

            case R.id.registerButton:
                startActivity(new Intent(this, VendorRegister.class));
                break;

            case R.id.vforget_pw:
                startActivity(new Intent(this, FPvendor.class));
                break;


        }

    }


}

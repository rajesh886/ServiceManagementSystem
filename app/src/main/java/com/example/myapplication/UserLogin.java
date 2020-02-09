package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity implements View.OnClickListener {
    String email, password;
    int counter=0;

    EditText emailInput;
    EditText passwordInput;
    TextView forget_pw;

    Button loginButton;
    Button registerButton;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        mAuth=FirebaseAuth.getInstance();


        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        forget_pw = (TextView) findViewById(R.id.forget_pw);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);


        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        forget_pw.setOnClickListener(this);

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(UserLogin.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserLogin.this, ClientHomeService.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(UserLogin.this,"Please Login",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UserLogin.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                    }
                    else  if(!(email.isEmpty() && password.isEmpty())){
                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(UserLogin.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                                    counter++;
                                }
                                else{
                                    Toast.makeText(UserLogin.this,"You are logged in",Toast.LENGTH_SHORT).show();
                                    Intent intToHome = new Intent(UserLogin.this, ClientHomeService.class);
                                    startActivity(intToHome);

                                }
                            }
                        });


                    }
                    else{
                        Toast.makeText(UserLogin.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "You have incorrectly entered password for more than 3 times", Toast.LENGTH_SHORT).show();
                    Intent j = new Intent(UserLogin.this, ForgetPassword.class);
                    startActivity(j);
                }
                break;

            case R.id.registerButton:
                startActivity(new Intent(this, UserRegister.class));
                break;


            case R.id.forget_pw:
                startActivity(new Intent(this, ForgetPassword.class));
                break;
        }

    }


}

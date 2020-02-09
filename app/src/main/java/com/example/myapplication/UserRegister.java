package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegister extends AppCompatActivity {


    EditText email_input, pw_input, confirm_password, qusn1, qusn2;

    Button REGISTER;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Customers");

        email_input = (EditText) findViewById(R.id.email_input);
        pw_input = (EditText) findViewById(R.id.pw_input);
        confirm_password = (EditText) findViewById(R.id.confirm_password );
        qusn1 = (EditText) findViewById(R.id.qusn1);
        qusn2 = (EditText) findViewById((R.id.qusn2));


        REGISTER = (Button) findViewById(R.id.REGISTER);

        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=email_input.getText().toString();
                String pw=pw_input.getText().toString();
                String pw1=confirm_password.getText().toString();
                final String sec1=qusn1.getText().toString();
                final String sec2=qusn2.getText().toString();

                if(email.equals("")||pw.equals("")||pw1.equals("")||sec1.equals("")||sec2.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pw.equals(pw1))
                    {
                        mAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    String user_id = mAuth.getCurrentUser().getUid();

                                    User customer = new User(email,sec1,sec2);

                                    ref.child(user_id).setValue(customer);

                                    Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                                    Intent j = new Intent(UserRegister.this, UserLogin.class);
                                    startActivity(j);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Not Registered",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Password do not match",Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

}



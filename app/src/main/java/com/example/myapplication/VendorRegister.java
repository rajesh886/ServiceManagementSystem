package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VendorRegister extends AppCompatActivity {


    EditText email_input, pw_input, confirm_password;
    public static String service;
    Spinner spinner2;
    Button REGISTER;

    FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Customers");

        email_input = (EditText) findViewById(R.id.email_input);
        pw_input = (EditText) findViewById(R.id.pw_input);
        confirm_password= (EditText) findViewById(R.id.confirm_password);

        spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service = spinner2.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        REGISTER = (Button) findViewById(R.id.REGISTER);

        REGISTER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=email_input.getText().toString();
                String pw=pw_input.getText().toString();
                String pw1=confirm_password.getText().toString();



                if(email.equals("")||pw.equals("")||pw1.equals("")){
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

                                    User customer = new User(email,service);

                                    ref.child(user_id).setValue(customer);

                                    Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                                    Intent j = new Intent(VendorRegister.this, VendorLogin.class);
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

    public void onRadioButtonClicked(View view){
    // Is the button now checked?
    boolean checked = ((RadioButton) view).isChecked();

    // Check which radio button was clicked
            switch(view.getId()) {
        case R.id.radio_yes:
            if (checked)
                Toast.makeText(getApplicationContext(),"Press register button",Toast.LENGTH_SHORT).show();
                 REGISTER.setVisibility(View.VISIBLE);
            break;
        case R.id.radio_no:
            if (checked)
                Toast.makeText(getApplicationContext(),"You must agree to be approved vendor",Toast.LENGTH_SHORT).show();
                REGISTER.setVisibility(View.GONE);
                break;
    }
}

}



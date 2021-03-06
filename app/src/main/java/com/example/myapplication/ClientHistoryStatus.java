package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientHistoryStatus extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Appointments> list;
    ClientHistoryHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_history_status);

        recyclerView = (RecyclerView) findViewById(R.id.listclienthistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Appointments").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list = new ArrayList<Appointments>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Appointments a = dataSnapshot1.getValue(Appointments.class);
                    if(a.getStatus().equals("1")) {
                        list.add(a);
                    }
                }

                viewHolder = new ClientHistoryHolder(ClientHistoryStatus.this,list);
                recyclerView.setAdapter(viewHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ClientHistoryStatus.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

}

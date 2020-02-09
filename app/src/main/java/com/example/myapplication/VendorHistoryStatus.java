package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VendorHistoryStatus extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Appointments> list;
    User vendor1;
    public static String vendoremail1;
    VendorHistoryHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_history_status);

        recyclerView = (RecyclerView) findViewById(R.id.listHistoryforvendor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference referencev = FirebaseDatabase.getInstance().getReference().child("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referencev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotv) {
                vendor1 = dataSnapshotv.getValue(User.class);
                vendoremail1 = vendor1.getEmail();
                Toast.makeText(getApplicationContext(),vendoremail1,Toast.LENGTH_SHORT).show();
                reference = FirebaseDatabase.getInstance().getReference().child("Appointments");

                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        list = new ArrayList<Appointments>();
                        String user_id=dataSnapshot.getKey();
                        Query query= reference.child(user_id).orderByChild("vendorname").equalTo(vendoremail1);

                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                {
                                    Appointments a = dataSnapshot1.getValue(Appointments.class);
                                    if(a.getStatus().equals("1")) {
                                        list.add(a);
                                    }
                                }

                                viewHolder = new VendorHistoryHolder(VendorHistoryStatus.this,list);
                                recyclerView.setAdapter(viewHolder);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

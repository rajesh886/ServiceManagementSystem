package com.example.myapplication;

import android.os.Bundle;

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

public class RequestList extends AppCompatActivity {


    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Request> list_requests;
    User vendor;
    public static String vendoremail;

    RequestViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);


        recyclerView = (RecyclerView) findViewById(R.id.requestlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference referencev = FirebaseDatabase.getInstance().getReference().child("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referencev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotv) {
                vendor = dataSnapshotv.getValue(User.class);
                vendoremail = vendor.getEmail();
                reference = FirebaseDatabase.getInstance().getReference().child("Requests");

                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            list_requests = new ArrayList<Request>();
                            String user_id=dataSnapshot.getKey();
                            Query query= reference.child(user_id).orderByChild("service").equalTo(vendor.getService());

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                    {
                                        Request r = dataSnapshot1.getValue(Request.class);
                                        list_requests.add(r);
                                    }

                                    viewHolder = new RequestViewHolder(RequestList.this,list_requests);
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

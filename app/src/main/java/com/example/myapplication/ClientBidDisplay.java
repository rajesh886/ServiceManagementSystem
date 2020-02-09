package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientBidDisplay extends AppCompatActivity {

    DatabaseReference referencebd;
    RecyclerView recyclerView;
    ArrayList<Bid> list_bids;
    BidViewHolder bidviewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_bid_display);

        recyclerView = (RecyclerView) findViewById(R.id.bidlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        referencebd = FirebaseDatabase.getInstance().getReference().child("Bids").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Query query = referencebd.orderByChild("problem").equalTo(OrderViewHolder.problemd);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_bids = new ArrayList<Bid>();
                for(DataSnapshot bidSnapshot: dataSnapshot.getChildren())
                {
                    Bid bid = bidSnapshot.getValue(Bid.class);
                    String s = bidSnapshot.getKey();
                    list_bids.add(bid);
                }

                bidviewHolder = new BidViewHolder(ClientBidDisplay.this,list_bids);
                recyclerView.setAdapter(bidviewHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

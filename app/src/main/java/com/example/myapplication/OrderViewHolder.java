package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderViewHolder extends RecyclerView.Adapter<OrderViewHolder.MyViewHolder> {

    Context context;
    ArrayList<Request> request;

    public static String problemd;



    public OrderViewHolder(Context c , ArrayList<Request> r){
        context = c;
        request = r;
    }

    @NonNull
    @Override
    public OrderViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.client_order_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder.MyViewHolder holder, int position) {
        holder.rname.setText(request.get(position).getFullname());
        holder.remail.setText(request.get(position).getEmail());
        holder.rcontact.setText(request.get(position).getContact());
        holder.raddress.setText(request.get(position).getAddress());
        holder.rservice.setText(request.get(position).getService());
        holder.rproblem.setText(request.get(position).getProblem());
    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rname,remail,rcontact,raddress,rservice,rproblem;
        Button cancel,viewbids;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rname = (TextView) itemView.findViewById(R.id.rname);
            remail = (TextView) itemView.findViewById(R.id.remail);
            rcontact = (TextView) itemView.findViewById(R.id.rcontact);
            raddress = (TextView) itemView.findViewById(R.id.raddress);
            rservice = (TextView) itemView.findViewById(R.id.rservice);
            rproblem = (TextView) itemView.findViewById(R.id.rproblem);

            cancel = (Button) itemView.findViewById(R.id.cancel);
            viewbids = (Button) itemView.findViewById(R.id.viewbids);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Query query = databaseReference.orderByChild("problem").equalTo(rproblem.getText().toString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Toast.makeText(context,"Your request has been Cancelled successfully.",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            viewbids.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String rproblem1= rproblem.getText().toString();
                    problemd = rproblem1;
                    Intent bidviewer = new Intent(context,ClientBidDisplay.class);
                    context.startActivity(bidviewer);
                }
            });
        }

    }
}

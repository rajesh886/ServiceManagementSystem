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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendorAppointmentHolder extends RecyclerView.Adapter<VendorAppointmentHolder.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appoint;

    public static String vdate;


    public VendorAppointmentHolder(Context c , ArrayList<Appointments> a){
        context = c;
        appoint = a;
    }

    @NonNull
    @Override
    public VendorAppointmentHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_vendor_appointment_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VendorAppointmentHolder.MyViewHolder holder, int position) {
        holder.vprovidername.setText(appoint.get(position).getVendorname());
        holder.vclientemail.setText(appoint.get(position).getUser());
        holder.vclientp.setText(appoint.get(position).getClientp());
        holder.vservicename.setText(appoint.get(position).getServicename());
        holder.vtotalpaid.setText(appoint.get(position).getTotalamount());
        holder.vday.setText(appoint.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return appoint.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vprovidername, vclientemail, vclientp, vservicename, vtotalpaid, vday;
        Button Complete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vprovidername = (TextView) itemView.findViewById(R.id.vprovidername);
            vclientemail = (TextView) itemView.findViewById(R.id.vclientemail);
            vclientp = (TextView) itemView.findViewById(R.id.vclientp);
            vservicename = (TextView) itemView.findViewById(R.id.vservicename);
            vtotalpaid = (TextView) itemView.findViewById(R.id.vtotalpaid);
            vday = (TextView) itemView.findViewById(R.id.vday);

            Complete = (Button) itemView.findViewById(R.id.Complete);

            Complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String qdate= vday.getText().toString();
                    vdate=qdate;

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference().child("Appointments");

                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                            {
                                if(dataSnapshot1.child("date").getValue().toString().equals(vdate)){
                                    Map<String, Object> hopperUpdates = new HashMap<>();
                                    hopperUpdates.put("status", "1");

                                    databaseReference.child(dataSnapshot.getKey()).child(dataSnapshot1.getKey()).updateChildren(hopperUpdates);

                                    Toast.makeText(context,"This service request for "+dataSnapshot1.child("clientp").getValue().toString()
                                    +" has been completed.",Toast.LENGTH_SHORT).show();

                                    Intent intohome = new Intent(context, VendorHome.class);
                                    context.startActivity(intohome);

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
            });


        }

    }
}

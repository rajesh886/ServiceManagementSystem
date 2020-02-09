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

public class AppointmentViewHolder extends RecyclerView.Adapter<AppointmentViewHolder.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appoint;

    public static String sprovidername, sclientemail, sclientp, sservicename, stotalpaid, sdate ;




    public AppointmentViewHolder(Context c , ArrayList<Appointments> a){
        context = c;
        appoint = a;
    }

    @NonNull
    @Override
    public AppointmentViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_appointment_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder.MyViewHolder holder, int position) {
        holder.providername.setText(appoint.get(position).getVendorname());
        holder.clientemail.setText(appoint.get(position).getUser());
        holder.clientp.setText(appoint.get(position).getClientp());
        holder.servicename.setText(appoint.get(position).getServicename());
        holder.totalpaid.setText(appoint.get(position).getTotalamount());
        holder.day.setText(appoint.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return appoint.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView providername, clientemail, clientp, servicename, totalpaid, day;
        Button ChangeAppointment,CancelAppointment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            providername = (TextView) itemView.findViewById(R.id.providername);
            clientemail = (TextView) itemView.findViewById(R.id.clientemail);
            clientp = (TextView) itemView.findViewById(R.id.clientp);
            servicename = (TextView) itemView.findViewById(R.id.servicename);
            totalpaid = (TextView) itemView.findViewById(R.id.totalpaid);
            day = (TextView) itemView.findViewById(R.id.day);

            ChangeAppointment = (Button) itemView.findViewById(R.id.ChangeAppointment);
            CancelAppointment = (Button) itemView.findViewById(R.id.CancelAppointment);

            CancelAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Appointments").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Query query = databaseReference.orderByChild("date").equalTo(day.getText().toString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Toast.makeText(context,"Your appointment has been Cancelled successfully. Your payment will " +
                                        "be refunded in a week.",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

            ChangeAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String fprovidername= providername.getText().toString();
                    sprovidername = fprovidername;
                    final String fclientemail= clientemail.getText().toString();
                    sclientemail=fclientemail;
                    final String fclientp= clientp.getText().toString();
                    sclientp=fclientp;
                    final String fservicename= servicename.getText().toString();
                    sservicename=fservicename;
                    final String ftotalpaid= totalpaid.getText().toString();
                    stotalpaid=ftotalpaid;
                    final String fdate= day.getText().toString();
                    sdate=fdate;

                    Intent intonewdate = new Intent(context, DateChange.class);
                    context.startActivity(intonewdate);
                }
            });

        }

    }
}

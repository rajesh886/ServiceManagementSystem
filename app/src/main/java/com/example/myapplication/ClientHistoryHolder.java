package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClientHistoryHolder extends RecyclerView.Adapter<ClientHistoryHolder.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appoint;



    public ClientHistoryHolder(Context c , ArrayList<Appointments> a){
        context = c;
        appoint = a;
    }

    @NonNull
    @Override
    public ClientHistoryHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_client_history_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHistoryHolder.MyViewHolder holder, int position) {
        holder.cprovidername.setText(appoint.get(position).getVendorname());
        holder.cclientemail.setText(appoint.get(position).getUser());
        holder.cclientp.setText(appoint.get(position).getClientp());
        holder.cservicename.setText(appoint.get(position).getServicename());
        holder.ctotalpaid.setText(appoint.get(position).getTotalamount());
        holder.cday.setText(appoint.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return appoint.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cprovidername, cclientemail, cclientp, cservicename, ctotalpaid, cday;
        FloatingActionButton rate;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            cprovidername = (TextView) itemView.findViewById(R.id.cprovidername);
            cclientemail = (TextView) itemView.findViewById(R.id.cclientemail);
            cclientp = (TextView) itemView.findViewById(R.id.cclientp);
            cservicename = (TextView) itemView.findViewById(R.id.cservicename);
            ctotalpaid = (TextView) itemView.findViewById(R.id.ctotalpaid);
            cday = (TextView) itemView.findViewById(R.id.cday);

            rate = (FloatingActionButton)itemView.findViewById(R.id.rate);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingbar);

            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newintent = new Intent(context,RatingDialog.class);
                    context.startActivity(newintent);
                }
            });

        }

    }
}

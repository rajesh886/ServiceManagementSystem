package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VendorHistoryHolder extends RecyclerView.Adapter<VendorHistoryHolder.MyViewHolder> {

    Context context;
    ArrayList<Appointments> appoint;

    public static String vdate;


    public VendorHistoryHolder(Context c , ArrayList<Appointments> a){
        context = c;
        appoint = a;
    }

    @NonNull
    @Override
    public VendorHistoryHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_vendor_history_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VendorHistoryHolder.MyViewHolder holder, int position) {
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

        }

    }
}

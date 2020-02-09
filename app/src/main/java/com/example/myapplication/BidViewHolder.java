package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BidViewHolder extends RecyclerView.Adapter<BidViewHolder.MyViewHolder>{

    Context context;
    ArrayList<Bid> bid;

    public static String totalbid,vname,vservice,vproblem;



    public BidViewHolder(Context c , ArrayList<Bid> b){
        context = c;
        bid = b;
    }

    @NonNull
    @Override
    public BidViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bid_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BidViewHolder.MyViewHolder holder, int position) {
        holder.vendorname.setText(bid.get(position).getEmail());
        holder.servicedesired.setText(bid.get(position).getService());
        holder.bidamount.setText(bid.get(position).getAmount());
        holder.completiontime.setText(bid.get(position).getDays());
        holder.clientproblem.setText(bid.get(position).getProblem());
    }

    @Override
    public int getItemCount() {
        return bid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vendorname, servicedesired,bidamount,completiontime,clientproblem;
        Button acceptbid, declinebid;
        public MyViewHolder(View itemView) {
            super(itemView);


            vendorname = (TextView) itemView.findViewById(R.id.vendorname);
            servicedesired = (TextView) itemView.findViewById(R.id.servicedesired);
            bidamount = (TextView) itemView.findViewById(R.id.bidamount);
            completiontime = (TextView) itemView.findViewById(R.id.completiontime);
            clientproblem = (TextView) itemView.findViewById(R.id.clientproblem);
            acceptbid = (Button) itemView.findViewById(R.id.acceptbid);
            declinebid = (Button) itemView.findViewById(R.id.declinebid);

            acceptbid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String total = bidamount.getText().toString();
                    totalbid=total;
                    final String vname1 = vendorname.getText().toString();
                    vname=vname1;
                    final String servicef = servicedesired.getText().toString();
                    vservice=servicef;

                    final String servicep = clientproblem.getText().toString();
                    vproblem=servicep;
                    Intent intoappintment = new Intent(context, AppointmentActivity.class);
                    context.startActivity(intoappintment);
                }
            });
        }

    }
}
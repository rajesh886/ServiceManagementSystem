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

public class RequestViewHolder extends RecyclerView.Adapter<RequestViewHolder.MyViewHolder> {

    Context context;
    ArrayList<Request> request;
    public static String idinfo,emailinfo,probleminfo,serviceinfo;



    public RequestViewHolder(Context c , ArrayList<Request> r){
        context = c;
        request = r;
    }

    @NonNull
    @Override
    public RequestViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.request_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder.MyViewHolder holder, int position) {
        holder.request_name.setText(request.get(position).getFullname());
        holder.request_email.setText(request.get(position).getEmail());
        holder.request_contact.setText(request.get(position).getContact());
        holder.request_address.setText(request.get(position).getAddress());
        holder.request_service.setText(request.get(position).getService());
        holder.request_problem.setText(request.get(position).getProblem());
        holder.request_id.setText(request.get(position).getUserid());
    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView request_id,request_name,request_email,request_contact,request_address,request_service,request_problem;
        Button place,decline;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            request_id = (TextView) itemView.findViewById(R.id.request_id);
            request_name = (TextView) itemView.findViewById(R.id.request_name);
            request_email = (TextView) itemView.findViewById(R.id.request_email);
            request_contact = (TextView) itemView.findViewById(R.id.request_contact);
            request_address = (TextView) itemView.findViewById(R.id.request_address);
            request_service = (TextView) itemView.findViewById(R.id.request_service);
            request_problem = (TextView) itemView.findViewById(R.id.request_problem);

            place = (Button) itemView.findViewById(R.id.place);
            decline = (Button) itemView.findViewById(R.id.decline);

            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String new_id = request_id.getText().toString();
                    idinfo=new_id;
                    final String new_email = request_email.getText().toString();
                    emailinfo=new_email;
                    final String request_problem1 = request_problem.getText().toString();
                    probleminfo=request_problem1;
                    final String request_service1 = request_service.getText().toString();
                    serviceinfo=request_service1;
                    Intent placeabid = new Intent(context,BidRequest.class);
                    context.startActivity(placeabid);
                }
            });

            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                }
            });
        }
    }
}

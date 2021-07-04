package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-10.
*/

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edith.divisaviyafinalproject.Activities.ProductDetails;
import com.edith.divisaviyafinalproject.Activities.RequestDetails;
import com.edith.divisaviyafinalproject.Details.RequestInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder>{

    Context context;
    ArrayList<RequestInformation> request;

    public RequestAdapter(Context context, ArrayList<RequestInformation> request) {
        this.context = context;
        this.request = request;
    }

    public RequestAdapter() {
    }

    @NonNull
    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.request_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.MyViewHolder holder, int position) {
        holder.rTopic.setText(request.get(position).getRequestTopic());
        holder.rCategory.setText(request.get(position).getRequestCategory());
        holder.rDescription.setText(request.get(position).getRequestDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), RequestDetails.class);
                intent.putExtra("rID",request.get(position).getRequestID());
                intent.putExtra("rTopic",request.get(position).getRequestTopic());
                intent.putExtra("rCategory",request.get(position).getRequestCategory());
                intent.putExtra("rDescription",request.get(position).getRequestDescription());
                intent.putExtra("rRemark",request.get(position).getRequestRemark());

                intent.putExtra("uName",request.get(position).getUserName());
                intent.putExtra("uAddress",request.get(position).getUserAddress());
                intent.putExtra("uTelephone",request.get(position).getUserTelephone());
                intent.putExtra("uLat",request.get(position).getUserLat().toString());
                intent.putExtra("uLng",request.get(position).getUserLng().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rTopic, rCategory, rDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rTopic = itemView.findViewById(R.id.request_topic);
            rCategory = itemView.findViewById(R.id.request_category);
            rDescription = itemView.findViewById(R.id.request_description);
        }
    }
}

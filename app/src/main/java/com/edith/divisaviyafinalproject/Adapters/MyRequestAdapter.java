package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-10.
*/

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edith.divisaviyafinalproject.Activities.MyRequestDetails;
import com.edith.divisaviyafinalproject.Activities.RequestDetails;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.RequestInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class MyRequestAdapter extends RecyclerView.Adapter<MyRequestAdapter.MyViewHolder>{
    Context context;
    ArrayList<RequestInformation> request;

    public MyRequestAdapter(Context context, ArrayList<RequestInformation> request) {
        this.context = context;
        this.request = request;
    }

    public MyRequestAdapter() {
    }

    @NonNull
    @Override
    public MyRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyRequestAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.request_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRequestAdapter.MyViewHolder holder, int position) {
        holder.rTopic.setText(request.get(position).getRequestTopic());
        holder.rCategory.setText(request.get(position).getRequestCategory());
        holder.rDescription.setText(request.get(position).getRequestDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MyRequestDetails.class);
                intent.putExtra("rID",request.get(position).getRequestID());
                intent.putExtra("rTopic",request.get(position).getRequestTopic());
                intent.putExtra("rCategory",request.get(position).getRequestCategory());
                intent.putExtra("rDescription",request.get(position).getRequestDescription());
                intent.putExtra("rRemark",request.get(position).getRequestRemark());

                context.startActivity(intent);
                ((Activity)context).finish();
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

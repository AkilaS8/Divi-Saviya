package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-09.
*/

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edith.divisaviyafinalproject.Activities.ProductDetails;
import com.edith.divisaviyafinalproject.Activities.ServiceDetails;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceInformation> service;

    public ServiceAdapter(Context context, ArrayList<ServiceInformation> service) {
        this.context = context;
        this.service = service;
    }

    @NonNull
    @Override
    public ServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.service_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.MyViewHolder holder, int position) {

        holder.sTopic.setText(service.get(position).getServiceTopic());
        holder.sCategory.setText(service.get(position).getServiceCategory());
        holder.sRate.setText(service.get(position).getUserRate().toString());
        holder.sPrice.setText(service.get(position).getServicePrice());
        holder.sDescription.setText(service.get(position).getServiceDetails());
        holder.setImage(service.get(position).getServiceImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ServiceDetails.class);
                intent.putExtra("sID",service.get(position).getServiceID());
                intent.putExtra("sTopic",service.get(position).getServiceTopic());
                intent.putExtra("sCategory",service.get(position).getServiceCategory());
                intent.putExtra("sDescription",service.get(position).getServiceDetails());
                intent.putExtra("sPrice",service.get(position).getServicePrice());
                intent.putExtra("sRemark",service.get(position).getServiceRemarks());
                intent.putExtra("sImage",service.get(position).getServiceImage());

                intent.putExtra("uName",service.get(position).getUserName());
                intent.putExtra("uOccupation",service.get(position).getUserOccupation());
                intent.putExtra("uAddress",service.get(position).getUserAddress());
                intent.putExtra("uTelephone",service.get(position).getUserTelephone());
                intent.putExtra("uRate",service.get(position).getUserRate().toString());
                intent.putExtra("uLat",service.get(position).getUserLat().toString());
                intent.putExtra("uLng",service.get(position).getUserLng().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return service.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView sImage;
        TextView sTopic, sCategory, sRate, sPrice, sDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sTopic = itemView.findViewById(R.id.service_topic);
            sCategory = itemView.findViewById(R.id.service_category);
            sRate = itemView.findViewById(R.id.service_rate);
            sPrice = itemView.findViewById(R.id.service_price);
            sDescription = itemView.findViewById(R.id.service_description);
        }

        public void setImage (final String uri){
            sImage = itemView.findViewById(R.id.service_image);
            Glide.with(itemView.getContext()).load(uri).into(sImage);
        }
    }
}

package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-08.
*/

import android.app.Activity;
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
import com.edith.divisaviyafinalproject.Activities.MyProductDetails;
import com.edith.divisaviyafinalproject.Activities.MyServiceDetails;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.MyViewHolder>{

    Context context;
    ArrayList<ServiceInformation> services;

    public MyServiceAdapter(Context context, ArrayList<ServiceInformation> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public MyServiceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_services_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyServiceAdapter.MyViewHolder holder, int position) {
        holder.sTopic.setText(services.get(position).getServiceTopic());
        holder.sCategory.setText(services.get(position).getServiceCategory());
        holder.sPrice.setText(services.get(position).getServicePrice());
        holder.sDescription.setText(services.get(position).getServiceDetails());
        holder.setImage(services.get(position).getServiceImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MyServiceDetails.class);
                intent.putExtra("sID",services.get(position).getServiceID());
                intent.putExtra("sTopic",services.get(position).getServiceTopic());
                intent.putExtra("sCategory",services.get(position).getServiceCategory());
                intent.putExtra("sPrice",services.get(position).getServicePrice());
                intent.putExtra("sRemarks",services.get(position).getServiceRemarks());
                intent.putExtra("sDescription",services.get(position).getServiceDetails());
                intent.putExtra("sImage",services.get(position).getServiceImage());
                intent.putExtra("sKeywords",services.get(position).getServiceKeywords());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sTopic, sCategory, sPrice, sDescription;
        ImageView sImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sTopic = itemView.findViewById(R.id.service_topic);
            sCategory = itemView.findViewById(R.id.service_category);
            sPrice = itemView.findViewById(R.id.service_price);
            sDescription = itemView.findViewById(R.id.service_description);
            sImage = itemView.findViewById(R.id.service_image);
        }

        public void setImage (final String uri){
            sImage = itemView.findViewById(R.id.product_image);
            Glide.with(itemView.getContext()).load(uri).into(sImage);
        }
    }
}

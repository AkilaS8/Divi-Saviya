package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-08.
*/

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.edith.divisaviyafinalproject.Activities.MyProductDetails;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>{

    Context context;
    ArrayList<ProductInformation> products;

    public MyProductAdapter(Context context, ArrayList<ProductInformation> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_products_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.MyViewHolder holder, int position) {
        holder.pName.setText(products.get(position).getProductName());
        holder.pCategory.setText(products.get(position).getProductCategory());
        holder.pPrice.setText(products.get(position).getProductPrice());
        holder.pQty.setText(products.get(position).getProductQty());
        holder.pAvailable.setText(products.get(position).getProductAvailability());
        holder.setImage(products.get(position).getProductImage());

        String available = products.get(position).getProductAvailability();
        if (available.equals("Available")){
            holder.pAvailableImage.setImageResource(R.drawable.tick);
        } else if (available.equals("Not Available")){
            holder.pAvailableImage.setImageResource(R.drawable.wrong);
            holder.pAvailable.setTextSize(14);
        } else if (available.equals("Ordered")){
            holder.pAvailableImage.setImageResource(R.drawable.caution);
        }

        String organic  = products.get(position).getProductOrganic();
        if (organic.equals("Organic")){
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E4FCC9"));
            holder.linearLayout.setBackgroundDrawable(colorDrawable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MyProductDetails.class);
                intent.putExtra("pID",products.get(position).getProductID());
                intent.putExtra("pName",products.get(position).getProductName());
                intent.putExtra("pCategory",products.get(position).getProductCategory());
                intent.putExtra("pOrganic",products.get(position).getProductOrganic());
                intent.putExtra("pPrice",products.get(position).getProductPrice());
                intent.putExtra("pQty",products.get(position).getProductQty());
                intent.putExtra("pAvailable",products.get(position).getProductAvailability());
                intent.putExtra("pRemark",products.get(position).getProductRemark());
                intent.putExtra("pImage",products.get(position).getProductImage());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView pName, pCategory, pPrice, pQty, pAvailable;
        ImageView pImage, pAvailableImage;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.product_name);
            pCategory = itemView.findViewById(R.id.product_category);
            pPrice = itemView.findViewById(R.id.product_price);
            pQty = itemView.findViewById(R.id.product_qty);
            pAvailable = itemView.findViewById(R.id.product_available);
            linearLayout = itemView.findViewById(R.id.product_background);

            pImage = itemView.findViewById(R.id.product_image);
            pAvailableImage = itemView.findViewById(R.id.product_available_image);

        }

        public void setImage (final String uri){
            pImage = itemView.findViewById(R.id.product_image);
            Glide.with(itemView.getContext()).load(uri).into(pImage);
        }
    }
}

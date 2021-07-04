package com.edith.divisaviyafinalproject.Adapters;/*
Created by Akila Ishan on 2021-02-09.
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
import com.edith.divisaviyafinalproject.Activities.ProductDetails;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    Context context;
    ArrayList<ProductInformation> product;

    public ProductAdapter(Context context, ArrayList<ProductInformation> product) {
        this.context = context;
        this.product = product;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        holder.pName.setText(product.get(position).getProductName());
        holder.pCategory.setText(product.get(position).getProductCategory());
        holder.pPrice.setText(product.get(position).getProductPrice());
        holder.pRate.setText(product.get(position).getUserRate().toString());
        holder.setImage(product.get(position).getProductImage());

        String organic  = product.get(position).getProductOrganic();
        if (organic.equals("Organic")){
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E4FCC9"));
            holder.pBackground.setBackgroundDrawable(colorDrawable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetails.class);
                intent.putExtra("pID",product.get(position).getProductID());
                intent.putExtra("pName",product.get(position).getProductName());
                intent.putExtra("pCategory",product.get(position).getProductCategory());
                intent.putExtra("pOrganic",product.get(position).getProductOrganic());
                intent.putExtra("pPrice",product.get(position).getProductPrice());
                intent.putExtra("pQty",product.get(position).getProductQty());
                intent.putExtra("pAvailable",product.get(position).getProductAvailability());
                intent.putExtra("pRemark",product.get(position).getProductRemark());
                intent.putExtra("pImage",product.get(position).getProductImage());

                intent.putExtra("uName",product.get(position).getUserName());
                intent.putExtra("uOccupation",product.get(position).getUserOccupation());
                intent.putExtra("uAddress",product.get(position).getUserAddress());
                intent.putExtra("uTelephone",product.get(position).getUserTelephone());
                intent.putExtra("uRate",product.get(position).getUserRate().toString());
                intent.putExtra("uLat",product.get(position).getUserLat().toString());
                intent.putExtra("uLng",product.get(position).getUserLng().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pName, pCategory, pPrice, pRate;
        ImageView pImage;
        LinearLayout pBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pName = itemView.findViewById(R.id.product_name);
            pCategory = itemView.findViewById(R.id.product_category);
            pPrice = itemView.findViewById(R.id.product_price);
            pRate = itemView.findViewById(R.id.product_rate);
            pImage = itemView.findViewById(R.id.product_image);
            pBackground = itemView.findViewById(R.id.product_background);
        }
        public void setImage (final String uri){
            pImage = itemView.findViewById(R.id.product_image);
            Glide.with(itemView.getContext()).load(uri).into(pImage);
        }
    }
}

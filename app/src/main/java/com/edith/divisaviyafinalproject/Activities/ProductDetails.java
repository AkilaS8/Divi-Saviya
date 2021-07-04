package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edith.divisaviyafinalproject.R;

public class ProductDetails extends AppCompatActivity {
    TextView pName, pCategory, pOrganic, pPrice, pRemark, pAvailable, pQty;
    TextView uName, uOccupation, uRate, uAddress, uTelephone;
    ImageView pImage;
    LinearLayout contact, direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        pName = findViewById(R.id.product_details_name_txt);
        pCategory = findViewById(R.id.product_details_category_txt);
        pOrganic = findViewById(R.id.product_details_organic_txt);
        pPrice = findViewById(R.id.product_details_price_txt);
        pRemark = findViewById(R.id.product_details_remark_txt);
        pAvailable = findViewById(R.id.product_details_available_txt);
        pQty = findViewById(R.id.product_details_qty_txt);
        pImage = findViewById(R.id.product_details_image);

        uName = findViewById(R.id.product_details_seller_name_txt);
        uOccupation = findViewById(R.id.product_details_seller_occupation_txt);
        uRate = findViewById(R.id.product_details_seller_rate_txt);
        uAddress = findViewById(R.id.product_details_seller_address_txt);
        uTelephone = findViewById(R.id.product_details_seller_telephone_txt);

        contact = findViewById(R.id.product_details_contact_btn);
        direction = findViewById(R.id.product_details_direction_btn);


        //------Action bar settings ----------------------------------------------------------------
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //------------------------Get Intent Data---------------------------------------------------
        pName.setText(getIntent().getStringExtra("pName"));
        pCategory.setText(getIntent().getStringExtra("pCategory"));
        pQty.setText(getIntent().getStringExtra("pQty"));
        pPrice.setText(getIntent().getStringExtra("pPrice"));
        pOrganic.setText(getIntent().getStringExtra("pOrganic"));
        pAvailable.setText(getIntent().getStringExtra("pAvailable"));
        pRemark.setText(getIntent().getStringExtra("pRemark"));

        uName.setText(getIntent().getStringExtra("uName"));
        uOccupation.setText(getIntent().getStringExtra("uOccupation"));
        uRate.setText(getIntent().getStringExtra("uRate"));
        uAddress.setText(getIntent().getStringExtra("uAddress"));
        uTelephone.setText(getIntent().getStringExtra("uTelephone"));

        Glide.with(ProductDetails.this).load(getIntent().getStringExtra("pImage")).into(pImage);
        //------------------------------------------------------------------------------------------

        //---------------Contact Seller-------------------------------------------------------------
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentC = new Intent();
                intentC.setAction(Intent.ACTION_DIAL); // Action for what intent called for
                intentC.setData(Uri.parse("tel: " + getIntent().getStringExtra("uTelephone"))); // Data with intent respective action on intent
                startActivity(intentC);
            }
        });
        //------------------------------------------------------------------------------------------

        //------------------------Direction---------------------------------------------------------
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = (getIntent().getStringExtra("uLat"));
                String lng = (getIntent().getStringExtra("uLng"));

                float latitude = Float.parseFloat(lat);
                float longitude = Float.parseFloat(lng);

                Intent intentD = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&model=b"));
                intentD.setPackage("com.google.android.apps.maps");

                if (intentD.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentD);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
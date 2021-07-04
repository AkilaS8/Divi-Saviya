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

public class ServiceDetails extends AppCompatActivity {
    TextView sTopic, sCategory, sDescription, sRemarks, sPrice;
    TextView uName, uOccupation, uRate, uAddress, uTelephone;
    ImageView sImage;
    LinearLayout contact, direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        sTopic = findViewById(R.id.service_details_topic_txt);
        sCategory = findViewById(R.id.service_details_category_txt);
        sDescription = findViewById(R.id.service_details_description_txt);
        sPrice = findViewById(R.id.service_details_price_txt);
        sRemarks = findViewById(R.id.service_details_remark_txt);
        sImage = findViewById(R.id.service_details_image);

        uName = findViewById(R.id.service_details_seller_name_txt);
        uOccupation = findViewById(R.id.service_details_seller_occupation_txt);
        uRate = findViewById(R.id.service_details_seller_rate_txt);
        uAddress = findViewById(R.id.service_details_seller_address_txt);
        uTelephone = findViewById(R.id.service_details_seller_telephone_txt);

        contact = findViewById(R.id.service_details_contact_btn);
        direction = findViewById(R.id.service_details_direction_btn);

        //------Action bar settings ----------------------------------------------------------------
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Service Details");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //------------------------Get Intent Data---------------------------------------------------
        sTopic.setText(getIntent().getStringExtra("sTopic"));
        sCategory.setText(getIntent().getStringExtra("sCategory"));
        sDescription.setText(getIntent().getStringExtra("sDescription"));
        sPrice.setText(getIntent().getStringExtra("sPrice"));
        sRemarks.setText(getIntent().getStringExtra("sRemark"));

        uName.setText(getIntent().getStringExtra("uName"));
        uOccupation.setText(getIntent().getStringExtra("uOccupation"));
        uRate.setText(getIntent().getStringExtra("uRate"));
        uAddress.setText(getIntent().getStringExtra("uAddress"));
        uTelephone.setText(getIntent().getStringExtra("uTelephone"));

        Glide.with(ServiceDetails.this).load(getIntent().getStringExtra("sImage")).into(sImage);
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
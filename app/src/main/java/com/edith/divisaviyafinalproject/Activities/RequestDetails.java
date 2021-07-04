package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edith.divisaviyafinalproject.R;

public class RequestDetails extends AppCompatActivity {
    TextView rTopic, rCategory, rDescription, rRemark;
    TextView uName, uAddress, uTelephone;
    LinearLayout contact, direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        rTopic = findViewById(R.id.request_details_topic_txt);
        rCategory = findViewById(R.id.request_details_category_txt);
        rDescription = findViewById(R.id.request_details_description_txt);
        rRemark = findViewById(R.id.request_details_remark_txt);

        uName = findViewById(R.id.request_details_user_name_txt);
        uAddress = findViewById(R.id.request_details_user_address_txt);
        uTelephone = findViewById(R.id.request_details_user_telephone_txt);

        contact = findViewById(R.id.request_details_contact_btn);
        direction = findViewById(R.id.request_details_direction_btn);

        //------Action bar settings ----------------------------------------------------------------
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request Details");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //------------------------Get Intent Data---------------------------------------------------
        rTopic.setText(getIntent().getStringExtra("rTopic"));
        rCategory.setText(getIntent().getStringExtra("rCategory"));
        rDescription.setText(getIntent().getStringExtra("rDescription"));
        rRemark.setText(getIntent().getStringExtra("rRemark"));

        uName.setText(getIntent().getStringExtra("uName"));
        uAddress.setText(getIntent().getStringExtra("uAddress"));
        uTelephone.setText(getIntent().getStringExtra("uTelephone"));
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
        //------------------------------------------------------------------------------------------
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
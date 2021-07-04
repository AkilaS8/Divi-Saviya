package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyServiceDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String sTopic, sCategory, sDescription, sPrice, sRemarks, sKeywords;
    ArraySets arraySets = new ArraySets();

    EditText serviceTopic, serviceDescription, servicePrice, serviceRemarks, serviceKeywords;
    Spinner serviceCategory;
    ImageView serviceImage;

    ToggleButton serviceEdit;
    Button serviceDelete;

    FirebaseFirestore serviceUpdate = FirebaseFirestore.getInstance();
    String USER_ID = "";
    String SERVICE_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service_details);

        serviceTopic = findViewById(R.id.service_topic);
        serviceCategory = findViewById(R.id.service_category);
        serviceDescription = findViewById(R.id.service_description);
        servicePrice = findViewById(R.id.service_price);
        serviceRemarks = findViewById(R.id.service_remark);
        serviceKeywords = findViewById(R.id.service_keywords);
        serviceImage = findViewById(R.id.service_image);
        serviceEdit = findViewById(R.id.service_edit);
        serviceDelete = findViewById(R.id.service_delete);

        //Disable Click
        serviceTopic.setEnabled(false);
        serviceCategory.setEnabled(false);
        servicePrice.setEnabled(false);
        serviceRemarks.setEnabled(false);
        serviceKeywords.setEnabled(false);

        serviceTopic.setText(getIntent().getStringExtra("sTopic"));
        servicePrice.setText(getIntent().getStringExtra("sPrice"));
        serviceRemarks.setText(getIntent().getStringExtra("sRemarks"));
        serviceDescription.setText(getIntent().getStringExtra("sDescription"));
        serviceKeywords.setText(getIntent().getStringExtra("sKeywords"));
        Glide.with(MyServiceDetails.this).load(getIntent().getStringExtra("sImage")).into(serviceImage);

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle("My Service");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //--------------- Spinners Settings --------------------------------------------------------
        //Category
        serviceCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Service);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceCategory.setAdapter(adapter);

        serviceCategory.setSelection(adapter.getPosition(getIntent().getStringExtra("sCategory")));
        //------------------------------------------------------------------------------------------

        //-------------Toggle Button----------------------------------------------------------------
        serviceEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Enable Click
                    serviceTopic.setEnabled(true);
                    serviceCategory.setEnabled(true);
                    servicePrice.setEnabled(true);
                    serviceRemarks.setEnabled(true);
                    serviceKeywords.setEnabled(true);
                }else{
                    //Disable Click
                    serviceTopic.setEnabled(false);
                    serviceCategory.setEnabled(false);
                    servicePrice.setEnabled(false);
                    serviceRemarks.setEnabled(false);
                    serviceKeywords.setEnabled(false);

                    //TODO: update part
                    if (!checkEmptyEditText()){
                        Toast.makeText(MyServiceDetails.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    }else{
                        DocumentReference productRef = serviceUpdate.collection("MyStore").document(USER_ID).collection("MyServices").document(SERVICE_ID);
                        DocumentReference reference = serviceUpdate.document("Services/" + SERVICE_ID);

                        productRef.update("serviceTopic",sTopic);
                        productRef.update("serviceCategory",sCategory);
                        productRef.update("servicePrice",sPrice);
                        productRef.update("serviceRemark",sRemarks);
                        productRef.update("serviceDetails",sDescription);
                        productRef.update("serviceKeywords",sKeywords);

                        reference.update("serviceTopic",sTopic);
                        reference.update("serviceCategory",sCategory);
                        reference.update("servicePrice",sPrice);
                        reference.update("serviceRemark",sRemarks);
                        reference.update("serviceDetails",sDescription);
                        reference.update("serviceKeywords",sKeywords);

                        Toast.makeText(MyServiceDetails.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MyServiceDetails.this, MyShop.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            }
        });
        //------------------------------------------------------------------------------------------

        serviceDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Are you Sure To Delete Service", Snackbar.LENGTH_LONG).setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productDeleteMethod();
                    }
                }).show();
            }
        });
    }

    private void productDeleteMethod() {
        DocumentReference productRef = serviceUpdate.collection("MyStore").document(USER_ID).collection("MyServices").document(SERVICE_ID);
        DocumentReference reference = serviceUpdate.document("Services/" + SERVICE_ID);

        productRef.delete();
        reference.delete();

        Toast.makeText(MyServiceDetails.this, "Service Deleted", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MyServiceDetails.this, MyShop.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        SERVICE_ID = getIntent().getStringExtra("sID");
    }

    public boolean checkEmptyEditText() {
        sTopic = serviceTopic.getText().toString();
        sDescription = serviceDescription.getText().toString();
        sRemarks = serviceRemarks.getText().toString();
        sPrice = servicePrice.getText().toString();
        sKeywords = serviceKeywords.getText().toString();

        if (!sTopic.isEmpty() && !sDescription.isEmpty() && !sRemarks.isEmpty() && !sPrice.isEmpty() && !sKeywords.isEmpty() && !sCategory.equals("Select Service")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = (Spinner) parent;

        if (spinner1.getId() == R.id.service_category) {
            sCategory = arraySets.Service[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
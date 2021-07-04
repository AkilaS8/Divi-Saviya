package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import static com.edith.divisaviyafinalproject.R.layout.activity_add_product;
import static com.edith.divisaviyafinalproject.R.layout.activity_my_product_details;

public class MyProductDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String pName, pCategory, pOrganic, pPrice, pQty, pAvailable, pRemark;
    ArraySets arraySets = new ArraySets();

    EditText productName, productPrice, productQty, productRemark;
    Spinner productCategory, productOrganic, productAvailability;
    ImageView productImage;

    ToggleButton productEdit;
    Button productDelete;

    FirebaseFirestore productUpdate = FirebaseFirestore.getInstance();
    String USER_ID = "";
    String PRODUCT_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_details);

        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productQty = findViewById(R.id.product_qty);
        productRemark = findViewById(R.id.product_remark);
        productCategory = findViewById(R.id.product_category);
        productOrganic = findViewById(R.id.product_organic);
        productAvailability = findViewById(R.id.product_available);
        productImage = findViewById(R.id.product_image);
        productEdit = findViewById(R.id.product_edit);
        productDelete = findViewById(R.id.product_delete);

        productName.setText(getIntent().getStringExtra("pName"));
        productPrice.setText(getIntent().getStringExtra("pPrice"));
        productQty.setText(getIntent().getStringExtra("pQty"));
        productRemark.setText(getIntent().getStringExtra("pRemark"));
        Glide.with(MyProductDetails.this).load(getIntent().getStringExtra("pImage")).into(productImage);

        //Disable
        productName.setEnabled(false);
        productPrice.setEnabled(false);
        productQty.setEnabled(false);
        productRemark.setEnabled(false);
        productCategory.setEnabled(false);
        productOrganic.setEnabled(false);
        productAvailability.setEnabled(false);

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle("My Product");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //--------------- Spinners Settings --------------------------------------------------------
        //Category
        productCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);

        productCategory.setSelection(adapter.getPosition(getIntent().getStringExtra("pCategory")));

        //Organic
        productOrganic.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Organic);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productOrganic.setAdapter(adapter1);

        productOrganic.setSelection(adapter1.getPosition(getIntent().getStringExtra("pOrganic")));

        //Available
        productAvailability.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Status);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productAvailability.setAdapter(adapter2);

        productAvailability.setSelection(adapter2.getPosition(getIntent().getStringExtra("pAvailable")));
        //------------------------------------------------------------------------------------------

        //-------------Toggle Button----------------------------------------------------------------
        productEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Enable Click
                    productName.setEnabled(true);
                    productPrice.setEnabled(true);
                    productQty.setEnabled(true);
                    productRemark.setEnabled(true);
                    productCategory.setEnabled(true);
                    productOrganic.setEnabled(true);
                    productAvailability.setEnabled(true);
                } else {
                    //Disable
                    productName.setEnabled(false);
                    productPrice.setEnabled(false);
                    productQty.setEnabled(false);
                    productRemark.setEnabled(false);
                    productCategory.setEnabled(false);
                    productOrganic.setEnabled(false);
                    productAvailability.setEnabled(false);

                    //TODO:Update part

                    if (!checkEmptyEditText()) {
                        Toast.makeText(MyProductDetails.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentReference productRef = productUpdate.collection("MyStore").document(USER_ID).collection("MyProducts").document(PRODUCT_ID);
                        DocumentReference reference = productUpdate.document("Products/" + PRODUCT_ID);

                        productRef.update("productName", pName);
                        productRef.update("productPrice", pPrice);
                        productRef.update("productQty", pQty);
                        productRef.update("productRemark", pRemark);
                        productRef.update("productCategory", pCategory);
                        productRef.update("productOrganic", pOrganic);
                        productRef.update("productAvailability", pAvailable);

                        reference.update("productName", pName);
                        reference.update("productPrice", pPrice);
                        reference.update("productQty", pQty);
                        reference.update("productRemark", pRemark);
                        reference.update("productCategory", pCategory);
                        reference.update("productOrganic", pOrganic);
                        reference.update("productAvailability", pAvailable);

                        Toast.makeText(MyProductDetails.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MyProductDetails.this, MyShop.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            }
        });
        //------------------------------------------------------------------------------------------

        //-------------------- Delete Button -------------------------------------------------------
        productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Are you Sure To Delete Product", Snackbar.LENGTH_LONG).setAction("DELETE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productDeleteMethod();
                    }
                }).show();
            }
        });
    }

    public void productDeleteMethod() {
        DocumentReference productRef = productUpdate.collection("MyStore").document(USER_ID).collection("MyProducts").document(PRODUCT_ID);
        DocumentReference reference = productUpdate.document("Products/" + PRODUCT_ID);

        productRef.delete();
        reference.delete();
        Toast.makeText(MyProductDetails.this, "Product Deleted", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MyProductDetails.this, MyShop.class);
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
        PRODUCT_ID = getIntent().getStringExtra("pID");
    }

    private boolean checkEmptyEditText() {
        pName = productName.getText().toString();
        pPrice = productPrice.getText().toString();
        pRemark = productRemark.getText().toString();
        pQty = productQty.getText().toString();

        if (!pName.isEmpty() && !pPrice.isEmpty() && !pQty.isEmpty() && !pRemark.isEmpty() && !pCategory.equals("Select Category")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner1 = (Spinner) parent;
        Spinner spinner2 = (Spinner) parent;
        Spinner spinner3 = (Spinner) parent;

        if (spinner1.getId() == R.id.product_category) {
            pCategory = arraySets.Category[position];
        }
        if (spinner2.getId() == R.id.product_organic) {
            pOrganic = arraySets.Organic[position];
        }
        if (spinner3.getId() == R.id.product_available) {
            pAvailable = arraySets.Status[position];
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
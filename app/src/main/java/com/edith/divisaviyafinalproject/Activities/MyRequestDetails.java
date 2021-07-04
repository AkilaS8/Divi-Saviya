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
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyRequestDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String rTopic = "";
    String rCategory = "";
    String rDescription = "";
    String rRemark = "";

    EditText requestTopic, requestDescription, requestRemark;
    Spinner requestCategory;

    ToggleButton requestEdit;
    Button requestDelete;

    ArraySets arraySets = new ArraySets();

    FirebaseFirestore requestUpdate = FirebaseFirestore.getInstance();
    String USER_ID = "";
    String REQUEST_ID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_details);

        requestTopic = findViewById(R.id.request_topic);
        requestDescription = findViewById(R.id.request_description);
        requestRemark = findViewById(R.id.request_remark);
        requestCategory = findViewById(R.id.request_category);
        requestEdit = findViewById(R.id.request_edit);
        requestDelete = findViewById(R.id.request_delete);

        requestTopic.setText(getIntent().getStringExtra("rTopic"));
        requestDescription.setText(getIntent().getStringExtra("rDescription"));
        requestRemark.setText(getIntent().getStringExtra("rRemark"));

        //Disable
        requestTopic.setEnabled(false);
        requestCategory.setEnabled(false);
        requestDescription.setEnabled(false);
        requestRemark.setEnabled(false);

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle("My Request");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //--------------- Spinners Settings --------------------------------------------------------
        //Category
        requestCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Request);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestCategory.setAdapter(adapter);

        requestCategory.setSelection(adapter.getPosition(getIntent().getStringExtra("rCategory")));
        //------------------------------------------------------------------------------------------

        //-------------Toggle Button----------------------------------------------------------------
        requestEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Enable
                    requestTopic.setEnabled(true);
                    requestCategory.setEnabled(true);
                    requestDescription.setEnabled(true);
                    requestRemark.setEnabled(true);
                } else {
                    //Disable
                    requestTopic.setEnabled(false);
                    requestCategory.setEnabled(false);
                    requestDescription.setEnabled(false);
                    requestRemark.setEnabled(false);

                    //TODO: update part
                    if (!checkEmptyEditText()) {
                        Toast.makeText(MyRequestDetails.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentReference requestRef = requestUpdate.collection("MyStore").document(USER_ID).collection("MyRequests").document(REQUEST_ID);
                        DocumentReference reference = requestUpdate.document("Requests/" + REQUEST_ID);

                        requestRef.update("requestTopic", rTopic);
                        requestRef.update("requestCategory", rCategory);
                        requestRef.update("requestDescription", rDescription);
                        requestRef.update("requestRemark", rRemark);

                        reference.update("requestTopic", rTopic);
                        reference.update("requestCategory", rCategory);
                        reference.update("requestDescription", rDescription);
                        reference.update("requestRemark", rRemark);

                        Toast.makeText(MyRequestDetails.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MyRequestDetails.this, RequestMain.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            }
        });
        //------------------------------------------------------------------------------------------

        requestDelete.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        REQUEST_ID = getIntent().getStringExtra("rID");
    }

    public void productDeleteMethod() {
        DocumentReference requestRef = requestUpdate.collection("MyStore").document(USER_ID).collection("MyRequests").document(REQUEST_ID);
        DocumentReference reference = requestUpdate.document("Requests/" + REQUEST_ID);

        reference.delete();
        requestRef.delete();

        Toast.makeText(MyRequestDetails.this, "Product Deleted", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MyRequestDetails.this, RequestMain.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private boolean checkEmptyEditText() {
        rTopic = requestTopic.getText().toString();
        rDescription = requestDescription.getText().toString();
        rRemark = requestRemark.getText().toString();

        if (!rTopic.isEmpty() && !rDescription.isEmpty() && !rRemark.isEmpty() && !rCategory.equals("Select Category")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = (Spinner) parent;
        if (spinner1.getId() == R.id.request_category) {
            rCategory = arraySets.Request[position];
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
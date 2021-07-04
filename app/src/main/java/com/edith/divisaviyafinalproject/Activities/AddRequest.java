package com.edith.divisaviyafinalproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.RequestInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddRequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText requestTopic, requestDescription, requestRemark;
    Spinner requestCategory;
    TextView topic, category, description, remark;
    Button addrequest;
    String appTitle = "";

    RequestInformation requestInformation = new RequestInformation();
    ArraySets arraySets = new ArraySets();

    FirebaseFirestore requestStore = FirebaseFirestore.getInstance();

    public String rID = "";
    public String rTopic = "";
    public String rCategory = "";
    public String rDescription = "";
    public String rRemark = "";

    public String uName = "";
    public String uTelephone = "";
    public String uAddress = "";
    public String uOccupation = "";
    public Double uLat;
    public Double uLng;
    private String uState = "";

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        requestTopic = findViewById(R.id.Request_topic);
        requestCategory = findViewById(R.id.Request_category);
        requestDescription = findViewById(R.id.Request_description);
        requestRemark = findViewById(R.id.Request_remark);

        topic = findViewById(R.id.topic);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        remark = findViewById(R.id.remark);
        addrequest = findViewById(R.id.Request_add);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(AddRequest.this, "si");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_request_topic));
            category.setText(resources.getString(R.string.add_request_category));
            description.setText(resources.getString(R.string.add_request_description));
            remark.setText(resources.getString(R.string.add_request_remark));
            addrequest.setText(resources.getString(R.string.add_request_add));

            appTitle = resources.getString(R.string.add_request_add);

            requestTopic.setHint(resources.getString(R.string.add_request_topic));
            requestDescription.setHint(resources.getString(R.string.add_request_description));
            requestRemark.setHint(resources.getString(R.string.add_request_remark));

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(AddRequest.this, "en");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_request_topic));
            category.setText(resources.getString(R.string.add_request_category));
            description.setText(resources.getString(R.string.add_request_description));
            remark.setText(resources.getString(R.string.add_request_remark));
            addrequest.setText(resources.getString(R.string.add_request_add));

            appTitle = resources.getString(R.string.add_request_add);

            requestTopic.setHint(resources.getString(R.string.add_request_topic));
            requestDescription.setHint(resources.getString(R.string.add_request_description));
            requestRemark.setHint(resources.getString(R.string.add_request_remark));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(AddRequest.this, "ta");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_request_topic));
            category.setText(resources.getString(R.string.add_request_category));
            description.setText(resources.getString(R.string.add_request_description));
            remark.setText(resources.getString(R.string.add_request_remark));
            addrequest.setText(resources.getString(R.string.add_request_add));

            appTitle = resources.getString(R.string.add_request_add);

            requestTopic.setHint(resources.getString(R.string.add_request_topic));
            requestDescription.setHint(resources.getString(R.string.add_request_description));
            requestRemark.setHint(resources.getString(R.string.add_request_remark));
        }
//--------------------------------------------------------------------------------------------------

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        rID = USER_ID + "_" + System.currentTimeMillis() + "";

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle(appTitle);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));


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
        //------------------------------------------------------------------------------------------
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        rID = USER_ID + "_" + System.currentTimeMillis() + "";

        uName = userPreferences.getString("USER_NAME", "");
        uTelephone = userPreferences.getString("USER_TELEPHONE", "");
        uAddress = userPreferences.getString("USER_ADDRESS", "");
        uOccupation = userPreferences.getString("USER_OCCUPATION", "");
        uState = userPreferences.getString("USER_STATE", "");
        uLat = Double.valueOf(userPreferences.getString("USER_LAT", ""));
        uLng = Double.valueOf(userPreferences.getString("USER_LNG", ""));
        Log.d("User Product", uName + "  " + uTelephone + " " + uAddress + " " + uOccupation + " " + uLng.toString() + " " + uLat.toString());
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

    public void requestAdd(View view) {
        if (!checkEmptyEditText()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            saveRequest();
        }
    }

    public void saveRequest() {
        DocumentReference requestRef = requestStore.collection("MyStore").document(uTelephone).collection("MyRequests").document(rID);
        DocumentReference reference = requestStore.document("Requests/" + rID);

        RequestInformation information = new RequestInformation(rID, rTopic, rCategory, rDescription, rRemark, uName, uTelephone, uAddress, uOccupation, uState, uLat, uLng);


        requestRef.set(information).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.set(information);
                Toast.makeText(AddRequest.this, "Request Added Successfully", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddRequest.this, RequestMain.class);
                        startActivity(intent);
                        finish();
                    }
                },2000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddRequest.this, "Request Not Added !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner1 = (Spinner) parent;
        if (spinner1.getId() == R.id.Request_category) {
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
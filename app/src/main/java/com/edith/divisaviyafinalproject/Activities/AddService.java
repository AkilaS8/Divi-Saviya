package com.edith.divisaviyafinalproject.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.edith.divisaviyafinalproject.R.layout.activity_add_product;
import static com.edith.divisaviyafinalproject.R.layout.request_layout;

public class AddService extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText serviceTopic, serviceDescription, serviceRemark, servicePrice, serviceKeywords;
    ImageView serviceImage;
    Spinner serviceCategory;
    Button serviceAdd;
    TextView topic, category, description, remark, price;
    String appTitle = "";

    Uri serviceImagePath;

    String serviceID = "";

    ArraySets arraySets = new ArraySets();

    FirebaseFirestore serviceStore = FirebaseFirestore.getInstance();

    public String sTopic = "";
    public String sCategory = "";
    public String sDetails = "";
    public String sPrice = "";
    public String sImage = "";
    public String sRemarks = "";
    public String sKeywords = "";

    public String uName = "";
    public String uTelephone = "";
    public String uAddress = "";
    public String uOccupation = "";
    public Double uLat;
    public Double uLng;
    private String uState = "";
    private Double uRate;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        serviceTopic = findViewById(R.id.service_topic);
        serviceCategory = findViewById(R.id.service_category);
        serviceDescription = findViewById(R.id.service_description);
        serviceRemark = findViewById(R.id.service_remark);
        servicePrice = findViewById(R.id.service_price);
        serviceKeywords = findViewById(R.id.service_keywords);
        serviceImage = findViewById(R.id.service_image);

        serviceAdd = findViewById(R.id.service_add);

        topic = findViewById(R.id.topic);
        category = findViewById(R.id.category);
        description = findViewById(R.id.description);
        remark = findViewById(R.id.remark);
        price = findViewById(R.id.price);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(AddService.this, "si");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_service_topic));
            category.setText(resources.getString(R.string.add_service_category));
            description.setText(resources.getString(R.string.add_service_description));
            remark.setText(resources.getString(R.string.add_service_remark));
            price.setText(resources.getString(R.string.add_service_price));
            serviceAdd.setText(resources.getString(R.string.add_service_add));

            serviceTopic.setHint(resources.getString(R.string.add_service_topic));
            serviceDescription.setHint(resources.getString(R.string.add_service_description));
            servicePrice.setHint(resources.getString(R.string.add_service_price));
            serviceRemark.setHint(resources.getString(R.string.add_service_remark));

            appTitle = resources.getString(R.string.add_service_appbar);


        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(AddService.this, "en");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_service_topic));
            category.setText(resources.getString(R.string.add_service_category));
            description.setText(resources.getString(R.string.add_service_description));
            remark.setText(resources.getString(R.string.add_service_remark));
            price.setText(resources.getString(R.string.add_service_price));
            serviceAdd.setText(resources.getString(R.string.add_service_add));

            serviceTopic.setHint(resources.getString(R.string.add_service_topic));
            serviceDescription.setHint(resources.getString(R.string.add_service_description));
            servicePrice.setHint(resources.getString(R.string.add_service_price));
            serviceRemark.setHint(resources.getString(R.string.add_service_remark));

            appTitle = resources.getString(R.string.add_service_appbar);

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(AddService.this, "ta");
            resources = context.getResources();

            topic.setText(resources.getString(R.string.add_service_topic));
            category.setText(resources.getString(R.string.add_service_category));
            description.setText(resources.getString(R.string.add_service_description));
            remark.setText(resources.getString(R.string.add_service_remark));
            price.setText(resources.getString(R.string.add_service_price));
            serviceAdd.setText(resources.getString(R.string.add_service_add));

            serviceTopic.setHint(resources.getString(R.string.add_service_topic));
            serviceDescription.setHint(resources.getString(R.string.add_service_description));
            servicePrice.setHint(resources.getString(R.string.add_service_price));
            serviceRemark.setHint(resources.getString(R.string.add_service_remark));

            appTitle = resources.getString(R.string.add_service_appbar);
        }
//--------------------------------------------------------------------------------------------------

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        serviceID = USER_ID + "_" + System.currentTimeMillis() + "";

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
        serviceCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Service);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceCategory.setAdapter(adapter);
        //------------------------------------------------------------------------------------------
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        serviceID = USER_ID + "_" + System.currentTimeMillis() + "";

        uName = userPreferences.getString("USER_NAME", "");
        uTelephone = userPreferences.getString("USER_TELEPHONE", "");
        uAddress = userPreferences.getString("USER_ADDRESS", "");
        uOccupation = userPreferences.getString("USER_OCCUPATION", "");
        uState = userPreferences.getString("USER_STATE", "");
        uLat = Double.valueOf(userPreferences.getString("USER_LAT", ""));
        uLng = Double.valueOf(userPreferences.getString("USER_LNG", ""));
        uRate = Double.valueOf(userPreferences.getString("USER_RATE", ""));
        Log.d("User Product", uName + "  " + uTelephone + " " + uAddress + " " + uOccupation + " " + uLng.toString() + " " + uLat.toString() + " " + uRate.toString());

    }

    public void imageUpload(View view) {
        //TODO: upload image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    public void productAdd(View view) {
        //TODO: Add Product
        if (!checkEmptyEditText()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            uploadImage();
        }
    }

    public boolean checkEmptyEditText() {
        sTopic = serviceTopic.getText().toString();
        sDetails = serviceDescription.getText().toString();
        sRemarks = serviceRemark.getText().toString();
        sPrice = servicePrice.getText().toString();
        sKeywords = serviceKeywords.getText().toString();

        if (!sTopic.isEmpty() && !sDetails.isEmpty() && !sRemarks.isEmpty() && !sPrice.isEmpty() && !sKeywords.isEmpty() && !sCategory.equals("Select Service")) {
            return true;
        } else {
            return false;
        }
    }

    public void uploadImage() {
        //Progress Window
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        //Fire Storage Path
        StorageReference storageReference = storage.getReference("ServiceImages").child(uTelephone);
        StorageReference reference = storageReference.child(serviceID);

        //upload
        reference.putFile(serviceImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uriImage = uriTask.getResult();
                String uri = uriImage.toString();
                sImage = uri;
                Log.d("User Image URI", sImage);

                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                saveService();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddService.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
                Log.d("Image upload Error", e.toString());
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercentage = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setProgress((int) progressPercentage);
            }
        });
    }

    public void saveService() {
        DocumentReference productRef = serviceStore.collection("MyStore").document(uTelephone).collection("MyServices").document(serviceID);
        DocumentReference reference = serviceStore.document("Services/" + serviceID);

        ServiceInformation information = new ServiceInformation(serviceID, sTopic, sCategory, sDetails, sPrice, sImage, sRemarks, sKeywords, uName, uTelephone, uAddress, uOccupation, uState, uLat, uLng, uRate);

        productRef.set(information).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddService.this, "Service Added Successfully", Toast.LENGTH_SHORT).show();
                reference.set(information);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddService.this, MyShop.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                @SuppressLint("ResourceType") View view = findViewById(activity_add_product);
                Snackbar.make(view, "Service Not Added Successfully", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            serviceImagePath = data.getData();
            serviceImage.setImageURI(serviceImagePath);
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
    public void onPointerCaptureChanged(boolean hasCapture) {

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
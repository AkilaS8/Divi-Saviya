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
import com.edith.divisaviyafinalproject.Details.ProductInformation;
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
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;

public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText productName, productPrice, productQty, productRemark;
    ImageView productImage;
    Spinner productCategory, productAvailable, productOrganic;
    TextView name, category, organic, price, qty, available, remark;
    Button addProduct;

    Uri productImagePath;

    String productID = "";

    ArraySets arraySets = new ArraySets();

//    SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
//    String USER_ID = userPreferences.getString("USER_TELEPHONE", "");

    FirebaseFirestore productStore = FirebaseFirestore.getInstance();

    public String appTitle = "";
    public String pName = "";
    public String pCategory = "";
    public String pOrganic = "";
    public String pPrice = "";
    public String pQty = "";
    public String pAvailable = "";
    public String pRemark = "";
    public String pImage = "";

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
        setContentView(activity_add_product);

        productName = findViewById(R.id.product_name);
        productCategory = findViewById(R.id.product_category);
        productOrganic = findViewById(R.id.product_organic);
        productPrice = findViewById(R.id.product_price);
        productQty = findViewById(R.id.product_qty);
        productRemark = findViewById(R.id.product_remark);
        productAvailable = findViewById(R.id.product_available);
        productImage = findViewById(R.id.product_image);

        addProduct = findViewById(R.id.product_add);

        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        organic = findViewById(R.id.organic);
        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        remark = findViewById(R.id.remark);
        available = findViewById(R.id.available);

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        productID = USER_ID + "_" + System.currentTimeMillis() + "";

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(AddProduct.this, "si");
            resources = context.getResources();

            name.setText(resources.getString(R.string.add_product_name));
            category.setText(resources.getString(R.string.add_product_category));
            organic.setText(resources.getString(R.string.add_product_organic));
            price.setText(resources.getString(R.string.add_product_price));
            qty.setText(resources.getString(R.string.add_product_qty));
            remark.setText(resources.getString(R.string.add_product_remark));
            available.setText(resources.getString(R.string.add_product_available));

            addProduct.setText(resources.getString(R.string.add_product_add));

            productName.setHint(resources.getString(R.string.add_product_name));
            productPrice.setHint(resources.getString(R.string.add_product_price));
            productQty.setHint(resources.getString(R.string.add_product_qty));
            productRemark.setHint(resources.getString(R.string.add_product_remark));

            appTitle = resources.getString(R.string.add_product_appbar);

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(AddProduct.this, "en");
            resources = context.getResources();

            name.setText(resources.getString(R.string.add_product_name));
            category.setText(resources.getString(R.string.add_product_category));
            organic.setText(resources.getString(R.string.add_product_organic));
            price.setText(resources.getString(R.string.add_product_price));
            qty.setText(resources.getString(R.string.add_product_qty));
            remark.setText(resources.getString(R.string.add_product_remark));
            available.setText(resources.getString(R.string.add_product_available));

            addProduct.setText(resources.getString(R.string.add_product_add));

            productName.setHint(resources.getString(R.string.add_product_name));
            productPrice.setHint(resources.getString(R.string.add_product_price));
            productQty.setHint(resources.getString(R.string.add_product_qty));
            productRemark.setHint(resources.getString(R.string.add_product_remark));

            appTitle = resources.getString(R.string.add_product_appbar);

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(AddProduct.this, "ta");
            resources = context.getResources();

            name.setText(resources.getString(R.string.add_product_name));
            category.setText(resources.getString(R.string.add_product_category));
            organic.setText(resources.getString(R.string.add_product_organic));
            price.setText(resources.getString(R.string.add_product_price));
            qty.setText(resources.getString(R.string.add_product_qty));
            remark.setText(resources.getString(R.string.add_product_remark));
            available.setText(resources.getString(R.string.add_product_available));

            addProduct.setText(resources.getString(R.string.add_product_add));

            productName.setHint(resources.getString(R.string.add_product_name));
            productPrice.setHint(resources.getString(R.string.add_product_price));
            productQty.setHint(resources.getString(R.string.add_product_qty));
            productRemark.setHint(resources.getString(R.string.add_product_remark));

            appTitle = resources.getString(R.string.add_product_appbar);
        }
//--------------------------------------------------------------------------------------------------

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
        productCategory.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);

        //Organic
        productOrganic.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Organic);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productOrganic.setAdapter(adapter1);

        //Available
        productAvailable.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.Status);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productAvailable.setAdapter(adapter2);
        //------------------------------------------------------------------------------------------

    }

    public void imageUpload(View view) {
        //TODO: upload image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            productImagePath = data.getData();
            productImage.setImageURI(productImagePath);
        }
    }

    public void productAdd(View view) {
        //TODO: Add Product
        if (!checkEmptyEditText()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            uploadImage();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        productID = USER_ID + "_" + System.currentTimeMillis() + "";

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

    public void saveProduct() {

        DocumentReference productRef = productStore.collection("MyStore").document(uTelephone).collection("MyProducts").document(productID);
        DocumentReference reference = productStore.document("Products/" + productID);

        ProductInformation information = new ProductInformation(productID, pName, pCategory, pOrganic, pPrice, pQty, pAvailable, pRemark, pImage, uName, uTelephone, uAddress, uOccupation, uState, uLat, uLng, uRate);

        productRef.set(information).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                reference.set(information);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(AddProduct.this, MyShop.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                @SuppressLint("ResourceType") View view = findViewById(activity_add_product);
                Snackbar.make(view, "Product Not Added Successfully", Snackbar.LENGTH_LONG).show();
                //Toast.makeText(AddProduct.this, "Product Not Added !!!", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void uploadImage() {
        //Progress Window
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        //Fire Storage Path
        StorageReference storageReference = storage.getReference("ProductImages").child(uTelephone);
        StorageReference reference = storageReference.child(productID);

        //upload
        reference.putFile(productImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uriImage = uriTask.getResult();
                String uri = uriImage.toString();
                pImage = uri;
                Log.d("User Image URI", pImage);

                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                saveProduct();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddProduct.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
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
package com.edith.divisaviyafinalproject.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ProfileInformation;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserRegistration extends AppCompatActivity implements LocationListener {

    private static final String TAG = "UserRegistration";

    EditText userName, userTelephone, userAddress, userOccupation, userPassword, userRePassword;
    Button userRegistration;
    ImageView userImage;
    TextView name, telephone, address, occupation, password, repassword;

    Uri userImagePath;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference("UserImages");

    LocationManager locationManager;

    public String uName = "";
    public String uTelephone = "";
    public String uAddress = "";
    public String uOccupation = "";
    public String uPassword = "";
    public String uRePassword = "";
    public String uImageURI = "";
    public Double uLat;
    public Double uLng;
    private String uState = "";
    private Double uRate = 0.00;

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;


    FirebaseFirestore registrationStore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);


        //-----------------------App Bar------------------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //------------------------------------------------------------------------------------------

        //------------Get the Current Location------------------------------------------------------
        grantPermission();
        checkLocationEnableOrNot();
        getLocation();
        //------------------------------------------------------------------------------------------

        userName = findViewById(R.id.user_name);
        userTelephone = findViewById(R.id.user_telephone);
        userAddress = findViewById(R.id.user_address);
        userOccupation = findViewById(R.id.user_occupation);
        userImage = findViewById(R.id.user_image);
        userPassword = findViewById(R.id.user_password);
        userRePassword = findViewById(R.id.user_re_enter_password);
        userRegistration = findViewById(R.id.user_registration);

        name = findViewById(R.id.name);
        telephone = findViewById(R.id.telephone);
        address = findViewById(R.id.address);
        occupation = findViewById(R.id.occupation);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(UserRegistration.this, "si");
            resources = context.getResources();

            name.setText(resources.getString(R.string.reg_name));
            telephone.setText(resources.getString(R.string.reg_telephone));
            address.setText(resources.getString(R.string.reg_address));
            occupation.setText(resources.getString(R.string.reg_occupation));
            password.setText(resources.getString(R.string.reg_password));
            repassword.setText(resources.getString(R.string.reg_repassword));

            userName.setHint(resources.getString(R.string.reg_name_hint));
            userTelephone.setHint(resources.getString(R.string.reg_telephone_hint));
            userAddress.setHint(resources.getString(R.string.reg_address_hint));
            userOccupation.setHint(resources.getString(R.string.reg_occupation_hint));
            userPassword.setHint(resources.getString(R.string.reg_password_hint));
            userRePassword.setHint(resources.getString(R.string.reg_repassword));

            userRegistration.setText(resources.getString(R.string.reg_register));


        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(UserRegistration.this, "en");
            resources = context.getResources();

            name.setText(resources.getString(R.string.reg_name));
            telephone.setText(resources.getString(R.string.reg_telephone));
            address.setText(resources.getString(R.string.reg_address));
            occupation.setText(resources.getString(R.string.reg_occupation));
            password.setText(resources.getString(R.string.reg_password));
            repassword.setText(resources.getString(R.string.reg_repassword));

            userName.setHint(resources.getString(R.string.reg_name_hint));
            userTelephone.setHint(resources.getString(R.string.reg_telephone_hint));
            userAddress.setHint(resources.getString(R.string.reg_address_hint));
            userOccupation.setHint(resources.getString(R.string.reg_occupation_hint));
            userPassword.setHint(resources.getString(R.string.reg_password_hint));
            userRePassword.setHint(resources.getString(R.string.reg_repassword));

            userRegistration.setText(resources.getString(R.string.reg_register));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(UserRegistration.this, "ta");
            resources = context.getResources();

            name.setText(resources.getString(R.string.reg_name));
            telephone.setText(resources.getString(R.string.reg_telephone));
            address.setText(resources.getString(R.string.reg_address));
            occupation.setText(resources.getString(R.string.reg_occupation));
            password.setText(resources.getString(R.string.reg_password));
            repassword.setText(resources.getString(R.string.reg_repassword));

            userName.setHint(resources.getString(R.string.reg_name_hint));
            userTelephone.setHint(resources.getString(R.string.reg_telephone_hint));
            userAddress.setHint(resources.getString(R.string.reg_address_hint));
            userOccupation.setHint(resources.getString(R.string.reg_occupation_hint));
            userPassword.setHint(resources.getString(R.string.reg_password_hint));
            userRePassword.setHint(resources.getString(R.string.reg_repassword));

            userRegistration.setText(resources.getString(R.string.reg_register));

        }
//--------------------------------------------------------------------------------------------------
    }

    public void userRegistration(View view) {

        if (!checkEmptyEditText()) {
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            if (checkPasswordMatch()) {
                userPassword.setText("");
                userRePassword.setText("");
                userPassword.requestFocus();
                Toast.makeText(this, "Password not matched.", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();

            }

        }
    }

    //---------------------Registration details-----------------------------------------------------
    public void saveDetails() {

        DocumentReference registrationRef = registrationStore.document("User/" + uTelephone);

        ProfileInformation information = new ProfileInformation(uName, uTelephone, uAddress, uOccupation, uPassword, uImageURI, uState, uLat, uLng, uRate);

        registrationRef.set(information).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                userName.setText("");
//                userTelephone.setText("");
//                userAddress.setText("");
//                userOccupation.setText("");
//                userPassword.setText("");
//                userRePassword.setText("");
//                userName.requestFocus();
//
//                userImagePath = null;
//                imageSuccess = 2;


                Toast.makeText(UserRegistration.this, "Your Registration is Successful !", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(UserRegistration.this, UserRegistration.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserRegistration.this, "Your Registration is Unsuccessful !", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

    private boolean checkEmptyEditText() {
        uName = userName.getText().toString();
        uTelephone = userTelephone.getText().toString();
        uAddress = userAddress.getText().toString();
        uOccupation = userOccupation.getText().toString();
        uPassword = userPassword.getText().toString();
        uRePassword = userRePassword.getText().toString();

        if (!uName.isEmpty() && !uTelephone.isEmpty() && !uAddress.isEmpty() && !uOccupation.isEmpty() && !uPassword.isEmpty() && !uRePassword.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPasswordMatch() {
        if (!uPassword.equals(uRePassword)) {
            return true;
        } else {
            return false;
        }
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
            userImagePath = data.getData();
            userImage.setImageURI(userImagePath);
        }
    }

    private void uploadImage() {

        //Progress Window
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        //Fire Storage Path
        StorageReference reference = storageReference.child(uTelephone);

        //uploading image
        reference.putFile(userImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uriImage = uriTask.getResult();
                String uri = uriImage.toString();
                uImageURI = uri;
                Log.d("User Image URI", uImageURI);

                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                saveDetails();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UserRegistration.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
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
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //---------------------------- Get location ----------------------------------------------------
    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationEnableOrNot() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnable = false;
        boolean networkEnable = false;

        try {
            gpsEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            networkEnable = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnable && !networkEnable) {
            new AlertDialog.Builder(UserRegistration.this).setTitle("Enable GPS Service").setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null).show();
        }
    }

    private void grantPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            uState = address.get(0).getLocality();
            uLat = location.getLatitude();
            uLng = location.getLongitude();

            Log.d("Sate------------", uState);
            Log.d("lat--------------------", String.valueOf(location.getLatitude()));
            Log.d("lng--------------", String.valueOf(location.getLongitude()));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
package com.edith.divisaviyafinalproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ProfileInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile extends AppCompatActivity {
    AlertDialog.Builder dialogBuilder;
    AlertDialog telephoneDialog, passwordDialog;

    ToggleButton profileEdit;
    EditText profileName, profileAddress, profileOccupation;
    TextView profileTelephone, profilePassword;
    TextView profileTopicName, profileTopicOccupation, profileTopicRate;
    ImageView profileImage;

    TextView name, telephone, address, occupation, password;

    FirebaseFirestore registrationStore = FirebaseFirestore.getInstance();

    Uri userImagePath;

    String pName, pAddress, pTelephone, pOccupation, pImage;

    String USER_ID ="";
    String appTitle = "";

    FirebaseStorage storage = FirebaseStorage.getInstance();

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileEdit = findViewById(R.id.profile_edit);
        profileName = findViewById(R.id.profile_name_edit);
        profileAddress = findViewById(R.id.profile_address);
        profileOccupation = findViewById(R.id.profile_occupation_edit);
        profileTelephone = findViewById(R.id.profile_telephone);
        profilePassword = findViewById(R.id.profile_password_edit);
        profileImage = findViewById(R.id.profile_image);

        name = findViewById(R.id.name);
        telephone = findViewById(R.id.telephone);
        address = findViewById(R.id.address);
        occupation = findViewById(R.id.occupation);
        password = findViewById(R.id.password);

        profileTopicName = findViewById(R.id.profile_name);
        profileTopicOccupation = findViewById(R.id.profile_occupation);
        profileTopicRate = findViewById(R.id.profile_rank);

        //Disable Click
        profileTelephone.setClickable(false);
        profilePassword.setClickable(false);
        profileImage.setClickable(false);
        profileName.setEnabled(false);
        profileAddress.setEnabled(false);
        profileOccupation.setEnabled(false);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(Profile.this, "si");
            resources = context.getResources();

            name.setText(resources.getString(R.string.my_profile_name));
            telephone.setText(resources.getString(R.string.my_profile_telephone));
            address.setText(resources.getString(R.string.my_profile_address));
            occupation.setText(resources.getString(R.string.my_profile_occupation));
            password.setText(resources.getString(R.string.my_profile_password));
            profilePassword.setText(resources.getString(R.string.my_profile_password_change));

            appTitle = resources.getString(R.string.my_profile_appbar);


        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(Profile.this, "en");
            resources = context.getResources();

            name.setText(resources.getString(R.string.my_profile_name));
            telephone.setText(resources.getString(R.string.my_profile_telephone));
            address.setText(resources.getString(R.string.my_profile_address));
            occupation.setText(resources.getString(R.string.my_profile_occupation));
            password.setText(resources.getString(R.string.my_profile_password));
            profilePassword.setText(resources.getString(R.string.my_profile_password_change));

            appTitle = resources.getString(R.string.my_profile_appbar);

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(Profile.this, "ta");
            resources = context.getResources();

            name.setText(resources.getString(R.string.my_profile_name));
            telephone.setText(resources.getString(R.string.my_profile_telephone));
            address.setText(resources.getString(R.string.my_profile_address));
            occupation.setText(resources.getString(R.string.my_profile_occupation));
            password.setText(resources.getString(R.string.my_profile_password));
            profilePassword.setText(resources.getString(R.string.my_profile_password_change));

            appTitle = resources.getString(R.string.my_profile_appbar);

        }
//--------------------------------------------------------------------------------------------------

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle(appTitle);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0CB856"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------



        //----------------------------------Toggle Button-------------------------------------------
        profileEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //Enable Click
                    profileTelephone.setClickable(true);
                    profilePassword.setClickable(true);
//                    profileImage.setClickable(true);
                    profileName.setEnabled(true);
                    profileAddress.setEnabled(true);
                    profileOccupation.setEnabled(true);


                }else{
                    //Disable Click
                    profileTelephone.setClickable(false);
                    profilePassword.setClickable(false);
                    profileImage.setClickable(false);
                    profileName.setEnabled(false);
                    profileAddress.setEnabled(false);
                    profileOccupation.setEnabled(false);

                    //TODO: save part
                    if (!checkEmptyEditText()) {
                        Toast.makeText(Profile.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {

//                        uploadImage();

                        DocumentReference updateProfile = registrationStore.document("User/" + USER_ID);

                        updateProfile.update("userName",pName);
                        updateProfile.update("userAddress",pAddress);
                        updateProfile.update("userOccupation",pOccupation);
//                        updateProfile.update("userImage",pImage);
                        Toast.makeText(Profile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void uploadImage() {

        StorageReference storageReference = storage.getReference("UserImages");

        //Progress Window
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.show();

        //Fire Storage Path
        StorageReference reference = storageReference.child(pTelephone);

        //uploading image
        reference.putFile(userImagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

                DocumentReference updateProfile = registrationStore.document("User/" + USER_ID);

//                updateProfile.update("userName",pName);
//                updateProfile.update("userAddress",pAddress);
//                updateProfile.update("userOccupation",pOccupation);
//                updateProfile.update("userImage",pImage);
                Toast.makeText(Profile.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
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

    private boolean checkEmptyEditText() {
        pName = profileName.getText().toString();
        pTelephone = profileTelephone.getText().toString();
        pAddress = profileAddress.getText().toString();
        pOccupation = profileOccupation.getText().toString();

        if (!pName.isEmpty() && !pTelephone.isEmpty() && !pAddress.isEmpty() && !pOccupation.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void telephoneEdit (View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup = getLayoutInflater().inflate(R.layout.telephone_edit_popup,null);

        dialogBuilder.setView(popup);
        telephoneDialog = dialogBuilder.create();
        telephoneDialog.show();
    }

    public void passwordEdit (View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup = getLayoutInflater().inflate(R.layout.password_edit_popup,null);

        dialogBuilder.setView(popup);
        passwordDialog = dialogBuilder.create();
        passwordDialog.show();
    }

    public void imageEdit(View view){
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
            profileImage.setImageURI(userImagePath);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences userPreferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        USER_ID = userPreferences.getString("USER_TELEPHONE","");

        DocumentReference updateProfile = registrationStore.document("User/" + USER_ID);

        updateProfile.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(Profile.this, "Error Loading", Toast.LENGTH_SHORT).show();
                    Log.d("Error Loading", error.toString());
                    return;
                }

                if (value.exists()) {
                    ProfileInformation information = value.toObject(ProfileInformation.class);

                    profileName.setText(information.getUserName().toString());
                    profileTelephone.setText(information.getUserTelephone().toString());
                    profileAddress.setText(information.getUserAddress().toString());
                    profileOccupation.setText(information.getUserOccupation().toString());

                    profileTopicName.setText(information.getUserName().toString());
                    profileTopicOccupation.setText(information.getUserOccupation().toString());
                    profileTopicRate.setText(information.getUserRate().toString());

                    if (!information.getUserImage().toString().isEmpty() || !information.getUserImage().equals(null)){
                        Glide.with(Profile.this).load(information.getUserImage().toString()).into(profileImage);
                    }

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.language_item:
                Intent intent = new Intent(Profile.this, LanguageChange.class);
                startActivity(intent);
                return true;
            case R.id.logout_item:
                Intent intent1 = new Intent(Profile.this, Login.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
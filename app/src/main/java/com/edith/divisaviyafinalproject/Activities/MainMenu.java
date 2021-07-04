package com.edith.divisaviyafinalproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ProfileInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    CardView explore, profile, help, guide, request, myShop, categories;
    TextView exploreT, profileT, helpT, guideT, requestT, myShopT, categoryT;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProfileInformation information;


    SharedPreferences userPreferences;
    String userName, userTelephone, userAddress, userOccupation, userPassword, userImage, userState, userLat, userLng, userRate;

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        explore = findViewById(R.id.explore);
        profile = findViewById(R.id.profile);
        help = findViewById(R.id.help);
        guide = findViewById(R.id.guide);
        request = findViewById(R.id.request);
        myShop = findViewById(R.id.myShop);
        categories = findViewById(R.id.categories);

        exploreT = findViewById(R.id.explore_txt);
        profileT = findViewById(R.id.profile_txt);
        guideT = findViewById(R.id.guide_txt);
        helpT = findViewById(R.id.help_txt);
        myShopT = findViewById(R.id.myshope_txt);
        categoryT = findViewById(R.id.category_txt);
        requestT = findViewById(R.id.request_txt);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(MainMenu.this, "si");
            resources = context.getResources();

            exploreT.setText(resources.getString(R.string.main_explore));
            profileT.setText(resources.getString(R.string.main_profile));
            helpT.setText(resources.getString(R.string.main_help));
            guideT.setText(resources.getString(R.string.main_guide));
            requestT.setText(resources.getString(R.string.main_request));
            myShopT.setText(resources.getString(R.string.main_myshop));
            categoryT.setText(resources.getString(R.string.main_category));

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(MainMenu.this, "en");
            resources = context.getResources();

            exploreT.setText(resources.getString(R.string.main_explore));
            profileT.setText(resources.getString(R.string.main_profile));
            helpT.setText(resources.getString(R.string.main_help));
            guideT.setText(resources.getString(R.string.main_guide));
            requestT.setText(resources.getString(R.string.main_request));
            myShopT.setText(resources.getString(R.string.main_myshop));
            categoryT.setText(resources.getString(R.string.main_category));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(MainMenu.this, "ta");
            resources = context.getResources();

            exploreT.setText(resources.getString(R.string.main_explore));
            profileT.setText(resources.getString(R.string.main_profile));
            helpT.setText(resources.getString(R.string.main_help));
            guideT.setText(resources.getString(R.string.main_guide));
            requestT.setText(resources.getString(R.string.main_request));
            myShopT.setText(resources.getString(R.string.main_myshop));
            categoryT.setText(resources.getString(R.string.main_category));

        }
//--------------------------------------------------------------------------------------------------

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                Animatoo.animateFade(MainMenu.this);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Profile.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Help.class);
                startActivity(intent);
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Guide.class);
                startActivity(intent);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, RequestMain.class);
                startActivity(intent);
            }
        });

        myShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, MyShop.class);
                startActivity(intent);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Categories.class);
                startActivity(intent);
            }
        });

//-------------------------------------Full Screen and hide app bar---------------------------------
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
//--------------------------------------------------------------------------------------------------


    }

    @Override
    protected void onStart() {
        super.onStart();

        //-----Getting User Details
        try {
            String UserID = getIntent().getStringExtra("USER_ID");

            DocumentReference reference = db.document("User/" + UserID);
            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        information = documentSnapshot.toObject(ProfileInformation.class);

                        userName = information.getUserName();
                        userTelephone = information.getUserTelephone();
                        userAddress = information.getUserAddress();
                        userOccupation = information.getUserOccupation();
                        userPassword = information.getUserPassword();
                        userImage = information.getUserImage();
                        userState = information.getUserState();
                        userLat = information.getUserLat().toString();
                        userLng = information.getUserLng().toString();
                        userRate = information.getUserRate().toString();

                        Log.d("User_Details", userName + "  " + userTelephone + " " + userAddress + " " + userOccupation + " " + userImage + " " + userLng + " " + userLat + " " + userRate);

                        userPreferences = getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor userEditor = userPreferences.edit();
                        userEditor.clear();

                        userEditor.putString("USER_NAME", userName);
                        userEditor.putString("USER_TELEPHONE", userTelephone);
                        userEditor.putString("USER_ADDRESS", userAddress);
                        userEditor.putString("USER_OCCUPATION", userOccupation);
                        userEditor.putString("USER_PASSWORD", userPassword);
                        userEditor.putString("USER_IMAGE", userImage);
                        userEditor.putString("USER_STATE", userState);
                        userEditor.putString("USER_LAT", userLat);
                        userEditor.putString("USER_LNG", userLng);
                        userEditor.putString("USER_RATE", userRate);
                        userEditor.commit();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error Loading User", e.toString());
                    Toast.makeText(MainMenu.this, "Error Loading User", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainMenu.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            Log.d("Error Loading User", e.toString());
            Toast.makeText(MainMenu.this, "Error Loading User", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainMenu.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(MainMenu.this, Profile.class);
        Animatoo.animateFade(this);
        startActivity(intent);
    }

    public void goToExplore(View view) {
        Intent intent = new Intent(MainMenu.this, MainActivity.class);

        startActivity(intent);

    }

    public void goToMyShop(View view) {
        Intent intent = new Intent(MainMenu.this, MyShop.class);
        Animatoo.animateFade(this);
        startActivity(intent);
    }
}
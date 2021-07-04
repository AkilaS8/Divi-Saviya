package com.edith.divisaviyafinalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edith.divisaviyafinalproject.Details.LoadingDialog;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ProfileInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    Button login;
    TextView register, forgot_password, dont;
    EditText userName, userPassword;

    LoadingDialog loadingDialog = new LoadingDialog(Login.this);

    String name, password;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProfileInformation information;

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.login_register_btn);
        login = findViewById(R.id.login_btn);
        userName = findViewById(R.id.login_telephone);
        userPassword = findViewById(R.id.login_password);
        forgot_password = findViewById(R.id.login_forgot_password);
        dont = findViewById(R.id.log_dont);

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

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(Login.this, "si");
            resources = context.getResources();

            userName.setHint(resources.getString(R.string.log_telephone));
            userPassword.setHint(resources.getString(R.string.log_password));
            forgot_password.setText(resources.getString(R.string.log_forgot));
            login.setText(resources.getString(R.string.log_login));
            dont.setText(resources.getString(R.string.log_account));
            register.setText(resources.getString(R.string.log_signup));

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(Login.this, "en");
            resources = context.getResources();

            userName.setHint(resources.getString(R.string.log_telephone));
            userPassword.setHint(resources.getString(R.string.log_password));
            forgot_password.setText(resources.getString(R.string.log_forgot));
            login.setText(resources.getString(R.string.log_login));
            dont.setText(resources.getString(R.string.log_account));
            register.setText(resources.getString(R.string.log_signup));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(Login.this, "ta");
            resources = context.getResources();

            userName.setHint(resources.getString(R.string.log_telephone));
            userPassword.setHint(resources.getString(R.string.log_password));
            forgot_password.setText(resources.getString(R.string.log_forgot));
            login.setText(resources.getString(R.string.log_login));
            dont.setText(resources.getString(R.string.log_account));
            register.setText(resources.getString(R.string.log_signup));

        }

//--------------------------------------------------------------------------------------------------

//------------------------------ First Launch ------------------------------------------------------
        SharedPreferences firstTime  = getSharedPreferences("FirstRun",MODE_PRIVATE);
        String first = firstTime.getString("FirstTimeInstall","Yes");

        if (first.equals("Yes")){
            Intent intent = new Intent(Login.this, SelectLanguage.class);
            startActivity(intent);
            finish();

            SharedPreferences.Editor editor = firstTime.edit();
            editor.putString("FirstTimeInstall","NO");
            editor.apply();
        }
//--------------------------------------------------------------------------------------------------



        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, UserRegistration.class);
                startActivity(intent);
                Log.d("Register Button", "Open Register Activity");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoading();
                if (isEmptyCheck()) {
                    try {
                        DocumentReference reference = db.document("User/" + name);
                        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    information = documentSnapshot.toObject(ProfileInformation.class);

                                    String fetchPassword = information.getUserPassword();
                                    String fetchUserID = information.getUserTelephone();

                                    //Passwords matching
                                    if (password.equals(fetchPassword)) {
                                        loadingDialog.dismissLoading();
                                        Toast.makeText(Login.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, MainMenu.class);
                                        intent.putExtra("USER_ID",fetchUserID);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        loadingDialog.dismissLoading();
                                        userName.setText("");
                                        userPassword.setText("");
                                        userName.requestFocus();
                                        Toast.makeText(Login.this, "Password or Username Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    loadingDialog.dismissLoading();
                                    userName.setText("");
                                    userPassword.setText("");
                                    userName.requestFocus();
                                    Toast.makeText(Login.this, "Password or Username Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismissLoading();
                                Toast.makeText(Login.this, "Error Login", Toast.LENGTH_SHORT).show();
                                Log.d("Login Error", e.toString());
                            }
                        });
                    } catch (Exception e) {
                        loadingDialog.dismissLoading();
                        Log.d("Login Error", e.toString());
                    }
                } else {
                    loadingDialog.dismissLoading();
                    Toast.makeText(Login.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isEmptyCheck() {
        name = userName.getText().toString();
        password = userPassword.getText().toString();

        if (!name.isEmpty() && !password.isEmpty()) {
            return true;
        } else {
            return false;
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
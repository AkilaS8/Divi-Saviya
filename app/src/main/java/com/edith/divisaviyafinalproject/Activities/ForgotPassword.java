package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.R;

public class ForgotPassword extends AppCompatActivity {

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    TextView forgot_text;
    Button submit;
    String APP_TITLE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //------Action bar settings ----------------------------------------------------------------
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(APP_TITLE);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(ForgotPassword.this, "si");
            resources = context.getResources();

            forgot_text.setText(resources.getString(R.string.forgot_txt));
            submit.setText(resources.getString(R.string.forgot_btn));
            APP_TITLE = (resources.getString(R.string.forgot_appbar));

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(ForgotPassword.this, "en");
            resources = context.getResources();

            forgot_text.setText(resources.getString(R.string.forgot_txt));
            submit.setText(resources.getString(R.string.forgot_btn));
            APP_TITLE = (resources.getString(R.string.forgot_appbar));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(ForgotPassword.this, "ta");
            resources = context.getResources();

            forgot_text.setText(resources.getString(R.string.forgot_txt));
            submit.setText(resources.getString(R.string.forgot_btn));
            APP_TITLE = (resources.getString(R.string.forgot_appbar));

        }

//--------------------------------------------------------------------------------------------------
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
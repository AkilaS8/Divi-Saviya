package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.R;

public class UserGuide extends AppCompatActivity {

    ImageView next, previous;
    TextView skip;
    ViewFlipper viewFlipper;

    TextView g1, g2, g3;

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        g1 = findViewById(R.id.guide1);
        g2 = findViewById(R.id.guide2);
        g3 = findViewById(R.id.guide3);

        //------------------------------------App bar and full screen-------------------------------
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

        viewFlipper = findViewById(R.id.view_flipper);
        next = findViewById(R.id.btn_next);
        previous = findViewById(R.id.btn_previous);
        skip = findViewById(R.id.btn_skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserGuide.this, Login.class);
                startActivity(intent);
                finish();
            }
        });


        //------------------------------------Language Change---------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(UserGuide.this, "si");
            resources = context.getResources();

            g1.setText(resources.getString(R.string.guide1));
            g2.setText(resources.getString(R.string.guide2));
            g3.setText(resources.getString(R.string.guide3));
            skip.setText(resources.getString(R.string.guide_skip));

        } else if (val.equals("English")) {
            context = LocaleHelper.setLocale(UserGuide.this, "en");
            resources = context.getResources();

            g1.setText(resources.getString(R.string.guide1));
            g2.setText(resources.getString(R.string.guide2));
            g3.setText(resources.getString(R.string.guide3));
            skip.setText(resources.getString(R.string.guide_skip));

        } else if (val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(UserGuide.this, "ta");
            resources = context.getResources();

            g1.setText(resources.getString(R.string.guide1));
            g2.setText(resources.getString(R.string.guide2));
            g3.setText(resources.getString(R.string.guide3));
            skip.setText(resources.getString(R.string.guide_skip));
        }
        //------------------------------------------------------------------------------------------
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.showPrevious();
    }

    public void previousView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }
}
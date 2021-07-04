package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ViewPageAdapterMyShop;
import com.edith.divisaviyafinalproject.Fragment.AllRequests;
import com.edith.divisaviyafinalproject.Fragment.MyProductsFragment;
import com.edith.divisaviyafinalproject.Fragment.MyRequests;
import com.edith.divisaviyafinalproject.Fragment.MyServicesFragment;
import com.edith.divisaviyafinalproject.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class RequestMain extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    String appTitle = "", request="", myrequest = "";

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_main);

        tabLayout = findViewById(R.id.tabView_mainRequest);
        viewPager = findViewById(R.id.pageViewer_mainRequest);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(RequestMain.this, "si");
            resources = context.getResources();

            appTitle = (resources.getString(R.string.request_appbar));
            request = (resources.getString(R.string.request_request));
            myrequest = (resources.getString(R.string.request_myrequest));

        }else if(val.equals("English")) {
            context = LocaleHelper.setLocale(RequestMain.this, "en");
            resources = context.getResources();

            appTitle = (resources.getString(R.string.request_appbar));
            request = (resources.getString(R.string.request_request));
            myrequest = (resources.getString(R.string.request_myrequest));

        }else if(val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(RequestMain.this, "ta");
            resources = context.getResources();

            appTitle = (resources.getString(R.string.request_appbar));
            request = (resources.getString(R.string.request_request));
            myrequest = (resources.getString(R.string.request_myrequest));
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

        ViewPageAdapterMyShop adapterMyShop = new ViewPageAdapterMyShop(getSupportFragmentManager());
        adapterMyShop.AddFragment(new AllRequests(), request);
        adapterMyShop.AddFragment(new MyRequests(), myrequest);

        viewPager.setAdapter(adapterMyShop);
        tabLayout.setupWithViewPager(viewPager);
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
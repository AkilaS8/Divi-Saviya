package com.edith.divisaviyafinalproject.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Details.ViewPageAdapterMyShop;
import com.edith.divisaviyafinalproject.Fragment.MyProductsFragment;
import com.edith.divisaviyafinalproject.Fragment.MyServicesFragment;
import com.edith.divisaviyafinalproject.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MyShop extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;

    TextView myShopTxt;
    String productTxt = "", serviceTxt = "",  TITLE = "";

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);

        tabLayout = findViewById(R.id.tabView_myProducts);
        appBarLayout = findViewById(R.id.appBarLayout);
        viewPager = findViewById(R.id.pageViewer_myProducts);
        myShopTxt = findViewById(R.id.myshop_txt);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(MyShop.this, "si");
            resources = context.getResources();

            myShopTxt.setText(resources.getString(R.string.myshop));
            productTxt = (resources.getString(R.string.myshop_product));
            serviceTxt = (resources.getString(R.string.myshop_service));
            TITLE = (resources.getString(R.string.myshop_appbar));

        }else if(val.equals("English")) {
            context = LocaleHelper.setLocale(MyShop.this, "en");
            resources = context.getResources();

            myShopTxt.setText(resources.getString(R.string.myshop));
            productTxt = (resources.getString(R.string.myshop_product));
            serviceTxt = (resources.getString(R.string.myshop_service));
            TITLE = (resources.getString(R.string.myshop_appbar));

        }else if(val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(MyShop.this, "ta");
            resources = context.getResources();

            myShopTxt.setText(resources.getString(R.string.myshop));
            productTxt = (resources.getString(R.string.myshop_product));
            serviceTxt = (resources.getString(R.string.myshop_service));
            TITLE = (resources.getString(R.string.myshop_appbar));
        }
//--------------------------------------------------------------------------------------------------

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle(TITLE);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0CB856"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        ViewPageAdapterMyShop adapterMyShop = new ViewPageAdapterMyShop(getSupportFragmentManager());
        adapterMyShop.AddFragment(new MyProductsFragment(), productTxt);
        adapterMyShop.AddFragment(new MyServicesFragment(), serviceTxt);

        viewPager.setAdapter(adapterMyShop);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
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
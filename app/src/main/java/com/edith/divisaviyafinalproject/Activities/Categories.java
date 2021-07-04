package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.Fragment.CategoryProductFragment;
import com.edith.divisaviyafinalproject.Fragment.CategoryServiceFragment;
import com.edith.divisaviyafinalproject.Fragment.MapsFragment;
import com.edith.divisaviyafinalproject.Fragment.ProductsFragment;
import com.edith.divisaviyafinalproject.Fragment.ServicesFragment;
import com.edith.divisaviyafinalproject.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Categories extends AppCompatActivity {
    ChipNavigationBar bottomMenuBar;
    Fragment fragment = null;

    Context context;
    Resources resources;
    SharedPreferences sharedPreferences;
    String TITLE_APP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        bottomMenuBar = findViewById(R.id.menuBar);

//--------------------------Language Change---------------------------------------------------------
        sharedPreferences = this.getSharedPreferences("selectLanguage", Context.MODE_PRIVATE);
        final String val = sharedPreferences.getString("select", "");

        if (val.equals("සිංහල")) {
            context = LocaleHelper.setLocale(Categories.this, "si");
            resources = context.getResources();

            TITLE_APP = resources.getString(R.string.category_appbar);

        }else if(val.equals("English")) {
            context = LocaleHelper.setLocale(Categories.this, "en");
            resources = context.getResources();

            TITLE_APP = resources.getString(R.string.category_appbar);

        }else if(val.equals("தமிழ்")) {
            context = LocaleHelper.setLocale(Categories.this, "ta");
            resources = context.getResources();

            TITLE_APP = resources.getString(R.string.category_appbar);

        }
//--------------------------------------------------------------------------------------------------

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle(TITLE_APP);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0CB856"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        //-------------------------------Menu bar Settings------------------------------------------
        bottomMenuBar.setItemSelected(R.id.product_menu, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new CategoryProductFragment()).commit();

        bottomMenuBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.service_menu:
                        fragment = new CategoryServiceFragment();
                        break;
                    case R.id.product_menu:
                        fragment = new CategoryProductFragment();
                        break;
                }

                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_container,fragment).commit();
                }
            }
        });
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
package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.edith.divisaviyafinalproject.Details.ViewPageAdapterGuide;
import com.edith.divisaviyafinalproject.Fragment.ArticalFragment;
import com.edith.divisaviyafinalproject.Details.ViewPageAdapterMyShop;
import com.edith.divisaviyafinalproject.Fragment.NewsFragment;
import com.edith.divisaviyafinalproject.R;
import com.edith.divisaviyafinalproject.Fragment.VideoFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class Guide extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle("Guide");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0CB856"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------

        tabLayout = findViewById(R.id.tabView_guide);
        appBarLayout = findViewById(R.id.appBarLayout);
        viewPager = findViewById(R.id.pageViewer_guide);

        ViewPageAdapterGuide adapterMyShop = new ViewPageAdapterGuide(getSupportFragmentManager());
        adapterMyShop.AddFragment(new NewsFragment(), "News");
        adapterMyShop.AddFragment(new VideoFragment(), "Videos");
        adapterMyShop.AddFragment(new ArticalFragment(), "Article");

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
package com.edith.divisaviyafinalproject.Activities;

import android.animation.Animator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.edith.divisaviyafinalproject.Fragment.MapsFragment;
import com.edith.divisaviyafinalproject.Fragment.ProductsFragment;
import com.edith.divisaviyafinalproject.Fragment.ServicesFragment;
import com.edith.divisaviyafinalproject.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar bottomMenuBar;
    Fragment fragment = null;
    View background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomMenuBar = findViewById(R.id.menuBar);
        background = findViewById(R.id.background);

        //-------Animation--------------------------------------------------------------------------
        if(savedInstanceState==null){
            background.setVisibility(View.INVISIBLE);
            final ViewTreeObserver viewTreeObserver= background.getViewTreeObserver();
            if (viewTreeObserver.isAlive()){
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        CircularReveal();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    }
                });
            }
        }


        //---------------------------------Action Bar Settings--------------------------------------
        // add back arrow to action bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set title
        getSupportActionBar().setTitle("");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#77EF6E"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);

        //set Image
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.topic);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT
                | Gravity.CENTER_VERTICAL);
        //layoutParams.leftMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        //------------------------------------------------------------------------------------------

        //-------------------------------Menu bar Settings------------------------------------------
        bottomMenuBar.setItemSelected(R.id.explore_menu, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new MapsFragment()).commit();

        bottomMenuBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.service_menu:
                        fragment = new ServicesFragment();
                        break;
                    case R.id.explore_menu:
                        fragment = new MapsFragment();
                        break;
                    case R.id.product_menu:
                        fragment = new ProductsFragment();
                        break;
                }

                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.view_container,fragment).commit();
                }
            }
        });
    }

    private void CircularReveal() {
        int cx=(int) background.getRight()-getDips(200);  // get dips agayan wenas karala madata ganda
        int cy =(int) background.getBottom()-getDips(300);
        float finalRadius=(float) Math.hypot(background.getWidth(),background.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(background,cx,cy,0,finalRadius);
        background.setVisibility(View.VISIBLE);
        anim.setDuration(2000);
        anim.start();
    }

    private int getDips(int i) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                i,
                resources.getDisplayMetrics()
        );
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
package com.edith.divisaviyafinalproject.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.LocaleHelper;
import com.edith.divisaviyafinalproject.R;

public class LanguageChange extends AppCompatActivity {

    Spinner spinner;
    TextView welcome, en, si, ta;
    Button next;

    ArraySets arraySets = new ArraySets();
    String value = null;

    boolean lang_select = true;
    Context context, contextEn, contextSn, contextTa;
    Resources resources;

    TextView textView;

    SharedPreferences languagePreferences;
    SharedPreferences.Editor lEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);

        //------Action bar settings ----------------------------------------------------------------
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Language");

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0CB856"));

        // Set BackgroundDrawable
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        //------------------------------------------------------------------------------------------


        spinner = findViewById(R.id.language_spinner_change);
        welcome = findViewById(R.id.language_welcome_change);
        next = findViewById(R.id.language_change);

        en = findViewById(R.id.select_en_change);
        si = findViewById(R.id.select_si_change);
        ta = findViewById(R.id.select_ta_change);

        languagePreferences = getSharedPreferences("selectLanguage",Context.MODE_PRIVATE);
        lEditor = languagePreferences.edit();

        final String languageValue = languagePreferences.getString("select","");

        //------------------------------------------------------------------------------------------
        contextEn = LocaleHelper.setLocale(LanguageChange.this,"en");
        resources = contextEn.getResources();
        en.setText(resources.getString(R.string.selectLanguage));

        contextSn = LocaleHelper.setLocale(LanguageChange.this,"si");
        resources = contextSn.getResources();
        si.setText(resources.getString(R.string.selectLanguage));


        contextTa = LocaleHelper.setLocale(LanguageChange.this,"ta");
        resources = contextTa.getResources();
        ta.setText(resources.getString(R.string.selectLanguage));
        //------------------------------------------------------------------------------------------


        //-------------------------------------spinner------------------------------------------------------
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySets.language);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value = arraySets.language[position];

                if (value.equals("Select Language")){
                    next.setEnabled(false);

                }else if (value.equals("English")){
                    context = LocaleHelper.setLocale(LanguageChange.this,"en");
                    resources = context.getResources();

                    welcome.setText(resources.getString(R.string.welcome));

                    lEditor.putString("select",value).commit();

                    next.setEnabled(true);

                }else if (value.equals("සිංහල")) {
                    context = LocaleHelper.setLocale(LanguageChange.this, "si");
                    resources = context.getResources();

                    welcome.setText(resources.getString(R.string.welcome));
                    lEditor.putString("select", value).commit();
                    next.setEnabled(true);

                } else if (value.equals("தமிழ்")) {
                    context = LocaleHelper.setLocale(LanguageChange.this, "ta");
                    resources = context.getResources();

                    welcome.setText(resources.getString(R.string.welcome));
                    lEditor.putString("select", value).commit();
                    next.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!next.isEnabled()){
                    Toast.makeText(LanguageChange.this, "Select Language first", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(LanguageChange.this,MainMenu.class);
                startActivity(intent);
                finish();
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
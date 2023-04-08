package com.example.homsi.psf;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;


public class ChooseSearchActivity extends AppCompatActivity {
    private Button all;
    private Button zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_search);
        all = findViewById(R.id.overall);
        zip= findViewById(R.id.zipsearch);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAll();
            }
        });
        zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchZip();
            }
        });
    }
    private void SearchAll()
    {
        Intent intent= new Intent(this,SearchListingActivity.class);
        startActivity(intent);
        finish();
    }
    private void SearchZip()
    {
        Intent intent= new Intent(this,SearchZipActivity.class);
        startActivity(intent);
        finish();
    }
}

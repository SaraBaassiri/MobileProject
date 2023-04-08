package com.example.homsi.psf;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseSpot extends AppCompatActivity {
    private Button editASpot;
    private Button oldSpot;
    private Button newSpot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_spot);

        editASpot = findViewById(R.id.editSpot);
        oldSpot= findViewById(R.id.useOldSpot);
        newSpot = findViewById(R.id.createSpotNew);


        editASpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEdit();
            }
        });

        oldSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startOld();
            }
        });

        newSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startNew();
            }
        });

    }
    private void startOld()
    {
        Intent intent= new Intent(this,ReuseParkingSpot.class);
        startActivity(intent);
        finish();
    }
    private void startEdit()
    {
        Intent intent= new Intent(this,EditParkingSpot.class);
        startActivity(intent);
        finish();
    }
    private void startNew()
    {
        Intent intent = new Intent(this,TimeActivity.class);
        startActivity(intent);
        finish();
    }
}

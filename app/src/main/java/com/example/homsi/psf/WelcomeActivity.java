package com.example.homsi.psf;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final Button login = findViewById(R.id.button2);
        login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        final Button register = findViewById(R.id.button3);
        register.setOnClickListener(v -> {
            Intent intent = new Intent( this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

package com.example.homsi.psf;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListingOverviewActivity extends AppCompatActivity {
    TextView parkingDetails;
    TextView details;
    TextView review;
    FirebaseUser user;
    String uid;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_overview);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        final String owner = getIntent().getStringExtra("Owner");
        final String listingHash = getIntent().getStringExtra("ListingHash");

        parkingDetails = findViewById(R.id.textView2);
        details = findViewById(R.id.textView7);
        review = findViewById(R.id.textView8);

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                intent.putExtra("owner", owner);
                intent.putExtra("listingHash", listingHash);
                startActivity(intent);
            }
        });
    }

}

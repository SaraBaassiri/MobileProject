package com.example.homsi.psf;

import android.content.Intent;
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

import java.util.ArrayList;

public class OtherUserProfileActivity extends AppCompatActivity{
    private TextView fName;
    private TextView lName;
    private TextView address;
    private TextView state;
    private TextView city;
    private TextView zip;
    private Button review;
    private FirebaseUser user;
    private String uid;
    private EditText reviewText;
    private String otherUserId;
    private static String keyString = " ";
    private ArrayList<String> reviewKeys = new ArrayList<>();
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        otherUserId = getIntent().getStringExtra("id");
        reviewText = findViewById(R.id.ReviewText);
        review = findViewById(R.id.submitReview);
        fName = findViewById(R.id.profName);
        lName = findViewById(R.id.profLastName);
        address = findViewById(R.id.profAddress);
        state = findViewById(R.id.profState);
        city = findViewById(R.id.profCity);
        zip = findViewById(R.id.profZip);

        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String str = reviewText.getText().toString();
                // TODO: User should be limited to one review per user to avoid spam from other users. need to fix.
                DatabaseReference key = dataRef.child("User Id: " + otherUserId).child("User Information").child("Ratings").child("Reviews").push();
                key.setValue(str);
                reviewKeys.add(key.getKey());
                System.out.println(reviewKeys.toString());
                keyString = key.getKey();

                System.out.println("This key was pushed to Database: " + key.getKey());
                System.out.println("User " + uid + " has reviewed user " + otherUserId);
                reviewText.setText(" ");
                CharSequence thanks = "Thank you! Your review has been submitted!";
                Toast.makeText(getApplicationContext(), thanks, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        DatabaseReference dref = dataRef.child("User Id: " + otherUserId).child("User Information");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address.setText(String.format("%s %s", "Address: ", dataSnapshot.child("Address").getValue()));
                fName.setText(String.format("%s %s", "First Name: ", dataSnapshot.child("First name").getValue()));
                lName.setText(String.format("%s %s", "Last Name: ", dataSnapshot.child("Last name").getValue()));
                state.setText(String.format("%s %s", "State: ", dataSnapshot.child("State").getValue()));
                city.setText(String.format("%s %s", "City: ", dataSnapshot.child("City").getValue()));
                zip.setText(String.format("%s %s", "Zip: ", dataSnapshot.child("Zip").getValue()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getKeys(){
        return keyString;
    }


}

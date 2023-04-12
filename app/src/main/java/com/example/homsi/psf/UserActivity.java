package com.example.homsi.psf;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {
    private Button signOutButton;
    private TextView helloUserText;
    private Button createListing;
    private Button profileButton;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button deleteListing;
    private Button searchListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        auth=FirebaseAuth.getInstance();
        deleteListing = (Button) findViewById(R.id.deleteButton);
        signOutButton = (Button) findViewById(R.id.signoutButton);
        createListing = (Button) findViewById(R.id.createButton);
        profileButton = (Button) findViewById(R.id.profile);
        helloUserText = (TextView) findViewById(R.id.emailText);
        searchListing = findViewById(R.id.searchButton);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(userIsValid(user)) {
                    helloUserText.setText(String.format("%s%s","Hello,\n", user.getEmail()));
                }
            }
        };

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutButton();
            }
        });
        createListing.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateListing();
            }
        }));
        deleteListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListingNow();
            }
        });
        searchListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearchListing();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile();
            }
        });
    }

    private void deleteListingNow(){
        Intent deleteIntent = new Intent(this, DeleteActivity.class);
        startActivity(deleteIntent);

    }
    private void profile(){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    private void startCreateListing() {
        Intent intent = new Intent(this, chooseSpot.class);
        startActivity(intent);
    }
    private void startSearchListing(){
        Intent intent= new Intent(this,SearchListingActivity.class);
        startActivity(intent);
    }
    private void signOutButton(){
        auth.signOut();
        startActivity(new Intent(UserActivity.this,WelcomeActivity.class));
        finish();

    }
    public boolean userIsValid(FirebaseUser user){
        return user != null;
    }
    protected void onResume()
    {
        super.onResume();
    }
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    public void onStop(){
        super.onStop();
        if(authListener!=null)
        {
            auth.removeAuthStateListener(authListener);
        }
    }
}


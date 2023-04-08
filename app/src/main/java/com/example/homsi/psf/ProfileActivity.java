package com.example.homsi.psf;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser user;
    private Button edit;
    private Button ChangeAccountInformation;
    private TextView fName;
    private TextView lName;
    private TextView address;
    private TextView state;
    private TextView city;
    private TextView zip;
    private ImageView profilePicture;
    private Button review;
    private DatabaseReference dref;
    private String uid;
    FirebaseStorage picture;
    StorageReference pictureReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        review = findViewById(R.id.MyReview);
        profilePicture=findViewById(R.id.profPic);
        edit = findViewById(R.id.editProf);
        fName = findViewById(R.id.profName);
        lName = findViewById(R.id.profLastName);
        address = findViewById(R.id.profAddress);
        state = findViewById(R.id.profState);
        city = findViewById(R.id.profCity);
        zip=findViewById(R.id.profZip);
        ChangeAccountInformation=findViewById(R.id.changeAccountInformation);
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();


        picture= FirebaseStorage.getInstance();
        pictureReference= picture.getReference();

            pictureReference.child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    // Pass it to Picasso to download, show in ImageView and caching
                    Glide.with(ProfileActivity.this).load(uri.toString()).into(profilePicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    System.out.println("No Profile Picture Found");
                }
            });

        dref=dataRef.child("User Id: " + uid).child("User Information");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName.setText(String.format("%s %s", "First Name: ", dataSnapshot.child("First name").getValue()));
                lName.setText(String.format("%s %s", "Last Name: ", dataSnapshot.child("Last name").getValue()));
                address.setText(String.format("%s %s", "Address: ", dataSnapshot.child("Address").getValue()));
                state.setText(String.format("%s %s", "State: ", dataSnapshot.child("State").getValue()));
                city.setText(String.format("%s %s", "City: ", dataSnapshot.child("City").getValue()));
                zip.setText(String.format("%s %s", "Zip: ", dataSnapshot.child("Zip").getValue()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ChangeAccountInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountInfoNow();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileNow();
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyReview();
            }
        });
    }
    private void editProfileNow(){

        Intent editProfIntent = new Intent(this,EditProfileActivity.class );
        startActivity(editProfIntent);
        finish();
    }
    private void editAccountInfoNow(){
        Intent editAccountIntent= new Intent(this, EditAccountActivity.class);
        startActivity(editAccountIntent);
        finish();
    }

    private void startMyReview(){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }


}

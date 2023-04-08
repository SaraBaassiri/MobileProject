package com.example.homsi.psf;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WritingActivity extends AppCompatActivity {
    EditText multiLine;
    Button submit;
    String review;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        multiLine = findViewById(R.id.editText);
        submit = findViewById(R.id.button4);

        final String owner = getIntent().getStringExtra("owner");
        final String listingHash = getIntent().getStringExtra("listingHash");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nae = user.getUid();
                review = multiLine.getText().toString();
                System.out.println(owner);
                System.out.println(listingHash);
                dataRef.child("User Id: " + owner).child(listingHash).child("Ratings").push().setValue(review + " by " + user.getEmail());
                dataRef.child(("User Id: ") + owner).child("User Information");
                dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String name = dataSnapshot.child(("User Id: ") + owner).child("User Information").getValue().toString();
                        //System.out.println("WritingActivity Class: " + name);
                        //System.out.println("WritingActivity Class: " + nameParser(name));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private String nameParser(String str){
        // parse first name
        int start = str.indexOf("=");
        int end = str.indexOf(",");

        // parse last name
        int first = str.indexOf("Last name=");
        int last = str.lastIndexOf(",");

        return str.substring(start + 1 , end) + " " + str.substring(first + 10 , last);
    }

}

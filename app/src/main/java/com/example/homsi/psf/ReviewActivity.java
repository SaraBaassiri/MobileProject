package com.example.homsi.psf;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    ListView reviews;
    ArrayList<String> array;
    FirebaseUser user;
    String uid;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviews = findViewById(R.id.ListviewReview);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        array = new ArrayList<>();

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //name = dataSnapshot.child(("User Id: ") + uid).child("User Information").getValue().toString();
                //System.out.println("WritingActivity Class: " + name);
                //System.out.println("WritingActivity Class: " + nameParser(name));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /*private void showData(DataSnapshot snapshot){
        array.clear();
        Iterable<DataSnapshot> children = snapshot.child("User Id: " + uid).child("User Information").child("Ratings").child("Reviews").getChildren();
        for(DataSnapshot ds : children){
            array.add(ds.getValue(String.class));
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        reviews.setAdapter(adapter);
    }*/

    private void showData(DataSnapshot snapshot){
        array.clear();
        Iterable<DataSnapshot> children = snapshot.child("User Id: " + uid).getChildren();
        for(DataSnapshot ds : children){
            //array.add(ds.getValue(String.class));
            if(!(ds.getKey().equals("User Information"))) {
                Iterable<DataSnapshot> ratings = snapshot.child("User Id: " + uid).child(ds.getKey()).child("Ratings").getChildren();

                for(DataSnapshot dss : ratings){
                    array.add(dss.getValue().toString());

                }
            }
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        reviews.setAdapter(adapter);
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

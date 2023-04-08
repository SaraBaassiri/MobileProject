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

public class MyBookingActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> array;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        listView = findViewById(R.id.Listview3);
        array = new ArrayList<>();
        dataRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.child("User Id: " + uid).child("Bookings").child("Address").getValue(String.class);
                String startTime = dataSnapshot.child("User Id: " + uid).child("Bookings").child("Start Time").getValue(String.class);
                String endTime = dataSnapshot.child("User Id: " + uid).child("Bookings").child("End Time").getValue(String.class);
                String startDate = dataSnapshot.child("User Id: " + uid).child("Bookings").child("Start Date").getValue(String.class);
                String endDate = dataSnapshot.child("User Id: " + uid).child("Bookings").child("End Date").getValue(String.class);

                array.add("Address: "+ address + "\n" + "Time: " + startTime + " - " + endTime + "\n" + "Date: " + startDate + " - " + endDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        array.add("My Booking Information");
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);
    }
}

package com.example.homsi.psf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class EditParkingSpot extends AppCompatActivity {
   private ListView mListView;
   private FirebaseUser user;
   private String uid;
   private String key;
   private ArrayList<String> array;
   private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parking_spot);

        myRef = FirebaseDatabase.getInstance().getReference();
        mListView = findViewById(R.id.editList);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            uid = user.getUid();
        }
        array = new ArrayList<>();

        key = createListing_Activity.getKey();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showData(final DataSnapshot dataSnapshot) {
        array.clear();
        final ArrayList<DataSnapshot> edit = new ArrayList<>();
        DataSnapshot userId = dataSnapshot.child("User Id: " + uid);
        final Iterable<DataSnapshot> listings = userId.getChildren();
        for (DataSnapshot ds : listings) {
            PSpot spot = ds.getValue(PSpot.class);
            if(spot.owner != null && spot.address != null) {
                array.add(spot.toString());
                edit.add(ds);
            }
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(EditParkingSpot.this);
                adb.setTitle("Edit Parking");
                adb.setMessage("Are you sure you want Edit your spot?");
                final DataSnapshot remove = edit.get(position);
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String editKey = remove.getKey();
                        Intent intent = new Intent(getApplicationContext(),changeSpotInformation.class);
                        intent.putExtra("PSpotKey",editKey);
                        startActivityForResult(intent,0);
                        adapter.notifyDataSetChanged();
                        finish();

                    }});
                adb.show();

            }
        });


    }

}

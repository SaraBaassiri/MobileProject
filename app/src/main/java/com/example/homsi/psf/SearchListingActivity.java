package com.example.homsi.psf;

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

public class SearchListingActivity extends AppCompatActivity {
    ListView mListView;
    FirebaseUser user;
    String uid;
    String key;
    ArrayList<String> array;
    ArrayList<String> mUsers;
    ArrayList<String> lKeys;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listing);

        mListView = findViewById(R.id.Listview);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            uid = user.getUid();
        }

        array = new ArrayList<>();
        mUsers = new ArrayList<>();
        lKeys = new ArrayList<>();
        key = createListing_Activity.getKey(); // gets the listing hash key

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array.clear();
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(final DataSnapshot dataSnapshot){
        array.clear();
        final ArrayList<DataSnapshot> bookings = new ArrayList<>();
        final ArrayList<DataSnapshot> ogInfo = new ArrayList<>();
        // Store users key in array list
        for(DataSnapshot d: dataSnapshot.getChildren()) {
            mUsers.add(String.valueOf(d.getKey()));
        }
        int i = 0;
        while(i < mUsers.size()) {
            DataSnapshot userId = dataSnapshot.child(mUsers.get(i));
            Iterable<DataSnapshot> listings = userId.getChildren();

            for (DataSnapshot ds : listings) {
                PSpot spot = ds.getValue(PSpot.class);
                if(spot.owner !=null && spot.availability && !spot.owner.equals(uid) && spot.address != null) {
                    array.add(spot.ownerToString());
                    bookings.add(ds);
                    System.out.println("Printing bookings in searchListing: " + bookings.toString());
                }

            }

            for(DataSnapshot dss : dataSnapshot.child("User Id: " + mUsers.get(i)).child(key).child("Original Information").getChildren()){
                //ogInfo.add(dss);
                //System.out.println(ogInfo);
            }
            i++;
        }

        final   ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
        mListView.setAdapter(adapter);
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchListingActivity.this);
                builder.setMessage("Would you like to Book now? or Visit User Profile")
                        .setCancelable(false)
                        .setPositiveButton("Book now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                AlertDialog.Builder adb=new AlertDialog.Builder(SearchListingActivity.this);
                                adb.setTitle("Book Parking Spot?");
                                adb.setMessage("Are you sure you want to book this parking spot");
                                final DataSnapshot update = bookings.get(i);
                                adb.setNegativeButton("Cancel", null);
                                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        update.child("availability").getRef().setValue(false);
                                        update.child("reserve").getRef().setValue(uid);
                                        int tocount = 0;
                                        tocount = Integer.parseInt(update.child("counter").getValue().toString());
                                        update.child("counter").getRef().setValue(tocount + 1);
                                        adapter.notifyDataSetChanged();

                                    }});
                                adb.show();
                            }

                        })

                        .setNegativeButton("Go to User Profile", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                String ownerId = getOwnerParser(array.get(i));
                                Intent otherUser = new Intent(getApplicationContext(), OtherUserProfileActivity.class);
                                otherUser.putExtra("id", ownerId);
                                startActivityForResult(otherUser, 0);
                            }
                        });
                builder.show();

            }
        });*/

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                String listingHash = getListingHashParser(bookings.get(i).toString());
                String listings = bookings.get(i).toString();
                String owner = getOwnerParser(bookings.get(i).toString());
                System.out.println("SearchListing " + owner);
                //System.out.println("SearchListing " + ogInfo.get(i).toString());
                Intent intent = new Intent(getApplicationContext(), ListingOverviewActivity.class);
                intent.putExtra("Booking", listings);
                intent.putExtra("Owner", owner);
                intent.putExtra("ListingHash", listingHash);
                intent.putExtra("DataSnapshot", listings);
                //intent.putExtra("sTime", null)
                startActivity(intent);
            }
        });
    }

    /**
     * helper method to parse string of owner to
     * retrieve id of other users
     * @param str
     * @return
     */
    private String getOwnerParser(String str){
        int start = str.indexOf("owner=");
        int end = str.indexOf("price=");

        return str.substring(start + 6, end - 2);
    }

    /**
     * helper method to parse string of Listing hash
     * @param str
     * @return
     */
    private String getListingHashParser(String str){
        int start = str.indexOf("listingHash=");
        int end = str.indexOf("owner=");

        return str.substring(start + 12, end - 2);
    }

}
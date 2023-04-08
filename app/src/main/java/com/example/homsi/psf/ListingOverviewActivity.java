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
    EditText toTime;
    EditText fromTime;
    EditText toDate;
    EditText fromDate;
    Button book;
    FirebaseUser user;
    String uid;
    DataSnapshot dataSnapshot;
    private String ogStartDate;
    private String ogEndDate;
    private String ogStartTime;
    private String ogEndTime;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    int toCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_overview);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        final String owner = getIntent().getStringExtra("Owner");
        final String listingHash = getIntent().getStringExtra("ListingHash");
        final String bookings = getIntent().getStringExtra("DataSnapshot");

        parkingDetails = findViewById(R.id.textView2);
        details = findViewById(R.id.textView7);
        review = findViewById(R.id.textView8);
        book = findViewById(R.id.button_book);
        toTime = findViewById(R.id.toTime);
        fromTime = findViewById(R.id.fromTime);
        toDate = findViewById(R.id.toDate);
        fromDate = findViewById(R.id.fromDate);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ogStartDate = dataSnapshot.child("User Id: " + owner).child(listingHash).child("Original Information").child("Start Date").getValue().toString();
                ogEndDate = dataSnapshot.child("User Id: " + owner).child(listingHash).child("Original Information").child("End Date").getValue().toString();
                ogStartTime = dataSnapshot.child("User Id: " + owner).child(listingHash).child("Original Information").child("Start Time").getValue().toString();
                ogEndTime = dataSnapshot.child("User Id: " + owner).child(listingHash).child("Original Information").child("End Time").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                intent.putExtra("owner", owner);
                intent.putExtra("listingHash", listingHash);
                startActivity(intent);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(bookings);
                System.out.println(counterParser(bookings));
                AlertDialog.Builder builder = new AlertDialog.Builder(ListingOverviewActivity.this);
                builder.setMessage("Are you sure you would like to Book now?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String counter = counterParser(bookings);
                                startTime = toTime.getText().toString();
                                endTime = fromTime.getText().toString();
                                startDate = toDate.getText().toString();
                                endDate = fromDate.getText().toString();
                                toCount = Integer.parseInt(counter);
                                myRef.child("User Id: " + ownerParser(bookings)).child(keyParser(bookings)).child("counter").getRef().setValue(toCount + 1);

                                // split booking: updating database
                                if(isValidDate() && isValidTime()){
                                    System.out.println("your dates are within range of the availability!");
                                    System.out.println("your times are within range of the availability!");
                                    myRef.child("User Id: " + ownerParser(bookings)).child(keyParser(bookings)).child("startDate").getRef().setValue(fromDate.getText().toString());
                                    myRef.child("User Id: " + ownerParser(bookings)).child(keyParser(bookings)).child("startTime").getRef().setValue(fromTime.getText().toString());

                                    // save booking to database
                                    myRef.child("User Id: " + uid).child("Bookings").child("Start Time").setValue(toTime.getText().toString());
                                    myRef.child("User Id: " + uid).child("Bookings").child("End Time").setValue(fromTime.getText().toString());
                                    myRef.child("User Id: " + uid).child("Bookings").child("Start Date").setValue(toDate.getText().toString());
                                    myRef.child("User Id: " + uid).child("Bookings").child("End Date").setValue(fromDate.getText().toString());
                                    myRef.child("User Id: " + uid).child("Bookings").child("Address").setValue(addressParser(bookings));

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Error: Please check if date and time are valid!", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        String booking = getIntent().getStringExtra("Booking");
        details.setText(booking);
    }


    private String ownerParser(String str){
        int start = str.indexOf("owner=");
        int end = str.indexOf("price=");

        return str.substring(start + 6, end - 2);
    }

    private String keyParser(String str){
        int start = str.indexOf("listingHash=");
        int end = str.indexOf("owner=");

        return str.substring(start + 12, end - 2);
    }

    private String counterParser(String str){
        int start = str.indexOf("counter=");
        int end = str.lastIndexOf("}");

        return str.substring(start + 8, end - 2);
    }

    private String endTimeParser(String str){
        int start = str.indexOf("endTime=");
        int end = str.indexOf("startTime=");

        return str.substring(start + 8, end - 2);

    }

    private String startTimeParser(String str){
        int start = str.indexOf("startTime=");
        int end = str.indexOf("state=");

        return str.substring(start + 10, end - 2);
    }

    private String endDateParser(String str){
        int start = str.indexOf("endDate=");
        int end = str.indexOf("zip=");

        return str.substring(start + 8, end - 2);
    }

    private String startDateParser(String str){
        int start = str.indexOf("Start Date=");
        int end = str.indexOf("End Date=");

        return str.substring(start + 11, end - 2);
    }

    private String addressParser(String str){
        int start = str.indexOf("address=");
        int end = str.indexOf("availability=");

        return str.substring(start + 8, end - 2);
    }

    private static long compareTo(Date d1, Date d2){
        return d1.getTime() - d2.getTime();
    }

    private boolean isValidDate(){
        boolean valid = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date d1 = sdf.parse(ogStartDate);
            Date d2 = sdf.parse(startDate);
            Date d3 = sdf.parse(ogEndDate);
            Date d4 = sdf.parse(endDate);

            // example: endDate 12/9/2017, ogEndDate 12/8/2017
            if(compareTo(d4,d3) > 0){
                valid = false;
            }

            // example: ogStartDate 12/5/2017, startDate 12/6/17
            if (compareTo(d1,d2) < 0){
                System.out.println("d1 is before d2");

                // example: startDate 12/6/17, ogEndDate 12/5/17
                if(compareTo(d2,d3) > 0){
                    valid = false;
                }
                valid = true;
            }

            // example: ogStartDate 12/5/17, startDate 12/4/17
            else if (compareTo(d1,d2) > 0){
                System.out.println("d1 is after d2");
                System.out.println("invalid date");
                valid = false;
            }
            else{
                System.out.println("They are equal");
                valid = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return valid;

    }

    private boolean isValidTime(){
        boolean valid = true;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        try {
            Date t1 = sdf.parse(ogStartTime);
            Date t2 = sdf.parse(ogEndTime);
            Date t3 = sdf.parse(startTime);
            Date t4 = sdf.parse(endTime);

            // If user start time exceeds end time return false
            if(t3.after(t2)){
                valid = false;
                System.out.println("Invalid Time");
            }
            // If user start time is before the actual start time return false;
            else if(t3.before(t1)){
                valid = false;
                System.out.println("Invalid Time");
            }
            // If user end time exceeds the actual end time return false
            else if(t4.after(t2)){
                valid = false;
                System.out.println("Invalid Time");
            }
            // If user end time is before the actual start time return false
            else if(t4.before(t1)){
                valid = false;
                System.out.println("Invalid Time");
            }
            else
                valid = true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return valid;

    }
}

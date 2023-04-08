package com.example.homsi.psf;

import android.content.Context;
import android.content.Intent;
import java.text.ParseException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.text.SimpleDateFormat;

public class changeSpotInformation extends AppCompatActivity {
    private FirebaseUser user;
    private Button edit;
    private TextView address;
    private EditText sDate;
    private EditText eDate;
    private EditText sTime;
    private EditText eTime;
    private EditText price;
    private DatabaseReference dref;
    private String id;
    private String UID;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_spot_information);
        id = getIntent().getStringExtra("PSpotKey");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        edit=findViewById(R.id.button5);
        address= findViewById(R.id.textView12);
        sDate=findViewById(R.id.editText3);
        eDate=findViewById(R.id.editText4);
        sTime=findViewById(R.id.editText5);
        eTime=findViewById(R.id.editText6);
        price=findViewById(R.id.editText7);

        dref = dataRef.child("User Id: " + UID).child(id);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                address.setText(String.format("%s %s", "Address: ", dataSnapshot.child("address").getValue()));
                sDate.setText(dataSnapshot.child("startDate").getValue().toString());
                eDate.setText(dataSnapshot.child("endDate").getValue().toString());
                sTime.setText(dataSnapshot.child("startTime").getValue().toString());
                eTime.setText(dataSnapshot.child("endTime").getValue().toString());
                price.setText( dataSnapshot.child("price").getValue().toString());
                dataSnapshot.child("availability").getRef().setValue(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmChanges();
            }
        });

    }
    private void confirmChanges() {
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        final Intent profileActivity = new Intent(this, chooseSpot.class);

        final String date1 = sDate.getText().toString();
        final String date2 = eDate.getText().toString();
        final String time1 = sTime.getText().toString();
        final String time2 = eTime.getText().toString();
        final Double pricetoEdit = Double.parseDouble(price.getText().toString());

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(date1)) {
            sDate.setError("This field is required");
            focusView = sDate;
            cancel = true;
        }
        if (TextUtils.isEmpty(date2) ) {
            eDate.setError("This field is required");
            focusView =eDate;
            cancel = true;
        }

        if(isValidDate(date1)==false)
        {
            sDate.setError("Error: format should be MM/DD/YYYY");
            focusView=sDate;
            cancel= true;
        }
        if(isValidDate(date2)==false)
        {
            eDate.setError("Error: format should be MM/DD/YYYY");
            focusView=eDate;
            cancel = true;
        }




        if(isValidTime(time1)==false)
        {
            sTime.setError("Error format should be hh:mmAM/PM");
            focusView=sTime;
            cancel = true;
        }
        if(isValidTime(time2)==false)
        {
            eTime.setError("Error format should be hh:mmAM/PM");
            focusView=eTime;
            cancel = true;
        }

        if (TextUtils.isEmpty(time1)) {
            sTime.setError(getString(R.string.error_field_required));
            focusView = sTime;
            cancel = true;
        }


        if (TextUtils.isEmpty(time2)) {
            eTime.setError(getString(R.string.error_field_required));
            focusView = eTime;
            cancel = true;
        }

        if (pricetoEdit==null ) {
            price.setError(getString(R.string.error_field_required));
            focusView = price;
            cancel = true;
        }






        if (cancel) {
            focusView.requestFocus();
        } else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = "";
            if (user != null) {
                uid = user.getUid();
            }
            DatabaseReference userRef = dataRef.child("User Id: " + uid).child(id);

            userRef.child("startDate").setValue(date1);
            userRef.child("endDate").setValue(date2);
            userRef.child("startTime").setValue(time1);
            userRef.child("endTime").setValue(time2);
            userRef.child("price").setValue(pricetoEdit);

            Context context = getApplicationContext();
            CharSequence failure = "You have updated your parking spot";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, failure, duration).show();

            startActivity(profileActivity);
            finish();
        }
    }


   private boolean isValidDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
        format.setLenient(false);
        try {
            format.parse(input);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }
    private boolean isValidTime(String input)
    {
        SimpleDateFormat format = new SimpleDateFormat("hh:mma");
        format.setLenient(false);
        try {
            format.parse(input);
            return true;
        }catch (ParseException e)
        {
            return false;
        }
    }


}

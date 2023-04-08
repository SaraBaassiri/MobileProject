package com.example.homsi.psf;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserInfoActivity extends AppCompatActivity {

    private AutoCompleteTextView AddressView;
    private AutoCompleteTextView CityView;
    private AutoCompleteTextView StateView;
    private AutoCompleteTextView ZipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
         AddressView = (AutoCompleteTextView) findViewById(R.id.address);
        CityView = (AutoCompleteTextView) findViewById(R.id.city);
        //StateView = (AutoCompleteTextView) findViewById(R.id.state);
        ZipView= (AutoCompleteTextView) findViewById(R.id.zip);
        Button finished = (Button) findViewById(R.id.finish);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] state = {"AK",  "AL", "AR", "AZ", "CA", "CO", "CT" ,"DE" ,"FL" ,"GA" ,"HI" ,"IA" ,"ID" ,"IL" ,"IN" ,"KS" ,"KY" ,"LA" ,"MA" ,"MD" ,"ME" ,"MI" ,"MN",
                "MO" ,"MS" ,"MT" ,"NC" ,"ND" ,"NE" ,"NH" ,"NJ" ,"NM", "NV" ,"NY" ,"OH" ,"OK" ,"OR" ,"PA" ,"RI" ,"SC" ,"SD" ,"TN" ,"TX" ,"UT" ,"VA" ,
                "VT" ,"WA" ,"WI" ,"WV" ,"WY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserInfoActivity.this,android.R.layout.simple_spinner_item, state);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address();
            }
        });
    }
    public void address() {
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        final Intent welcome= new Intent(this, WelcomeActivity.class);
        Bundle bundle= getIntent().getExtras();
        String first = "";
        String last = "";
        if(bundle!=null) {

            first = bundle.getString("firstname");
            last = bundle.getString("lastname");
        }

        final String address = AddressView.getText().toString();
        final String city = CityView.getText().toString();
        //final String state = StateView.getText().toString();
        final String zip= ZipView.getText().toString();

        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        final String state = mySpinner.getSelectedItem().toString();



        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(address) ) {
           AddressView.setError(getString(R.string.error_field_required));
            focusView = AddressView;
            cancel = true;
        }


        if (TextUtils.isEmpty(city)) {
            CityView.setError(getString(R.string.error_field_required));
            focusView = CityView;
            cancel = true;
        }
        /*
        if(TextUtils.isEmpty(state)){
          StateView.setError(getString(R.string.error_field_required));
            focusView = StateView;
            cancel = true;
        }
        */
        if(TextUtils.isEmpty(zip) || zipTest(zip) == false){
            ZipView.setError(getString(R.string.error_field_required));
            focusView = ZipView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = "";
        if(user!=null) {
            uid = user.getUid();
        }
            DatabaseReference userRef = dataRef.child("User Id: " + uid).child("User Information");

            userRef.child("First name" ).setValue(first);
            userRef.child("Last name" ).setValue(last);
            userRef.child("Address").setValue( address);
            userRef.child("City").setValue(city);
            userRef.child("State").setValue(state);
            userRef.child("Zip").setValue(zip);


        Context context = getApplicationContext();
            CharSequence failure = "You have completed the setup. Please log in";
            int duration = Toast.LENGTH_SHORT;
             Toast.makeText(context, failure, duration).show();

            startActivity(welcome);
            finish();

       }
    }
    private boolean zipTest(String zip)
    {
        boolean ziptest = false;
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }

}

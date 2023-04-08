package com.example.homsi.psf;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    private EditText fNameEdit;
    private EditText lNameEdit;
    private EditText addressEdit;
    private EditText cityEdit;
    private EditText stateEdit;
    private EditText zipEdit;
    private Button confirmEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fNameEdit=findViewById(R.id.editFName);
        lNameEdit=findViewById(R.id.editLName);
        addressEdit= findViewById(R.id.editAddress);
        cityEdit=findViewById(R.id.editCity);
        //stateEdit= findViewById(R.id.editState);
        zipEdit=findViewById(R.id.editZip);
        confirmEdit=findViewById(R.id.EditProfConfirm);
        Button finished = (Button) findViewById(R.id.finish);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] state = {"AK",  "AL", "AR", "AZ", "CA", "CO", "CT" ,"DE" ,"FL" ,"GA" ,"HI" ,"IA" ,"ID" ,"IL" ,"IN" ,"KS" ,"KY" ,"LA" ,"MA" ,"MD" ,"ME" ,"MI" ,"MN",
                "MO" ,"MS" ,"MT" ,"NC" ,"ND" ,"NE" ,"NH" ,"NJ" ,"NM", "NV" ,"NY" ,"OH" ,"OK" ,"OR" ,"PA" ,"RI" ,"SC" ,"SD" ,"TN" ,"TX" ,"UT" ,"VA" ,
                "VT" ,"WA" ,"WI" ,"WV" ,"WY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_spinner_item, state);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);




        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfileInformation();
            }
        });
    }
    private void editProfileInformation(){
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference();
        final Intent profileActivity= new Intent(this, ProfileActivity.class);

        final String firstName= fNameEdit.getText().toString();
        final String lastName=lNameEdit.getText().toString();
        final String address = addressEdit.getText().toString();
        final String city = cityEdit.getText().toString();
        //final String state = stateEdit.getText().toString();
        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        final String state = mySpinner.getSelectedItem().toString();
        final String zip= zipEdit.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if(TextUtils.isEmpty(firstName) || isNameValid(firstName) == false)
        {
            fNameEdit.setError("This field is required");
            focusView=fNameEdit;
            cancel =true;
        }
        if(TextUtils.isEmpty(lastName)|| isNameValid(lastName) == false)
        {
            lNameEdit.setError("This field is required");
            focusView=lNameEdit;
            cancel = true;
        }

        if (TextUtils.isEmpty(address) ) {
            addressEdit.setError(getString(R.string.error_field_required));
            focusView = addressEdit;
            cancel = true;
        }


        if (TextUtils.isEmpty(city)) {
            cityEdit.setError(getString(R.string.error_field_required));
            focusView = cityEdit;
            cancel = true;
        }
        if(TextUtils.isEmpty(state)){
            stateEdit.setError(getString(R.string.error_field_required));
            focusView = stateEdit;
            cancel = true;
        }

        if(TextUtils.isEmpty(zip) || zipTest(zip) == false){
            zipEdit.setError(getString(R.string.error_field_required));
            focusView = zipEdit;
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

            userRef.child("First name" ).setValue(firstName);
            userRef.child("Last name" ).setValue(lastName);
            userRef.child("Address").setValue( address);
            userRef.child("City").setValue(city);
            userRef.child("State").setValue(state);
            userRef.child("Zip").setValue(zip);

            Context context = getApplicationContext();
            CharSequence failure = "You have changed your profile information.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, failure, duration).show();

            startActivity(profileActivity);
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
    public boolean isNameValid(String name){
        String valid = "[A-Za-z]*";
        return name.contains(valid);
    }
}

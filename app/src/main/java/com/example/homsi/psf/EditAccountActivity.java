package com.example.homsi.psf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class EditAccountActivity extends AppCompatActivity {
    private Button choosePicture;
    private Button confirmAccountChanges;
    private EditText changeEmail;
    private EditText changePassword;
    private String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseStorage storage= FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        choosePicture= findViewById(R.id.ChoosePicture);
        confirmAccountChanges=findViewById(R.id.ConfirmAccInfo);
        changeEmail= findViewById(R.id.editAccEmail);
        changePassword=findViewById(R.id.editAccountPass);

        confirmAccountChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });



        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditAccountActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);

            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted and now can proceed
                    chooseGooglePicture(); //a sample method called

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(EditAccountActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // add other cases for more permissions
        }
    }

    private void chooseGooglePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    public static final int PICK_IMAGE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent profile= new Intent(this,UserActivity.class);
        if (requestCode == PICK_IMAGE&&resultCode== Activity.RESULT_OK) {
            Uri selected=data.getData();
            String path = getPath(EditAccountActivity.this,selected);
            StorageReference childReference = storageRef.child(uid);
            Uri file = Uri.fromFile(new File(path));
            UploadTask uploadTask= childReference.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
            //DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
            //dref=dref.child("User Id: " + uid).child("User Information");
            //dref.child("photoURI").setValue(selected.toString());
        }
        startActivity(profile);

    }

    private void updateAccount()
    {
        Intent profileActivity = new Intent(this,ProfileActivity.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email=changeEmail.getText().toString();
        final String password =changePassword.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !checkValidPassword(password)) {
            changePassword.setError(getString(R.string.error_invalid_password));
            focusView = changePassword;
            cancel = true;
        }



        if (!checkValidEmail(email)) {
            changeEmail.setError(getString(R.string.error_invalid_email));
            focusView = changeEmail;
            cancel = true;
        }

        if(cancel)
        {
            focusView.requestFocus();
        }
        else{
            user.updateEmail(email);
            user.updatePassword(password);
            Context context = getApplicationContext();
            CharSequence failure = "You have changed your account information.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, failure, duration).show();
            startActivity(profileActivity);
            finish();
        }


    }
    private boolean checkValidPassword(String pass)
    {
        return pass.length()>4;
    }
    private boolean checkValidEmail(String email)
    {
        return email.contains("@")&&email.contains(".");
    }


    public String getPath(Context context, Uri uri ) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
}




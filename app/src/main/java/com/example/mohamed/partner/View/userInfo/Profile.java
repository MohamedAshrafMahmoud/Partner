package com.example.mohamed.partner.View.userInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Profile extends AppCompatActivity {

    private final int pickImageRequest = 71;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    UserInfo info = new UserInfo();
    Uri saveuri;
    ImageView userPhoto, takephoto, done;
    EditText firstname, lastname, jobtitle, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/");

        userPhoto = (ImageView) findViewById(R.id.userimage);
        takephoto = (ImageView) findViewById(R.id.takeimge);
        done = (ImageView) findViewById(R.id.done);
        firstname = (EditText) findViewById(R.id.first);
        lastname = (EditText) findViewById(R.id.last);
        jobtitle = (EditText) findViewById(R.id.title);
        password = (EditText) findViewById(R.id.pass);


        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstname.getText().toString().length() == 0) {
                    firstname.setError("first name required");
                    firstname.requestFocus();
                } else if (lastname.getText().toString().length() == 0) {
                    lastname.setError("last name required");
                    lastname.requestFocus();
                } else if (jobtitle.getText().toString().length() == 0) {
                    jobtitle.setError("job title required");
                    jobtitle.requestFocus();
                } else if (password.getText().toString().length() == 0) {
                    password.setError("password required");
                    password.requestFocus();
                } else {
                    //upload user data
                    upload();
                }
            }
        });


    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //send data
    private void upload() {
        if (saveuri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading ...");
            dialog.show();

            String imgName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/" + imgName);
            imageFolder.putFile(saveuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(Profile.this, "Uploaded ", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    info = new UserInfo(
                                            Common.currentUser.getName(),
                                            firstname.getText().toString(),
                                            lastname.getText().toString(),
                                            jobtitle.getText().toString(),
                                            password.getText().toString(),
                                            uri.toString(),
                                            "",
                                            "",
                                            "");

                                    databaseReference.child(Common.currentUser.getName()).setValue(info);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            dialog.dismiss();
                            Toast.makeText(Profile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Upload      " + progress + "    %  ");
                        }
                    });
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private void ChooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), pickImageRequest);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
//for set user new photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pickImageRequest && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveuri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), saveuri);
                userPhoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

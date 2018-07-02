package com.example.mohamed.partner.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Projects;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class AddNewProject extends AppCompatActivity {

    
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    
    Projects projects = new Projects();
    ArrayList<String> arr;
    
    Uri saveuri;
    private final int pickImageRequest = 71;

    ImageView choosePhoto, userPhoto, done;
    TextView name;
    EditText projectName, projectDescription;
    Spinner projectCategory;
    Button addSkill;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_project);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Projects");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/");

        choosePhoto = (ImageView) findViewById(R.id.takeimge);
        userPhoto = (ImageView) findViewById(R.id.userimage);
        done = (ImageView) findViewById(R.id.done);
        name = (TextView) findViewById(R.id.username);
        projectName = (EditText) findViewById(R.id.projecttitle);
        projectCategory = (Spinner) findViewById(R.id.projectcategory);
        projectDescription = (EditText) findViewById(R.id.description);
        addSkill = (Button) findViewById(R.id.addskill);

        name.setText(Common.currentUser.getName());


        arr = new ArrayList<String>();
        arr.add("Application Development");
        arr.add("Game Development");
        arr.add("mobile development");
        arr.add("web development");
        arr.add("design");
        arr.add("mechanics");
        arr.add("music");
        arr.add("graphics");
        arr.add("economy");
        arr.add("film and animation");


        ArrayAdapter<String> arrad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        arrad.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        projectCategory.setAdapter(arrad);
        projectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                String item = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload user data
                upload();
            }
        });

        addSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkillAlert();
            }
        });

    }


    //alert dialog for add skill
    private void addSkillAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewProject.this);
        alertDialog.setTitle("Add Skills needed");
        alertDialog.setMessage("skills");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_addskill_addproject, null);

        final EditText skills = view.findViewById(R.id.edtskill);
        final EditText number = view.findViewById(R.id.edtnum);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                projects.setSkill(skills.getText().toString());
                projects.setSkill_number(number.getText().toString());

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
     
    
    //send data
    private void upload() {

        if (saveuri != null) {

            if (TextUtils.isEmpty(projects.getSkill())) {
                addSkillAlert();
                Toast.makeText(this, "Skills is needed", Toast.LENGTH_SHORT).show();

            } else {

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
                                Toast.makeText(AddNewProject.this, "Uploaded ", Toast.LENGTH_SHORT).show();
                                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                ArrayList<String> users =new ArrayList<String>();
                                users.add(Common.currentUser.getName());
                                        if (TextUtils.isEmpty(uri.toString())){

                                            projects = new Projects(
                                                    Common.currentUser.getName(),
                                                    String.valueOf(R.drawable.logoedit),
                                                    name.getText().toString(),
                                                    projectName.getText().toString(),
                                                    projectCategory.getSelectedItem().toString(),
                                                    projectDescription.getText().toString(),
                                                    projects.getSkill(),
                                                    projects.getSkill_number(),
                                                    users,
                                                    users);
                                        }else {
                                            projects = new Projects(
                                                    Common.currentUser.getName(),
                                                    uri.toString(),
                                                    name.getText().toString(),
                                                    projectName.getText().toString(),
                                                    projectCategory.getSelectedItem().toString(),
                                                    projectDescription.getText().toString(),
                                                    projects.getSkill(),
                                                    projects.getSkill_number(),
                                                    users,
                                                    users);
                                        }
                                        databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(projects);
                                        startActivity(new Intent(AddNewProject.this, MainTab.class));
                                        Toast.makeText(AddNewProject.this, "New project " + projects.getTitle() + " was added", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(AddNewProject.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
    

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void ChooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), pickImageRequest);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
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

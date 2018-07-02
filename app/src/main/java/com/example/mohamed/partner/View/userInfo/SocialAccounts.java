package com.example.mohamed.partner.View.userInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.View.AddNewProject;
import com.example.mohamed.partner.View.MainTab;
import com.example.mohamed.partner.View.SignUp;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.User;
import com.example.mohamed.partner.model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SocialAccounts extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    RelativeLayout facebook,linkedin,github;
    ImageView done;

    UserInfo userInfo=new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_accounts);


        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("UserInfo");

        facebook=(RelativeLayout)findViewById(R.id.facebook);
        linkedin=(RelativeLayout)findViewById(R.id.linkenin);
        github=(RelativeLayout)findViewById(R.id.github);
        done=(ImageView)findViewById(R.id.done);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertFacebook();
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertlinkedin();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertgithub();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        databaseReference.child(Common.currentUser.getName()).setValue(userInfo);
                        Toast.makeText(SocialAccounts.this, "loaded !!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }




////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showAlertlinkedin() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SocialAccounts.this);
        alertDialog.setTitle("Add your linked account");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_linkedin, null);

        final EditText skills = view.findViewById(R.id.edtskilll);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                userInfo.setLinkedin(skills.getText().toString());

            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();


    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void showAlertFacebook() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SocialAccounts.this);
        alertDialog.setTitle("Add your facebook account");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_facebook, null);

        final EditText skills = view.findViewById(R.id.edtskillf);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                userInfo.setFacebook(skills.getText().toString());

            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showAlertgithub() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SocialAccounts.this);
        alertDialog.setTitle("Add your github account");

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_github, null);

        final EditText skills = view.findViewById(R.id.edtskillg);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                userInfo.setGithub(skills.getText().toString());

            }
        });
        alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();

    }
}

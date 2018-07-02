package com.example.mohamed.partner.View.memberInfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Member_SocialAccounts extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    RelativeLayout facebook, linkedin, github;

    UserInfo userInfo = new UserInfo();

    String userName = "";
    String newUserName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_social_accounts);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");

        facebook = (RelativeLayout) findViewById(R.id.facebook);
        linkedin = (RelativeLayout) findViewById(R.id.linkenin);
        github = (RelativeLayout) findViewById(R.id.github);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFacebook();
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLinkedIn();
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGithub();
            }
        });

        if (getIntent() != null) {
            userName = getIntent().getStringExtra("userName");
        }
        if (!userName.isEmpty()) {
            newUserName=userName;
        }


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadLinkedIn() {


        databaseReference.child(newUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);

                Intent face = new Intent(Intent.ACTION_VIEW);
                face.setData(Uri.parse(userInfo.getLinkedin()));
                startActivity(face);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadGithub() {
        databaseReference.child(newUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);

                Intent face = new Intent(Intent.ACTION_VIEW);
                face.setData(Uri.parse(userInfo.getGithub()));
                startActivity(face);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadFacebook() {

        databaseReference.child(newUserName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(UserInfo.class);

                Intent face = new Intent(Intent.ACTION_VIEW);
                face.setData(Uri.parse(userInfo.getFacebook()));
                startActivity(face);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}

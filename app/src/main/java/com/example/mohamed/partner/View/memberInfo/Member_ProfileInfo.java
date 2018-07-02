package com.example.mohamed.partner.View.memberInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.View.ProjectDescription;
import com.example.mohamed.partner.View.userInfo.MyProjects;
import com.example.mohamed.partner.model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Member_ProfileInfo extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    UserInfo userInfo = new UserInfo();

    String userName = "";


    ImageView userImage;
    Button follow;
    TextView firstname, lastName, title;
    LinearLayout socialAccounts;
    LinearLayout myProjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_profile);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");

        userImage = (ImageView) findViewById(R.id.userimage);
        follow = (Button) findViewById(R.id.follow);
        socialAccounts = (LinearLayout)findViewById(R.id.linersocialaccounts);
        myProjects = (LinearLayout)findViewById(R.id.myprojects);
        firstname = (TextView)findViewById(R.id.name2);
        lastName = (TextView) findViewById(R.id.name3);
        title = (TextView)findViewById(R.id.title2);



        if (getIntent() != null) {
            userName = getIntent().getStringExtra("userName");
        }
        if (!userName.isEmpty()) {
            databaseReference.child(userName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userInfo = dataSnapshot.getValue(UserInfo.class);


                    if (TextUtils.isEmpty(userInfo.getImage()) && TextUtils.isEmpty(userInfo.getFirstName()) && TextUtils.isEmpty(userInfo.getLastName()) && TextUtils.isEmpty(userInfo.getJobtitle())) {
                        userImage.setImageResource(R.drawable.logoedit);
                        firstname.setText(userName);
                        lastName.setText("");
                        title.setText("wait jobtitle !!");
                    } else {
                        Picasso.with(getBaseContext()).load(userInfo.getImage()).into(userImage);

                        firstname.setText(userInfo.getFirstName());
                        lastName.setText(userInfo.getLastName());
                        title.setText(userInfo.getJobtitle());
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        socialAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Member_ProfileInfo.this, Member_SocialAccounts.class));

            }
        });


        socialAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Member_ProfileInfo.this, MyProjects.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
        });



    }
    }

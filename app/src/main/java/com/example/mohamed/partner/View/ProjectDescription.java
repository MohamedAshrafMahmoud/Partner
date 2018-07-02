package com.example.mohamed.partner.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.View.memberInfo.Member_ProfileInfo;
import com.example.mohamed.partner.View.userInfo.Profile;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Comment;
import com.example.mohamed.partner.model.Projects;
import com.example.mohamed.partner.model.Rating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ProjectDescription extends AppCompatActivity implements RatingDialogListener {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceRating;
    DatabaseReference databaseReferenceComments;


    Projects projects;

    String projectId = "";

    ImageView project, user;
    TextView userName,projectName, descripton ,skill,rate,getRates,followersCount,membersCount;
    RatingBar ratingBar;
    ImageView send;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects_description);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Projects");
        databaseReferenceRating = database.getReference("Rating");
        databaseReferenceComments =database.getReference("Comments");

        project = (ImageView) findViewById(R.id.descriptionprojectimage);
        user = (ImageView) findViewById(R.id.descriptionuserimage);
        userName = (TextView) findViewById(R.id.descriptionusername);
        projectName = (TextView) findViewById(R.id.projectname);
        descripton = (TextView) findViewById(R.id.descriptiondescription);
        skill = (TextView) findViewById(R.id.skillview);
        rate = (TextView) findViewById(R.id.ratee);
        getRates = (TextView) findViewById(R.id.rates);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        followersCount = (TextView) findViewById(R.id.followersCount);
        membersCount = (TextView) findViewById(R.id.membersCount);
        send=(ImageView)findViewById(R.id.sendcomment);
        comment=(EditText)findViewById(R.id.edtwritecomment);

        if (getIntent() != null) {
            projectId = getIntent().getStringExtra("projectId");
        }
        if (!projectId.isEmpty()) {
            getDetailas(projectId);
            getRatingInBar(projectId);
            getFollowersAndMembers(projectId);
        }


        //check user or member user
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.currentUser.getName()==projects.getName()){
                    Intent intent = new Intent(ProjectDescription.this, Profile.class);
                    //intent.putExtra("userId", projects.getName());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ProjectDescription.this, Member_ProfileInfo.class);
                    intent.putExtra("userName", projects.getName());
                    startActivity(intent);
                }
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        getRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProjectDescription.this,ShowRates.class);
                intent.putExtra("ID",projectId);
                startActivity(intent);
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Comment comm = new Comment(Common.currentUser.getName(), projectId,comment.getText().toString());
                databaseReferenceComments.push().setValue(comm);

                Intent intent = new Intent(ProjectDescription.this, ShowComment.class);
                intent.putExtra("projectId2",projectId);
                // intent.putExtra("comment",comment.getText().toString());
                startActivity(intent);

            }
        });


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getDetailas(String projectId) {

        databaseReference.child(projectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projects = dataSnapshot.getValue(Projects.class);

                Picasso.with(getBaseContext()).load(projects.getImage()).into(project);

                userName.setText(projects.getName());
                projectName.setText(projects.getTitle());
                descripton.setText(projects.getDescription());
                skill.setText(projects.getSkill());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getRatingInBar(String projectId) {
        com.google.firebase.database.Query foodRating = databaseReferenceRating.orderByChild("projectId").equalTo(projectId);

        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0, sum = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum += Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count != 0) {
                    float average = sum / count;
                    ratingBar.setRating(average);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showRatingDialog() {

        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not Good", "Quite Ok", "Very Good", "Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate our project")
                .setDescription("Please select some stars and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.colorPrimaryDark)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorAccent)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(ProjectDescription.this)
                .show();
    }

    public void onPositiveButtonClicked(final int rateValue, String comment) {

        final Rating rating = new Rating(Common.currentUser.getName(), projectId, String.valueOf(rateValue), comment);
        databaseReferenceRating.push()
                .setValue(rating)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete( Task<Void> task) {
                        Toast.makeText(ProjectDescription.this, "Thank you for submit rating ", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void onNegativeButtonClicked() {
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getFollowersAndMembers(final String projectId) {

        databaseReference.child(projectId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projects = (Projects) dataSnapshot.getValue(Projects.class);
                followersCount.setText(String.valueOf(projects.getFollowers().size()));
                membersCount.setText(String.valueOf(projects.getMembers().size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void updateFollowers(final String projectId) {

        databaseReference.child(projectId).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> followers = (ArrayList<String>) dataSnapshot.getValue();
                if(!followers.contains(Common.currentUser.getName())){
                    followers.add(Common.currentUser.getName());
                    databaseReference.child(projectId).child("followers").setValue(followers);
                    getFollowersAndMembers(projectId);
            }
        }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateMembers(final String projectId) {

        databaseReference.child(projectId).child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> members = (ArrayList<String>) dataSnapshot.getValue();
                if(!members.contains(Common.currentUser.getName())){
                    members.add(Common.currentUser.getName());
                    databaseReference.child(projectId).child("members").setValue(members);
                    getFollowersAndMembers(projectId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////

  //on click from image

    public void addFollower(View view){
        updateFollowers(projectId);
    }

    public void addMember(View view){
        updateMembers(projectId);
    }
}


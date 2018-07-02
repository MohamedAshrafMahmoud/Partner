package com.example.mohamed.partner.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Comment;
import com.example.mohamed.partner.model.Rating;
import com.example.mohamed.partner.viewholders.ShowCommentsViewHolder;
import com.example.mohamed.partner.viewholders.ShowRatesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowComment extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<Comment, ShowCommentsViewHolder> adapter;

    RecyclerView recycler;

    String projectId = "";

    EditText comment;
    ImageView send;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_comment);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Comments");

//        if (getIntent() != null) {
//            projectId = getIntent().getStringExtra("projectId2");
//        }
//        if (!projectId.isEmpty()) {
            loadComments(projectId);
//        }

        send=(ImageView)findViewById(R.id.sendcomment);
        comment=(EditText)findViewById(R.id.edtwritecomment);

        recycler = (RecyclerView) findViewById(R.id.listcomment);
        recycler.setLayoutManager(new LinearLayoutManager(this));




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Comment comm = new Comment(Common.currentUser.getName(), projectId,comment.getText().toString());
                databaseReference.push().setValue(comm);



            }
        });
    }




    private void loadComments(String id) {

        if (getIntent() != null)
            id = getIntent().getStringExtra("projectId2");
        if (!id.isEmpty() && id != null) {


            adapter = new FirebaseRecyclerAdapter<Comment, ShowCommentsViewHolder>(Comment.class, R.layout.item_show_comment, ShowCommentsViewHolder.class, databaseReference.orderByChild("projectId").equalTo(id)) {
                @Override
                protected void populateViewHolder(ShowCommentsViewHolder viewHolder, Comment comment, int position) {

                    viewHolder.name.setText(comment.getUserName());
                    viewHolder.comment.setText(comment.getComment());

                }
            };
            recycler.setAdapter(adapter);
        }
    }
}
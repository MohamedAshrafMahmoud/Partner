package com.example.mohamed.partner.View.userInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.example.mohamed.partner.Interface.ItemClickListner;
import com.example.mohamed.partner.R;
import com.example.mohamed.partner.View.ProjectDescription;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Projects;
import com.example.mohamed.partner.model.UserInfo;
import com.example.mohamed.partner.viewholders.projectsHomeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProjects extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recycler_projects;
    FirebaseRecyclerAdapter<Projects, projectsHomeViewHolder> adapter;

    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_my_projects);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Projects");

        recycler_projects = (RecyclerView)findViewById(R.id.recyclerhome);
        recycler_projects.setHasFixedSize(true);
        recycler_projects.setLayoutManager(new LinearLayoutManager( this));


        if (getIntent() != null) {
            userName = getIntent().getStringExtra("UserName");
        }
        if (!userName.isEmpty()) {
            loadProjects();

        }


    }

    private void loadProjects() {
        adapter = new FirebaseRecyclerAdapter<Projects, projectsHomeViewHolder>(Projects.class, R.layout.item_progect, projectsHomeViewHolder.class, databaseReference.orderByChild("name")) {
            @Override
            protected void populateViewHolder(projectsHomeViewHolder viewHolder, Projects projects, int position) {

                viewHolder.name.setText(projects.getName());
                viewHolder.projectName.setText(projects.getTitle());
                viewHolder.category.setText(projects.getCategory());
                viewHolder.date.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));

                Picasso.with(getBaseContext()).load(projects.getImage()).into(viewHolder.projectImage);

                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(MyProjects.this, ProjectDescription.class);
                        intent.putExtra("projectId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();  //refresh data if there is a change
        recycler_projects.setAdapter(adapter);
    }
}

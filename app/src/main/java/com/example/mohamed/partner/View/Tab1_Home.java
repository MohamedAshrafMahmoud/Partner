package com.example.mohamed.partner.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.partner.Interface.ItemClickListner;
import com.example.mohamed.partner.R;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Projects;
import com.example.mohamed.partner.viewholders.projectsHomeViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Tab1_Home extends Fragment {


    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recycler_projects;
    FirebaseRecyclerAdapter<Projects, projectsHomeViewHolder> adapter;
    FloatingActionButton floatingActionButton;

    private OnFragmentInteractionListener mListener;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_home, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Projects");

        recycler_projects = (RecyclerView) view.findViewById(R.id.recyclerhome);
        recycler_projects.setHasFixedSize(true);
        recycler_projects.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL    ));
        loadProjects();

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddNewProject.class));
            }
        });




        return view;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadProjects() {

        adapter = new FirebaseRecyclerAdapter<Projects, projectsHomeViewHolder>(Projects.class, R.layout.item_progect, projectsHomeViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(projectsHomeViewHolder viewHolder, Projects projects, int position) {

                viewHolder.name.setText(projects.getName());
                viewHolder.projectName.setText(projects.getTitle());
                viewHolder.category.setText(projects.getCategory());
                viewHolder.followersCount.setText(String.valueOf(projects.getFollowers().size()));
                viewHolder.membersCount.setText(String.valueOf(projects.getMembers().size()));
                viewHolder.date.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));

                Picasso.with(getActivity()).load(projects.getImage()).into(viewHolder.projectImage);

                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getActivity(), ProjectDescription.class);
                        intent.putExtra("projectId", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        adapter.notifyDataSetChanged();  //refresh data if there is a change
        recycler_projects.setAdapter(adapter);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
package com.example.mohamed.partner.View.userInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.View.AddNewProject;
import com.example.mohamed.partner.View.SignIn;
import com.example.mohamed.partner.common.Common;
import com.example.mohamed.partner.model.Projects;
import com.example.mohamed.partner.model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Tab2_ProfileInfo extends Fragment {

    private OnFragmentInteractionListener mListener;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    UserInfo userInfo = new UserInfo();

    ImageView goProfile, userImage;
    Button logout;
    TextView firstname, lastName, title;
    LinearLayout socialAccounts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_profile_info, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("UserInfo");

        goProfile = (ImageView) view.findViewById(R.id.goprofile);
        userImage = (ImageView) view.findViewById(R.id.userimage);
        logout = (Button) view.findViewById(R.id.logout);
        socialAccounts = (LinearLayout) view.findViewById(R.id.linersocialaccounts);
        firstname = (TextView) view.findViewById(R.id.name2);
        lastName = (TextView) view.findViewById(R.id.name3);
        title = (TextView) view.findViewById(R.id.title2);


        goProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profile.class));
            }
        });

        socialAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SocialAccounts.class));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Are you sure");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), SignIn.class));

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
        });


//        databaseReference.child(Common.currentUser.getName()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                userInfo = dataSnapshot.getValue(UserInfo.class);
//
//
//                if (TextUtils.isEmpty(userInfo.getImage()) && TextUtils.isEmpty(userInfo.getFirstName()) && TextUtils.isEmpty(userInfo.getLastName()) && TextUtils.isEmpty(userInfo.getJobtitle())) {
//                    userImage.setImageResource(R.drawable.logoedit);
//                    firstname.setText("user");
//                    lastName.setText("name");
//                    title.setText("still wait");
//                } else {
////                    Picasso.with(getActivity()).load(userInfo.getImage()).into(userImage);
////
////                    firstname.setText(userInfo.getFirstName());
////                    lastName.setText(userInfo.getLastName());
////                    title.setText(userInfo.getJobtitle());
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        return view;
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
}
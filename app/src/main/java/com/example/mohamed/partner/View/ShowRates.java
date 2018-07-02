package com.example.mohamed.partner.View;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mohamed.partner.R;
import com.example.mohamed.partner.model.Rating;
import com.example.mohamed.partner.viewholders.ShowRatesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShowRates extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<Rating, ShowRatesViewHolder> adapter;

    RecyclerView recycler;

    String projectId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_rates);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Rating");

        recycler = (RecyclerView) findViewById(R.id.recyclerComment);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        loadComments(projectId);

    }

    private void loadComments(String foodId) {

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("ID");
        if (!foodId.isEmpty() && foodId != null) {

            //create request query
           // Query query = databaseReference.orderByChild("projectId").equalTo(foodId);

            adapter = new FirebaseRecyclerAdapter<Rating, ShowRatesViewHolder>(Rating.class, R.layout.item_showrates, ShowRatesViewHolder.class, databaseReference.orderByChild("projectId").equalTo(foodId)) {
                @Override
                protected void populateViewHolder(ShowRatesViewHolder viewHolder, Rating rating, int position) {

                    viewHolder.name.setText(rating.getUserName());
                    viewHolder.comment.setText(rating.getComment());
                    viewHolder.rate.setRating(Float.parseFloat(rating.getRateValue()));

                }
            };
            recycler.setAdapter(adapter);
        }
    }
}

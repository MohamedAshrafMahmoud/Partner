package com.example.mohamed.partner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mohamed.partner.R;

public class ShowCommentsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, comment;



    public ShowCommentsViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        comment = (TextView) itemView.findViewById(R.id.commentt);
    }


}
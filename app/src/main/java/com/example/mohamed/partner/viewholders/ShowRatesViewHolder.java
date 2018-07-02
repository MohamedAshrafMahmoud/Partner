package com.example.mohamed.partner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mohamed.partner.R;

/**
 * Created by mohamed on 4/7/18.
 */

public class ShowRatesViewHolder extends RecyclerView.ViewHolder {

    public TextView name, comment;
    public RatingBar rate;


    public ShowRatesViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.txtusername);
        comment = (TextView) itemView.findViewById(R.id.txtusercomment);
        rate = (RatingBar) itemView.findViewById(R.id.Rating);



    }


}
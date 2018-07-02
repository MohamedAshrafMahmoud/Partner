package com.example.mohamed.partner.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.partner.Interface.ItemClickListner;
import com.example.mohamed.partner.R;


/**
 * Created by mohamed on 3/12/18.
 */
public class projectsHomeViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener{

    public TextView name,projectName,category,date,followersCount,membersCount;
    public ImageView projectImage;

    private ItemClickListner itemClickListner;


    public projectsHomeViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.mainname);
        projectName = (TextView) itemView.findViewById(R.id.mainprojectname);
        category = (TextView) itemView.findViewById(R.id.maincategory);
        date = (TextView) itemView.findViewById(R.id.date);
        followersCount = (TextView) itemView.findViewById(R.id.followersCount2);
        membersCount = (TextView) itemView.findViewById(R.id.membersCount2);
        projectImage = (ImageView) itemView.findViewById(R.id.mainprojectimage);


        itemView.setOnClickListener(this);  //for action in items

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);
    }


}


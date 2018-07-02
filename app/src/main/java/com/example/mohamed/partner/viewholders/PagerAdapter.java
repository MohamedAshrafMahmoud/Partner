package com.example.mohamed.partner.viewholders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mohamed.partner.View.Tab1_Home;
import com.example.mohamed.partner.View.userInfo.Tab2_ProfileInfo;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int aNOFTabs;

    public PagerAdapter(FragmentManager fm, int aNumberOfTabs) {
        super(fm);
        this.aNOFTabs=aNumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                Tab1_Home tab1=new Tab1_Home();
                return tab1;
            case 1:
                Tab2_ProfileInfo tab2=new Tab2_ProfileInfo();
                return tab2;

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return aNOFTabs;
    }
}

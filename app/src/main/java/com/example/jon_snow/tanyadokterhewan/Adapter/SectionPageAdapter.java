package com.example.jon_snow.tanyadokterhewan.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jon_snow.tanyadokterhewan.Fragment.ChatFragment;
import com.example.jon_snow.tanyadokterhewan.Fragment.FriendsFragment;
import com.example.jon_snow.tanyadokterhewan.Fragment.RequestsFragment;

/**
 * Created by muksalbakrie on 01/05/18.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {
    //integer to count number of tabs


    public SectionPageAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {


        switch(position) {
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;

            case 1:
                ChatFragment chatsFragment = new ChatFragment();
                return  chatsFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return  null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "REQUEST";
            case 1:
                return "CHATS";
            case 2:
                return "Friends";
                default:
                    return null;
        }

    }
}

package com.example.ioanna.stationeryinventory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.ioanna.stationeryinventory.StationeryTabs.NUM_TABS;
import static com.example.ioanna.stationeryinventory.StationeryTabs.tabNames;

/**
 * Created by Ioanna on 29/07/2017.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {
    //Tag for the log messages
    public static final String LOG_TAG = StationeryProvider.class.getSimpleName();

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of items
    @Override
    public int getCount() {
        return NUM_TABS;
    }

    // Returns the fragment to display for that tab
    @Override
    public Fragment getItem(int position) {
        Fragment newFrag = null;
        switch (position) {
            case 0:
                newFrag = ListFragment.newInstance();
                break;
            case 1:
                newFrag = AvailableStationeryForSaleListFragment.newInstance();
                break;
        }
        return newFrag;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

}

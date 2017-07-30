package com.example.ioanna.stationeryinventory;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import static com.example.ioanna.stationeryinventory.StationeryTabs.NUM_TABS;
import static com.example.ioanna.stationeryinventory.StationeryTabs.tabNames;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener,
        AvailableStationeryForSaleListFragment.OnFragmentInteractionListener {
    /**
     * Tag for the log messages
     */
    public final String LOG_TAG = StationeryProvider.class.getSimpleName();
    public Context mainContext;
    private ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeActionBar();
        InitializeViewPager();
        InitializeTabMenu();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //Initialize Action Bar
    private void InitializeActionBar() {
        Log.i(LOG_TAG, "InitializeActionBar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
            }
        }
    }

    //Initialize View Pager
    private void InitializeViewPager() {
        Log.i(LOG_TAG, "InitializeViewPager");
        //Associate the ViewPager with the CustomPagerAdapter
        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        if (viewPager != null) {
            CustomPagerAdapter viewPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
        }
    }

    //Initialize Tab Menu
    private void InitializeTabMenu() {
        Log.i(LOG_TAG, "InitializeTabMenu");
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            for (int i = 1; i < NUM_TABS; ++i) {
                tabLayout.addTab(tabLayout.newTab().setText(tabNames[i - 1]));
            }
        }
    }

    @Override
    public void onFragmentInteraction(String TAG, Object data) {

    }
}

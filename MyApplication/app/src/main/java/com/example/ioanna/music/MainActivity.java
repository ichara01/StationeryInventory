package com.example.ioanna.music;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import static com.example.ioanna.music.MusicModel.NUM_TABS;
import static com.example.ioanna.music.MusicModel.tabNames;

public class MainActivity extends AppCompatActivity {

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
        //setTabContext(tabLayout);
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

    //Set action bar icons - Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.action_bar_search).getActionView();
            if (searchView != null)
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    //On selecting action bar icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_settings:
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                return true;
            case R.id.action_bar_help:
                Intent helpActivity = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(helpActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Initialize Action Bar
    private  void InitializeActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
            }
        }
    }

    //Initialize Tab Menu
    private void InitializeTabMenu(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if(tabLayout != null){
            for (int i=1;i<NUM_TABS;++i){
                tabLayout.addTab(tabLayout.newTab().setText(tabNames[i-1]));
            }
        }
    }

    //Load Tab Menu
    private void setTabContext(TabLayout tabLayout){
        ListView tabOne = (ListView) LayoutInflater.from(this).inflate(R.layout.activity_songs, null);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        ListView tab2 = (ListView) LayoutInflater.from(this).inflate(R.layout.activity_list, null);
        tabLayout.getTabAt(1).setCustomView(tab2);
        ListView tab3 = (ListView) LayoutInflater.from(this).inflate(R.layout.activity_list, null);
        tabLayout.getTabAt(2).setCustomView(tab3);
        ListView tab4 = (ListView) LayoutInflater.from(this).inflate(R.layout.activity_list, null);
        tabLayout.getTabAt(3).setCustomView(tab4);
        ListView tab5 = (ListView) LayoutInflater.from(this).inflate(R.layout.activity_list, null);
        tabLayout.getTabAt(4).setCustomView(tab5);
    }

    //Initialize View Pager
    private void InitializeViewPager(){
        //Associate the ViewPager with the CustomPagerAdapter
        viewPager = (ViewPager) findViewById(R.id.mainViewPager);
        if(viewPager != null){
            CustomPagerAdapter viewPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(viewPagerAdapter);
        }
    }
}

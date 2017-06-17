package com.example.ioanna.ayianapatourguide;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        ListFragment.OnFragmentInteractionListener,
        RestaurantFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Handle navigation view item clicks
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        // Create fragment object based on selected menu item
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_beaches) {
            fragment = ListFragment.newInstance(LoadBeaches(), NavMenu.ItemType.Simple, NavMenu.MenuItems.Beaches);
        } else if (id == R.id.nav_restaurants) {
            fragment = RestaurantFragment.newInstance(LoadRestaurants());
        } else if (id == R.id.nav_nightlife) {
            fragment = ListFragment.newInstance(LoadNightlife(), NavMenu.ItemType.Description, NavMenu.MenuItems.Nightlife);
        } else if (id == R.id.nav_things_to_do) {
            fragment = ListFragment.newInstance(LoadThingsToDo(), NavMenu.ItemType.Description, NavMenu.MenuItems.ThingsToDo);
        }
        // Update the fragment by replacing any existing fragment
        try {
            if (fragment != null) {
                setFragment(fragment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Close the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(String TAG, Object data) {
    }

    private ArrayList<SimpleListItem> LoadBeaches() {
        ArrayList<SimpleListItem> beaches = new ArrayList<SimpleListItem>();
        beaches.add(new SimpleListItem(getString(R.string.beach_greco), R.drawable.cavo_greco_beach));
        beaches.add(new SimpleListItem(getString(R.string.beach_nissi), R.drawable.nissi_beach));
        beaches.add(new SimpleListItem(getString(R.string.beach_korakas), R.drawable.kamara_tou_koraka));
        beaches.add(new SimpleListItem(getString(R.string.beach_macronissos), R.drawable.makronissos_beach));
        beaches.add(new SimpleListItem(getString(R.string.beach_kambouris), R.drawable.kampouris_beach));
        beaches.add(new SimpleListItem(getString(R.string.beach_landa), R.drawable.landa_beach));
        return beaches;
    }

    private ArrayList<RestaurantItem> LoadRestaurants() {
        ArrayList<RestaurantItem> restaurants = new ArrayList<RestaurantItem>();
        restaurants.add(new RestaurantItem(getString(R.string.rest_glasshouse), getString(R.string.rest_glasshouse_adrress), getString(R.string.international_cusine), getString(R.string.price4), R.drawable.glasshouse));
        restaurants.add(new RestaurantItem(getString(R.string.rest_sage), getString(R.string.rest_sage_address),  getString(R.string.international_cusine), getString(R.string.price4), R.drawable.sage));
        restaurants.add(new RestaurantItem(getString(R.string.rest_tavernaki), getString(R.string.rest_tavernaki_address),  getString(R.string.greek_cusine), getString(R.string.price2_3), R.drawable.tavernaki));
        restaurants.add(new RestaurantItem(getString(R.string.rest_quadro), getString(R.string.rest_quadro_adrress),  getString(R.string.italian_cusine), getString(R.string.price2_3), R.drawable.quadro));
        restaurants.add(new RestaurantItem(getString(R.string.rest_los_bandidos), getString(R.string.rest_los_bandidos_address),  getString(R.string.mexican_cusine),getString(R.string.price2_3), R.drawable.losbandidos));
        restaurants.add(new RestaurantItem(getString(R.string.rest_eden), getString(R.string.rest_eden_address), getString(R.string.international_cusine), getString(R.string.price2_3), R.drawable.edengarden));
        restaurants.add(new RestaurantItem(getString(R.string.rest_flames), getString(R.string.rest_flames_address),  getString(R.string.steakhouse_cusine), getString(R.string.price2_3), R.drawable.flames));
        return restaurants;
    }

    private ArrayList<SimpleListItem> LoadNightlife() {
        ArrayList<SimpleListItem> list = new ArrayList<SimpleListItem>();
        list.add(new SimpleListItem(getString(R.string.seniorfrog), getString(R.string.bar), R.drawable.seniorfrog));
        list.add(new SimpleListItem(getString(R.string.nissibay), getString(R.string.bar), R.drawable.nissibar));
        list.add(new SimpleListItem(getString(R.string.makronissos),getString(R.string.bar), R.drawable.makronissosbar));
        list.add(new SimpleListItem(getString(R.string.soho), getString(R.string.night_club), R.drawable.sosho));
        list.add(new SimpleListItem(getString(R.string.bedrock), getString(R.string.night_club), R.drawable.bedrock));
        list.add(new SimpleListItem(getString(R.string.castle), getString(R.string.night_club), R.drawable.thecastleclub));
        list.add(new SimpleListItem(getString(R.string.black_white), getString(R.string.night_club), R.drawable.blackandwhite));
        list.add(new SimpleListItem(getString(R.string.riverreggae), getString(R.string.after_party), R.drawable.riverreggae));
        return list;
    }

    private ArrayList<SimpleListItem> LoadThingsToDo() {
        ArrayList<SimpleListItem> list = new ArrayList<SimpleListItem>();
        list.add(new SimpleListItem(getString(R.string.cape_greco_park),getString(R.string.cape_greco_park_short_desc),getString(R.string.cape_greco_park_desc), R.drawable.cape_greco_palaces));
        list.add(new SimpleListItem(getString(R.string.waterWorld),getString(R.string.waterWorld_short_desc),getString(R.string.waterWorld_desc), R.drawable.waterpark));
        list.add(new SimpleListItem(getString(R.string.sculpture_park),getString(R.string.sculpture_park_short_desc),getString(R.string.sculpture_park_desc),R.drawable.sculpture_park));
        list.add(new SimpleListItem(getString(R.string.lunapark),getString(R.string.lunapark_short_desc),getString(R.string.lunapark_desc),R.drawable.lunapark));
        return list;
    }
}


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
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                                HomeFragment.OnFragmentInteractionListener,
                                                                ListFragment.OnFragmentInteractionListener,
                                                                RestaurantFragment.OnFragmentInteractionListener{

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            fragment = ListFragment.newInstance(LoadBeaches(),NavMenu.ItemType.Simple, NavMenu.MenuItems.Beaches);
        } else if (id == R.id.nav_restaurants) {
            fragment = RestaurantFragment.newInstance(LoadRestaurants());
        } else if (id == R.id.nav_nightlife) {
            fragment = ListFragment.newInstance(LoadNightlife(),NavMenu.ItemType.Description, NavMenu.MenuItems.Nightlife);
        } else if (id == R.id.nav_things_to_do) {
            fragment = ListFragment.newInstance(LoadThingsToDo(),NavMenu.ItemType.Description, NavMenu.MenuItems.ThingsToDo);
        }
        // Update the fragment by replacing any existing fragment
        try {
            if(fragment != null){
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
        //fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onFragmentInteraction(String TAG, Object data) {
        if (TAG.equals("HOME")) {
            //Do something with 'data' that comes from fragment1
        } else if (TAG.equals("BEACHES")) {

        }
    }

    private ArrayList<SimpleListItem> LoadBeaches(){
        ArrayList<SimpleListItem> beaches = new ArrayList<SimpleListItem>();
        beaches.add(new SimpleListItem("Cape Greco", "",R.drawable.cavo_greco_beach));
        beaches.add(new SimpleListItem("Nissi Bay", "Luis Fonsi + DaddyYankee",R.drawable.nissi_beach));
        beaches.add(new SimpleListItem("Kamara tou Koraka", "Jax Jones + Raye",R.drawable.kamara_tou_koraka));
        beaches.add(new SimpleListItem("Macronissos", "The Chainsmokers + Coldplay",R.drawable.makronissos_beach));
        beaches.add(new SimpleListItem("Ammos tou Kambouri", "Chained To The Rhrythm",R.drawable.kampouris_beach));
        beaches.add(new SimpleListItem("Landa", "Small sandy beach in between Nissi and Macronissos beaches",R.drawable.landa_beach));
        return beaches;
    }

    private ArrayList<RestaurantItem> LoadRestaurants(){
        ArrayList<RestaurantItem> restaurants = new ArrayList<RestaurantItem>();
        restaurants.add(new RestaurantItem("Glasshouse Lounge Restaurant", "Adams Beach Hotel, Ayia Napa","International, Mediterranean, European","$$$$",R.drawable.glasshouse));
        restaurants.add(new RestaurantItem("Sage Restaurant & Wine Bar", "Kryou Nerou 8, Agia Napa","International, Mediterranean, European","$$$$",R.drawable.sage));
        restaurants.add(new RestaurantItem("En-yevo tavernaki","Dionysiou Solomou 16, Agia Napa","Greek, Grill, Vegetarian Friendly","$$ - $$$",R.drawable.tavernaki));
        restaurants.add(new RestaurantItem("Quadro","Kryou Nerou, Ayia Napa","Italian, Pizza, Mediterranean","$$ - $$$",R.drawable.quadro));
        restaurants.add(new RestaurantItem("Los Bandidos","Ari Velouchioti 2, Ayia Napa","Mexican, Vegetarian Friendly","$$ - $$$",R.drawable.losbandidos));
        restaurants.add(new RestaurantItem("The Garden of Eden Restaurant", "Nissi Avenue 144, Ayia Napa","International, Mediterranean, European","$$ - $$$",R.drawable.edengarden));
        restaurants.add(new RestaurantItem("Flames Restaurant and Bar", "Agias Mavris Avenue 58, Ayia Napa","Steakhouse, Mediterranean, European","$$ - $$$",R.drawable.flames));
        return restaurants;
    }

    private ArrayList<SimpleListItem> LoadNightlife(){
        ArrayList<SimpleListItem> list = new ArrayList<SimpleListItem>();
        list.add(new SimpleListItem("Senior Frog's", "Bar",R.drawable.seniorfrog));
        list.add(new SimpleListItem("Nissi Bay", "Bar",R.drawable.nissibar));
        list.add(new SimpleListItem("Makronissos", "Bar",R.drawable.makronissosbar));
        list.add(new SimpleListItem("Soho", "Night Club",R.drawable.sosho));
        list.add(new SimpleListItem("Bedrock Inn", "Night Club",R.drawable.bedrock));
        list.add(new SimpleListItem("The Castle Club","Night Club",R.drawable.thecastleclub));
        list.add(new SimpleListItem("Black & White","Night Club",R.drawable.blackandwhite));
        list.add(new SimpleListItem("River Reggae","After Party Night Club",R.drawable.riverreggae));
        return list;
    }

    private ArrayList<SimpleListItem> LoadThingsToDo(){
        ArrayList<SimpleListItem> list = new ArrayList<SimpleListItem>();
        list.add(new SimpleListItem("Cape Greco National Forest Park","It is a relatively unspoilt area with great natural beauty","Explore the multitude of intriguing nature trails lined with pine trees and a diverse botanical splendour whilst pausing to take in the spectacular views from the sea cliffs looking down to the beautiful Mediterranean Sea",R.drawable.cape_greco_palaces));
        list.add(new SimpleListItem("WaterWorld Themed Waterpark","It is the largest themed waterpark in Europe and one of Cyprus's biggest attractions.","The award-winning Greek themed waterpark. With over 21 thrilling rides for all ages, the park is themed on ancient Greek mythology.Top attractions include Chariots chase, Aeolos Whirlpool, the River Odyssey and Poseidon wave pool.",R.drawable.waterpark));
        list.add(new SimpleListItem("Ayia Napa Sculpture Park","The open air sculpture museum with more than 20 sculptors from around the world","It features stunning works of art from international artists displayed in an open air museum.Located at the eastern edge of Ayia Napa the sculpture park covers an area of 20,000 sq.m. The rocky landscape slopes down to the sea and provides a serene and peaceful backdrop to this visually exciting display of modern artwork.",R.drawable.sculpture_park));
        list.add(new SimpleListItem("Parko Paliatso Fun Fair & Luna Park","Fun for all family","Parko Paliatso Luna park is a family owned funfair in the heart of Ayia Napa, on Nissi Avenue. It opened in 1999 and today covers an area of 25,000 square metres with more than 25 attractions.",R.drawable.lunapark));
        return list;
    }
}


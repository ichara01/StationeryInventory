package com.example.ioanna.music;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

public class RelatedSongsActivity extends AppCompatActivity {

    ArrayList<SongModel> songsDetails;
    GroupModel groupDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_songs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            groupDetails = extras.getParcelable("DATA");
            setTitle(groupDetails.getName() + " " + groupDetails.getGroupType());
            //Based on group details, it will retrieve related songs
            songsDetails = DummyData_GetSongs_Sample1();
            // Adapter creates list item views for each item
            // getActivity()returns the activity associated with a fragment
            SongArrayAdapter songAdapter = new SongArrayAdapter(this , songsDetails);
            ListView list = (ListView) findViewById(R.id.related_songs_list_view);
            // Attach the adapter to the listView.
            list.setAdapter(songAdapter);
        } else {
            //newString= extras.getString("STRING_I_NEED");
        }
    }

    //Dummy Data
    private ArrayList<SongModel> DummyData_GetSongs_Sample1(){
        ArrayList<SongModel> songs = new ArrayList<SongModel>();
        songs.add(new SongModel("Despacito", "Luis Fonsi + DaddyYankee",R.mipmap.song));
        songs.add(new SongModel("You Don't Know Me", "Jax Jones + Raye",R.mipmap.song));
        songs.add(new SongModel("Something Just Like This", "The Chainsmokers + Coldplay",R.mipmap.song));
        songs.add(new SongModel("Katy Perry + Skip Marley", "Chained To The Rhrythm",R.mipmap.song));
        songs.add(new SongModel("That's What I Like", "Bruno Mars",R.mipmap.song));
        songs.add(new SongModel("It Ain't Me", "Kygo + Selena Gomez",R.mipmap.song));
        songs.add(new SongModel("Shape Of You", "Ed Sheeran",R.mipmap.song));
        songs.add(new SongModel("Subeme La Radio", "Enrique Inglesias",R.mipmap.song));
        songs.add(new SongModel("Love On The Brain", "Rihanna",R.mipmap.song));
        songs.add(new SongModel("Swalla", "Jason Derulo + Nicki Minaj +Ty Dolla Sign",R.mipmap.song));
        songs.add(new SongModel("Paris", "The Chainsmokers",R.mipmap.song));
        songs.add(new SongModel("Scared To Be Lonely", "Martin Garrix + Dua Lipa",R.mipmap.song));
        return songs;
    }
}

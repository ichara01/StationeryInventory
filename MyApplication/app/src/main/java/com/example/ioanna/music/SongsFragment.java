package com.example.ioanna.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class SongsFragment extends Fragment {
    ArrayList<SongModel> songsDetails ;

    public SongsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param songs Parameter 1.
     * @return A new instance of fragment SongsFragment.
     */
    public static SongsFragment newInstance(ArrayList<SongModel> songs ) {
       SongsFragment fragment = new SongsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList ("DATA", songs);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songsDetails = getArguments().getParcelableArrayList("DATA");
        }
    }

    // Inflate the view based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.activity_songs, container, false);
        // Adapter creates list item views for each item
        // getActivity()returns the activity associated with a fragment
        SongArrayAdapter songAdapter = new SongArrayAdapter(getActivity() , songsDetails);
        ListView list = (ListView) view.findViewById(R.id.songs_list_view);
        // Attach the adapter to the listView.
        list.setAdapter(songAdapter);
        //Show related message in case of none of songs are available
        /*if(songsDetails.isEmpty()){
            TextView txtSongLabel = (TextView) view.findViewById(R.id.txtSongLabel);
            txtSongLabel.setVisibility(View.GONE);
        }*/
        return view;
    }
}
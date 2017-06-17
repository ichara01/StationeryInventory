package com.example.ioanna.music;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ioanna on 06/05/2017.
 */

public class SongArrayAdapter<S> extends ArrayAdapter<SongModel> {

    public SongArrayAdapter(Context context, ArrayList<SongModel> songs) {
        super(context, 0, songs);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_item, parent, false);
        }
        // Get the object located at this position in the list
        final SongModel currentSong = getItem(position);
        // Find the TextView in the song_item.xml layout with the ID song
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.song);
        nameTextView.setText(currentSong.getSongTitle());
        // Find the TextView in the song_item.xml layout with the ID artist
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.artist);
        numberTextView.setText(currentSong.getArtistName());
        // Find the ImageView in the song_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        iconView.setImageResource(currentSong.getAlbumImageID());

        ImageButton btnSong = (ImageButton) listItemView.findViewById(R.id.btnPlay);
        btnSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songsActivity = new Intent(getContext(), AudioPlayer.class);
                songsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                songsActivity.putExtra("DATA",currentSong);
                getContext().startActivity(songsActivity);
            }
        });

        // Return the whole list item layout so that it can be shown in the ListView
        return listItemView;
    }
}

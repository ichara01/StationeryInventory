package com.example.ioanna.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class AudioPlayer extends AppCompatActivity {

    SongModel song;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
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
            song = extras.getParcelable("DATA");
            LoadData();
        }
    }

    private void LoadData(){
        //Show song image
        ImageView iconView = (ImageView) findViewById(R.id.groupModelImg);
        iconView.setImageResource(song.getAlbumImageID());
        //Show song title
        TextView songTitle = (TextView) findViewById(R.id.songTitle);
        songTitle.setText(song.getSongTitle() + " - " + song.getArtistName());

        //mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri);
        //mediaPlayer.start();
    }
}

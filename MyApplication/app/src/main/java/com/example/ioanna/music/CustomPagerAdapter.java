package com.example.ioanna.music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static com.example.ioanna.music.MusicModel.NUM_TABS;
import static com.example.ioanna.music.MusicModel.tabNames;

/**
 * Created by Ioanna on 06/05/2017.
 */

public class CustomPagerAdapter extends FragmentPagerAdapter {

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
            switch (position) {
                case 0:
                    return SongsFragment.newInstance(DummyData_GetSongs_Sample1());
                case 1:
                    return ListFragment.newInstance(DummyData_GetGroupItems_Sample1(),position);
                case 2:
                    return ListFragment.newInstance(new ArrayList<GroupModel>(),position);
                case 3:
                    return ListFragment.newInstance(DummyData_GetGroupItems_Sample2(),position);
                case 4:
                    return ListFragment.newInstance(DummyData_GetGroupItems_Sample1(),position);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
           return tabNames[position];
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

    private ArrayList<GroupModel> DummyData_GetGroupItems_Sample1(){
        ArrayList<GroupModel> songs = new ArrayList<GroupModel>();
        songs.add(new GroupModel("Luis Fonsi + DaddyYankee",R.mipmap.album,tabNames[2]));
        songs.add(new GroupModel("Kygo + Selena Gomez",R.mipmap.album,tabNames[2]));
        songs.add(new GroupModel("Ed Sheeran",R.mipmap.album,tabNames[2]));
        songs.add(new GroupModel("Enrique Inglesias",R.mipmap.album,tabNames[2]));
        songs.add(new GroupModel("Rihanna",R.mipmap.album,tabNames[2]));
        return songs;
    }

    private ArrayList<GroupModel> DummyData_GetGroupItems_Sample2(){
        ArrayList<GroupModel> songs = new ArrayList<GroupModel>();
        songs.add(new GroupModel("Luis Fonsi + DaddyYankee",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Kygo + Selena Gomez",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Ed Sheeran",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Enrique Inglesias",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Rihanna",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Kygo + Selena Gomez",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Ed Sheeran",R.mipmap.album,tabNames[3]));
        songs.add(new GroupModel("Enrique Inglesias",R.mipmap.album,tabNames[3]));
        return songs;
    }
}

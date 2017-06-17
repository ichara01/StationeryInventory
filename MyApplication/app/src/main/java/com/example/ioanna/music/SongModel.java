package com.example.ioanna.music;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Ioanna on 06/05/2017.
 */

public class SongModel implements Parcelable {
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private String mGenre;
    private int mAlbumPhotoID;

    public SongModel(String vTitle, String vArtist, int imageResourceId)
    {
        mTitle = vTitle;
        mArtist = vArtist;
        mAlbumPhotoID = imageResourceId;
    }

    public SongModel(String vTitle, String vArtist, String vAlbum, String vGenre, int imageResourceId)
    {
        mTitle = vTitle;
        mArtist = vArtist;
        mAlbum = vAlbum;
        mGenre = vGenre;
        mAlbumPhotoID = imageResourceId;
    }

    protected SongModel(Parcel in) {
        mTitle = in.readString();
        mArtist = in.readString();
        mAlbum = in.readString();
        mGenre = in.readString();
        mAlbumPhotoID = in.readInt();
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    //Get song title
    public String getSongTitle() {
        return mTitle;
    }

   //Get song's artist
    public String getArtistName(){
        return  mArtist;
    }

    //Get Album
    public String getAlbumName(){
        return mAlbum;
    }

    //Get Genre
    public String getGenre(){
        return mGenre;
    }

    //Get image ID
    public int getAlbumImageID(){ return mAlbumPhotoID;}

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v(TAG, "Song Model - writeToParcel..."+ flags);
        dest.writeString(mTitle);
        dest.writeString(mArtist);
        dest.writeString(mAlbum);
        dest.writeString(mGenre);
        dest.writeInt(mAlbumPhotoID);
    }
}

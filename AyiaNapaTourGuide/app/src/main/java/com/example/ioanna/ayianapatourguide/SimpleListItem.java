package com.example.ioanna.ayianapatourguide;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Ioanna on 05/06/2017.
 */

public class SimpleListItem implements Parcelable {

    private int mImgID;
    private String mHeader;
    private String mShortDescription;
    private String mDescription;

    SimpleListItem(String mTitle, String mDescription){
        this.mHeader = mTitle;
        this.mDescription = mDescription;
        this.mShortDescription = mDescription;
    }

    SimpleListItem(String mTitle, int imgId){
        this.mHeader = mTitle;
        this.mImgID = imgId;
    }

    SimpleListItem(String mTitle, String mDescription, int imgId){
        this.mHeader = mTitle;
        this.mShortDescription = mDescription;
        this.mDescription = mDescription;
        this.mImgID = imgId;
    }

    SimpleListItem(String mTitle, String mShortDescription, String mDescription, int imgId){
        this.mHeader = mTitle;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mImgID = imgId;
    }

    public SimpleListItem(Parcel in) {
        mHeader = in.readString();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mImgID = in.readInt();
    }

    public String getHeader() {
        return mHeader;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public int getImageID() { return mImgID; }

    public void updateHeader(String header) {
        this.mHeader = header;
    }

    public void updateDescription(String description) {
        this.mDescription = mDescription;
    }

    public void updateImage(int imageID) {this.mImgID = imageID; }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v(TAG, "Simple List Item - writeToParcel..."+ flags);
        dest.writeString(mHeader);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeInt(mImgID);
    }
    
    public static final Creator<SimpleListItem> CREATOR = new Creator<SimpleListItem>() {
        @Override
        public SimpleListItem createFromParcel(Parcel in) {
            return new SimpleListItem(in);
        }

        @Override
        public SimpleListItem[] newArray(int size) {
            return new SimpleListItem[size];
        }
    };
    
    @Override
    public String toString() {
        String s = "Title: " + mHeader + ", "  + ", Description:" + mDescription + "\n";
        return s;
    }
}

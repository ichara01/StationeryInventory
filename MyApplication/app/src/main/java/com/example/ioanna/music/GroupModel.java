package com.example.ioanna.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ioanna on 13/05/2017.
 */

public class GroupModel implements Parcelable {
    private String mName;
    private int mImageID;
    private String mGroupType;

    GroupModel(String albumName, String groupType){
        mName = albumName;
        mGroupType = groupType;
    }

    GroupModel(String name, int imageID, String groupType){
        mName = name;
        mImageID = imageID;
        mGroupType = groupType;
    }

    protected GroupModel(Parcel in) {
        mName = in.readString();
        mImageID = in.readInt();
        mGroupType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mImageID);
        dest.writeString(mGroupType);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<GroupModel> CREATOR = new Creator<GroupModel>() {
        @Override
        public GroupModel createFromParcel(Parcel in) {
            return new GroupModel(in);
        }

        @Override
        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };

    public String getName(){ return mName; }

    public int getImageID(){ return mImageID; }

    public String getGroupType(){ return mGroupType; }
}

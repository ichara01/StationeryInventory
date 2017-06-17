package com.example.ioanna.ayianapatourguide;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Ioanna on 05/06/2017.
 */

public class RestaurantItem implements Parcelable {

    private int mImgID;
    private String mName;
    private String mAddress;
    private String mPrice;
    private String mCuisines;

    RestaurantItem(String name, String address,String cusines, String price, int imgId){
        this.mName = name;
        this.mAddress = address;
        this.mPrice = price;
        this.mCuisines = cusines;
        this.mImgID = imgId;
    }

    public RestaurantItem(Parcel in) {
        mName = in.readString();
        mAddress = in.readString();
        mPrice = in.readString();
        mCuisines = in.readString();
        mImgID = in.readInt();
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCuisines() {
        return mCuisines;
    }

    public int getImageID() { return mImgID; }

    public void updateName(String name) {
        this.mName = name;
    }

    public void updateAddress(String address) {
        this.mAddress = address;
    }

    public void updatePrice(String price) {
        this.mPrice = price;
    }

    public void updateCusines(String cuisines) {
        this.mCuisines = cuisines;
    }

    public void updateImage(int imageID) {this.mImgID = imageID; }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v(TAG, "Restaurant - writeToParcel..."+ flags);
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mPrice);
        dest.writeString(mCuisines);
        dest.writeInt(mImgID);
    }
    
    public static final Creator<RestaurantItem> CREATOR = new Creator<RestaurantItem>() {
        @Override
        public RestaurantItem createFromParcel(Parcel in) {
            return new RestaurantItem(in);
        }

        @Override
        public RestaurantItem[] newArray(int size) {
            return new RestaurantItem[size];
        }
    };
    
    @Override
    public String toString() {
        String s = "Name: " + mName + ", "  + ", Address:" + mAddress + ", "  + ", Price:" + mPrice + ", "  + ", Cusines:" + mCuisines + "\n";
        return s;
    }
}

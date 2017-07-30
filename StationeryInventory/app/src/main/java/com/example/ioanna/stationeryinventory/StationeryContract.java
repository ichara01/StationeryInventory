package com.example.ioanna.stationeryinventory;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ioanna on 24/07/2017.
 */

public final class StationeryContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.stationeryinventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Stationery
    public static final String PATH_STATIONERY = "stationery";
    public static final String PATH_STATIONERY_ID = "stationery/#";
    //The MIME type for a list of stationery.
    public static final String CONTENT_STATIONERY_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATIONERY;
    //The MIME type for a single stationery.
    public static final String CONTENT_STATIONERY_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATIONERY;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private StationeryContract() {
    }

    /* Inner class that defines the table contents */
    public static class StationeryEntry implements BaseColumns {
        public static final Uri STATIONERY_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STATIONERY);
        public static final Uri STATIONERY_URI_ID = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STATIONERY_ID);

        public static final String TABLE_NAME = "stationery";
        public static final String COLUMN_STATIONERY_ITEMNAME = "itemName";
        public static final String COLUMN_STATIONERY_ITEMIMAGENAME = "imageName";
        public static final String COLUMN_STATIONERY_ITEMIMAGEDATA = "imageData";
        public static final String COLUMN_STATIONERY_SUPPLIER = "supplier";
        public static final String COLUMN_STATIONERY_SUPPLIER_PHONE = "supplierPhone";
        public static final String COLUMN_STATIONERY_QUANTITY = "quantity";
        public static final String COLUMN_STATIONERY_PRICE = "price";
        public static final String COLUMN_STATIONERY_CREATEDON = "createdOn";
    }

    // Stationery
    protected static final String SQL_CREATE_STATIONERY_ENTRIES =
            "CREATE TABLE " + StationeryEntry.TABLE_NAME + " (" +
                    StationeryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    StationeryEntry.COLUMN_STATIONERY_SUPPLIER + " TEXT NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE + " TEXT NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_ITEMNAME + " TEXT NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_ITEMIMAGENAME + " TEXT  NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA + " BLOB NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_PRICE + " NUMERIC NOT NULL ," +
                    StationeryEntry.COLUMN_STATIONERY_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                    StationeryEntry.COLUMN_STATIONERY_CREATEDON + " DEFAULT CURRENT_TIMESTAMP" +
                    " )";

    protected static final String SQL_DELETE_STATIONERY_ENTRIES =
            "DROP TABLE IF EXISTS " + StationeryEntry.TABLE_NAME;
}

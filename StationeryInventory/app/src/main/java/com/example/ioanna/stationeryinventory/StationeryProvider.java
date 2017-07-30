package com.example.ioanna.stationeryinventory;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.ioanna.stationeryinventory.StationeryContract.CONTENT_AUTHORITY;
import static com.example.ioanna.stationeryinventory.StationeryContract.PATH_STATIONERY;
import static com.example.ioanna.stationeryinventory.StationeryContract.PATH_STATIONERY_ID;

/**
 * Created by Ioanna on 25/07/2017.
 */

/**
 * {@link ContentProvider} for Stationery app.
 */
public class StationeryProvider extends ContentProvider {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = StationeryProvider.class.getSimpleName();

    private static final int STATIONERY = 1;
    private static final int STATIONERY_ID = 2;

    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_STATIONERY, STATIONERY);
        sURIMatcher.addURI(CONTENT_AUTHORITY, PATH_STATIONERY_ID, STATIONERY_ID);
    }

    /**
     * Database helper object
     */
    private StationeryDbHelper mDbHelper;

    //Initialize the provider
    @Override
    public boolean onCreate() {
        Log.i("StaioneryProvider: ", "Create Database");
        mDbHelper = new StationeryDbHelper(getContext());
        return true;
    }

    //Retrieve data from your provider
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // This cursor will hold the result of the query
        Cursor cursor = null;
        // Figure out if the URI matcher can match the URI to a specific code
        int match = sURIMatcher.match(uri);
        switch (match) {
            case STATIONERY:
                cursor = database.query(StationeryContract.StationeryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case STATIONERY_ID:
                selection = StationeryContract.StationeryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(StationeryContract.StationeryEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case STATIONERY:
                return StationeryContract.CONTENT_STATIONERY_LIST_TYPE;
            case STATIONERY_ID:
                return StationeryContract.CONTENT_STATIONERY_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case STATIONERY:
                return insertStationery(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a stationery stationery_overall_item into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertStationery(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Insert the new stationery with the given values
        long id = database.insert(StationeryContract.StationeryEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case STATIONERY:
                // Delete all rows that match the selection and selection args
                return database.delete(StationeryContract.StationeryEntry.TABLE_NAME, selection, selectionArgs);
            case STATIONERY_ID:
                // Delete a single row given by the ID in the URI
                selection = StationeryContract.StationeryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(StationeryContract.StationeryEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sURIMatcher.match(uri);
        switch (match) {
            case STATIONERY:
                return updateStationery(uri, values, selection, selectionArgs);
            case STATIONERY_ID:
                selection = StationeryContract.StationeryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateStationery(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateStationery(Uri uri, ContentValues values, String selection, String[] selectionArg) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        // Update existing stationery with the given values
        return database.update(StationeryContract.StationeryEntry.TABLE_NAME, values, selection, selectionArg);
    }
}

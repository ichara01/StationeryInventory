package com.example.ioanna.stationeryinventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ioanna.stationeryinventory.DbBitmapUtility.getImage;

/**
 * Created by Ioanna on 29/07/2017.
 * Define the adapter to describe the process of projecting the Cursor's data into a View.
 */

public class StationeryCursorAdapter extends CursorAdapter {

    static class ViewHolder {
        @BindView(R.id.txt_overall_name)
        TextView nameTextView;
        @BindView(R.id.overall_item_img)
        ImageView imageView;
        @BindView(R.id.txt_overall_code)
        TextView codeTextView;
        @BindView(R.id.txt_overall_brand)
        TextView brandTextView;
        @BindView(R.id.txt_overall_quantity)
        TextView quantityTextView;
        @BindView(R.id.txt_overall_price)
        TextView priceTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //Reference the child views for later actions
    ViewHolder holder;

    public StationeryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.stationery_overall_item, parent, false);
        //Cache view fields into the holder
        holder = new ViewHolder(itemView);
        return itemView;
    }

    /**
     * This method binds the stationery data (in the current row pointed to by cursor) to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Extract properties from cursor
        int code = cursor.getInt(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry._ID));
        byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME));
        String supplier = cursor.getString(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE));
        // Populate fields with extracted properties
        holder.codeTextView.setText(String.valueOf(code));
        holder.nameTextView.setText(name);
        holder.brandTextView.setText(supplier);
        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.priceTextView.setText(Double.toString(price));
        if (image != null && image.length > 0) {
            holder.imageView.setImageBitmap(getImage(image));
        }
    }
}

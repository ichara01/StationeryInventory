package com.example.ioanna.stationeryinventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ioanna.stationeryinventory.DbBitmapUtility.getImage;

/**
 * Created by Ioanna on 29/07/2017.
 * Define the adapter to describe the process of projecting the Cursor's data into a View.
 */

public class AvailableStationeryForSaleCursorAdapter extends CursorAdapter {

    static class ViewHolder {
        @BindView(R.id.txt_name)
        TextView nameTextView;
        @BindView(R.id.txt_code)
        TextView codeTextView;
        @BindView(R.id.txt_supplier)
        TextView supplierTextView;
        @BindView(R.id.txt_quantity)
        TextView quantityTextView;
        @BindView(R.id.txt_price)
        TextView priceTextView;
        @BindView(R.id.item_img)
        ImageView imageView;
        @BindView(R.id.btn_sale)
        Button btnSale;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //Reference the child views for later actions
    ViewHolder holder;

    public AvailableStationeryForSaleCursorAdapter(Context context, Cursor cursor) {
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.stationery_item, parent, false);
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
        String name = cursor.getString(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME));
        byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA));
        String supplier = cursor.getString(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE));
        // Populate fields with extracted properties
        holder.codeTextView.setText(String.valueOf(code));
        holder.nameTextView.setText(name);
        holder.supplierTextView.setText(supplier);
        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.priceTextView.setText(Double.toString(price));
        if (image != null && image.length > 0) {
            holder.imageView.setImageBitmap(getImage(image));
        }
        //On click on Sale button reduce stationery quantity
        holder.btnSale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reduceQuantity(v);
            }
        });
    }

    // Reduce stationery quantity
    private void reduceQuantity(View v) {
        // Get stationery unique ID
        LinearLayout lr = (LinearLayout) v.getParent();
        TextView codeTextView = (TextView) lr.findViewById(R.id.txt_code);
        TextView quanityTextView = (TextView) lr.findViewById(R.id.txt_quantity);
        String code = codeTextView.getText().toString();
        String quantity = quanityTextView.getText().toString();
        // Update stationery
        updateStationery(v.getContext(), Integer.parseInt(code), Integer.parseInt(quantity));
    }

    private boolean updateStationery(Context context, int id, int quantity) {
        if (quantity > 0) {
            // Create a ContentValues object where column names are the keys
            ContentValues values = new ContentValues();
            values.put(StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY, quantity - 1);
            int numOfRows = context.getContentResolver().update(ContentUris.withAppendedId(StationeryContract.StationeryEntry.STATIONERY_URI, id), values, null, null);
            // Show a toast message depending on whether or not the update was successful
            if (numOfRows == 1) {
                // Notify that a row was updated and attempt to sync changes
                context.getContentResolver().notifyChange(StationeryContract.StationeryEntry.STATIONERY_URI, null);
            } else {
                return false;
            }
        }
        return true;
    }
}

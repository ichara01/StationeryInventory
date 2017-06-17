package com.example.ioanna.ayianapatourguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ioanna on 05/06/2017.
 */

public class RestaurantArrayAdapter extends ArrayAdapter<RestaurantItem> {

    public RestaurantArrayAdapter(Context context, ArrayList<RestaurantItem> items) {
        super(context, 0, items);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        }
        // Get the object located at this position in the list
        final RestaurantItem currentItem = getItem(position);
        // Set name
        TextView headerTextView = (TextView) listItemView.findViewById(R.id.restaurant_name);
        headerTextView.setText(currentItem.getName());
        // Set address
        TextView addressTextView = (TextView) listItemView.findViewById(R.id.restaurant_address);
        addressTextView.setText(currentItem.getAddress());
        // Set price
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.restaurant_price);
        priceTextView.setText(currentItem.getPrice());
        // Set cusines
        TextView cusinesTextView = (TextView) listItemView.findViewById(R.id.restaurant_cusines);
        cusinesTextView.setText(currentItem.getCuisines());
        // Set Image item
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.simple_item_img);
        iconView.setImageResource(currentItem.getImageID());
        return listItemView;
    }

}
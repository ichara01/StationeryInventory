package com.example.ioanna.ayianapatourguide;

import android.content.Context;
import android.os.Parcelable;
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

public class SimpleItemArrayAdapter extends ArrayAdapter<SimpleListItem> {

    public SimpleItemArrayAdapter(Context context, ArrayList<SimpleListItem> items) {
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
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item, parent, false);
        }
        // Get the object located at this position in the list
        final SimpleListItem currentItem = getItem(position);
        // Set Header
        TextView headerTextView = (TextView) listItemView.findViewById(R.id.simple_item_text_header);
        headerTextView.setText(currentItem.getHeader());
        // Set Image item
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.simple_item_img);
        iconView.setImageResource(currentItem.getImageID());
        //Button btnMore = (Button) listItemView.findViewById(R.id.simple_item_btn_more);
        // Return the whole list item layout so that it can be shown in the ListView
        return listItemView;
    }

}
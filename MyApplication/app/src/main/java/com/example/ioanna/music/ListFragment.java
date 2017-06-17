package com.example.ioanna.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    ArrayList<GroupModel> groupItems ;
    int pos;

    public ListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param items Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance(ArrayList<GroupModel> items,int position) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList ("DATA", items);
        args.putInt ("POS", position);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle params = getArguments();
        if (params != null) {
            groupItems = params.getParcelableArrayList("DATA");
            pos = params.getInt("POS");
        }
    }

    // Inflate the view based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.activity_list, container, false);
        // Adapter creates list item views for each item
        // getActivity()returns the activity associated with a fragment
        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity() , groupItems);
        ListView list = (ListView) view.findViewById(R.id.list_view);
        // Attach the adapter to the listView.
        list.setAdapter(adapter);

        TextView txtListLabel = (TextView) view.findViewById(R.id.txtListLabel);
        switch (pos) {
            case 1:
                if (groupItems.isEmpty())
                    txtListLabel.setText(getString(R.string.no_albums));
                    //txtListLabel.setVisibility(View.GONE);
                else
                    txtListLabel.setText(getString(R.string.albums_description));
                break;
            case 2:
                if (groupItems.isEmpty())
                    txtListLabel.setText(getString(R.string.no_artists));
                else
                    txtListLabel.setText(getString(R.string.artists_description));
                break;
            case 3:
                if (groupItems.isEmpty())
                    txtListLabel.setText(getString(R.string.no_genres));
                else
                    txtListLabel.setText(getString(R.string.genres_description));
                break;
            case 4:
                if (groupItems.isEmpty())
                    txtListLabel.setText(getString(R.string.no_playlists));
                else
                    txtListLabel.setText(getString(R.string.playlists_description));
                break;
        }
        return view;
    }
}

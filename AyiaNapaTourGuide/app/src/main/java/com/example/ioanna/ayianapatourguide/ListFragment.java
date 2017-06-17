package com.example.ioanna.ayianapatourguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    private static final String ARG_PARAM1 = "DATA";
    private static final String ARG_PARAM2 = "TYPE";
    private static final String ARG_PARAM3 = "MENU";

    //Parameters
    ArrayList<SimpleListItem> items ;
    NavMenu.ItemType itemType;
    NavMenu.MenuItems menu;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param items Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance(ArrayList<SimpleListItem> items, NavMenu.ItemType itemType, NavMenu.MenuItems menu) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, items);
        args.putSerializable(ARG_PARAM2, itemType);
        args.putSerializable(ARG_PARAM3,menu);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = getArguments().getParcelableArrayList(ARG_PARAM1);
            itemType = (NavMenu.ItemType) getArguments().getSerializable(ARG_PARAM2);
            menu = (NavMenu.MenuItems) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // Adapter creates list item view for each item
        // getActivity()returns the activity associated with a fragment
        // Simple data
        if (itemType.equals(NavMenu.ItemType.Simple)){
            SimpleItemArrayAdapter adapter = new SimpleItemArrayAdapter(getActivity() , items);
            ListView list = (ListView) view.findViewById(R.id.simple_list_view);
            // Attach the adapter to the listView.
            list.setAdapter(adapter);
        } else{
            ItemArrayAdapter adapter = new ItemArrayAdapter(getActivity() , items);
            ListView list = (ListView) view.findViewById(R.id.simple_list_view);
            // Attach the adapter to the listView.
            list.setAdapter(adapter);
        }
        // Set navigation title
        SetTitle();
        return view;
    }

    // Set navigation title based on selected menu
    private void SetTitle(){
        switch (menu) {
            case Beaches:getActivity().setTitle(getString(R.string.nav_beaches));
                            break;
            case Restaurants:getActivity().setTitle(getString(R.string.nav_restaurants));
                            break;
            case Nightlife:getActivity().setTitle(getString(R.string.nav_nightlife));
                            break;
            case ThingsToDo:getActivity().setTitle(getString(R.string.nav_things_to_do));
                            break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String TAG, Object data);
    }
}

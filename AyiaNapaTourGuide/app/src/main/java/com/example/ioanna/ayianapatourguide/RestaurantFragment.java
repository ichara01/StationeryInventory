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
 * {@link RestaurantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {
    private static final String ARG_PARAM1 = "DATA";

    //Parameters
    ArrayList<RestaurantItem> items ;

    private OnFragmentInteractionListener mListener;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param items Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    public static RestaurantFragment newInstance(ArrayList<RestaurantItem> items) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, items);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        // Adapter creates list item view for each item
        // getActivity()returns the activity associated with a fragment
        RestaurantArrayAdapter adapter = new RestaurantArrayAdapter(getActivity(), items);
        ListView list = (ListView) view.findViewById(R.id.restaurants_list_view);
        // Attach the adapter to the listView.
        list.setAdapter(adapter);
        // Set navigation title
        getActivity().setTitle(getString(R.string.nav_restaurants));
        return view;
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

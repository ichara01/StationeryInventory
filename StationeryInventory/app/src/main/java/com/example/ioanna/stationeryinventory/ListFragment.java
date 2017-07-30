package com.example.ioanna.stationeryinventory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.ioanna.stationeryinventory.StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER_PHONE;
import static com.example.ioanna.stationeryinventory.StationeryContract.StationeryEntry.STATIONERY_URI;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;
    private static StationeryCursorAdapter mCursorAdapter;
    private OnFragmentInteractionListener mListener;

    public static final String KEY_NEW_NAME = "NEW";
    public static final String KEY_ID = "ID";
    ListView list;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // Attach the adapter to the listView
        list = (ListView) view.findViewById(R.id.overall_list_view);
        mCursorAdapter = new StationeryCursorAdapter(view.getContext(), null);
        list.setAdapter(mCursorAdapter);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = view.findViewById(R.id.empty_view);
        list.setEmptyView(emptyView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditStationery.class);
                intent.putExtra(KEY_ID, id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditStationery.class);
                startActivity(intent);
            }
        });
        // Set navigation title based on selected menu
        getActivity().setTitle(getString(R.string.stationery_inv));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StationeryContract.StationeryEntry._ID,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE

        };

        String sortOrder = StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME + " ASC";

        // This loader will execute the ContextProvider's query method on a background thread
        return new CursorLoader(getActivity(), //Parent activity context
                STATIONERY_URI,  // The content URI
                projection,                       // The columns to return for each row
                null,                             // No selection clause
                null,                             // No selection arguments
                sortOrder);                       // Sorting by name

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update StationeryCursorAdapter with this new cursor containing updated data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String TAG, Object data);
    }
}

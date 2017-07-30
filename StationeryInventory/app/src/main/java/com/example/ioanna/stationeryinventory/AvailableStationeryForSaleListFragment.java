package com.example.ioanna.stationeryinventory;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import static com.example.ioanna.stationeryinventory.StationeryContract.StationeryEntry.STATIONERY_URI;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AvailableStationeryForSaleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AvailableStationeryForSaleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailableStationeryForSaleListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private static AvailableStationeryForSaleCursorAdapter mCursorAdapter;
    private OnFragmentInteractionListener mListener;

    ListView list;

    public AvailableStationeryForSaleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListFragment.
     */
    public static AvailableStationeryForSaleListFragment newInstance() {
        AvailableStationeryForSaleListFragment fragment = new AvailableStationeryForSaleListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_available_stationery_for_sale_list, container, false);
        // Attach the adapter to the listView
        list = (ListView) view.findViewById(R.id.sale_list_view);
        mCursorAdapter = new AvailableStationeryForSaleCursorAdapter(view.getContext(), null);
        list.setAdapter(mCursorAdapter);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = view.findViewById(R.id.empty_view);
        list.setEmptyView(emptyView);
        // Set navigation title based on selected menu
        getActivity().setTitle(getString(R.string.available_stationery));
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
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMIMAGEDATA,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_SUPPLIER,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY,
                StationeryContract.StationeryEntry.COLUMN_STATIONERY_PRICE

        };

        // Get stationeries whose quantity is greater than 0
        String selectionClause = StationeryContract.StationeryEntry.COLUMN_STATIONERY_QUANTITY + " > ?";
        String[] selectionArgs = new String[]{String.valueOf(0)};

        String sortOrder = StationeryContract.StationeryEntry.COLUMN_STATIONERY_ITEMNAME + " ASC";

        // This loader will execute the ContextProvider's query method on a background thread
        return new CursorLoader(getActivity(), //Parent activity context
                STATIONERY_URI,         // The content URI
                projection,             // The columns to return for each row
                selectionClause,        // No selection clause
                selectionArgs,         // No selection arguments
                sortOrder);             // Sorting by name

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

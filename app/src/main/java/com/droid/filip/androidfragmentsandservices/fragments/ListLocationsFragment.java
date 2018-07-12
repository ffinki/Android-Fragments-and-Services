package com.droid.filip.androidfragmentsandservices.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.mocks.MockedResponse;

public class ListLocationsFragment extends ListFragment {

    private MainActivity activity;
    private int shownPosition = 0;

    public ListLocationsFragment() {
        super();
    }

    public static ListLocationsFragment newInstance() {
        ListLocationsFragment locationsFragment = new ListLocationsFragment();
        return locationsFragment;
    }

    public static ListLocationsFragment newInstance(int index) {
        ListLocationsFragment locationsFragment = new ListLocationsFragment();
        Bundle args = new Bundle();
        args.putInt("SAVED_POSITION", index);
        locationsFragment.setArguments(args);
        return locationsFragment;
    }

    public static ListLocationsFragment newInstance(Bundle bundle) {
        int getShownIndex = bundle.getInt("SAVED_POSITION");
        return newInstance(getShownIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            shownPosition = savedInstanceState.getInt("SAVED_POSITION");
        }
        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, MockedResponse.getMockedCities()));
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelection(shownPosition);
        //activity.showDetails(shownPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SAVED_POSITION", shownPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        activity.showDetails(position);
        shownPosition = position;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}

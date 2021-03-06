package com.droid.filip.androidfragmentsandservices.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.R;
import com.droid.filip.androidfragmentsandservices.mocks.MockedResponse;
import com.droid.filip.androidfragmentsandservices.singleton.GsonSingleton;
import com.droid.filip.androidfragmentsandservices.stakes.Location;
import com.droid.filip.androidfragmentsandservices.stakes.LocationsResponse;
import com.google.gson.reflect.TypeToken;

public class ListLocationsFragment extends ListFragment {

    private MainActivity activity;
    private int shownPosition = 0;
    private LocationsResponse response;

    public ListLocationsFragment() {
        super();
    }

    public static ListLocationsFragment newInstance() {
        ListLocationsFragment locationsFragment = new ListLocationsFragment();
        return locationsFragment;
    }

    public static ListLocationsFragment newInstance(int index, String strResponse) {
        ListLocationsFragment locationsFragment = new ListLocationsFragment();
        Bundle args = new Bundle();
        args.putInt("SAVED_POSITION", index);
        args.putString("LOCATIONS", strResponse);
        locationsFragment.setArguments(args);
        return locationsFragment;
    }

    public static ListLocationsFragment newInstance(Bundle bundle) {
        int getShownIndex = bundle.getInt("SAVED_POSITION");
        String strResponse = bundle.getString("LOCATIONS");
        return newInstance(getShownIndex, strResponse);
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
            response = GsonSingleton.getInstance().fromJson(
                savedInstanceState.getString("LOCATIONS"), LocationsResponse.class);
        }
        if (response == null) {
            response = activity.getLocationsResponse();
        }
        setListAdapter(new ArrayAdapter<String>(
                getActivity(), R.layout.list_cities_list_item, convertCityObjectsToTitles()));
        ListView listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelection(shownPosition);
        //activity.showDetails(shownPosition);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SAVED_POSITION", shownPosition);
        outState.putString("LOCATIONS", GsonSingleton.getInstance().toJson(
                response, new TypeToken<LocationsResponse>(){}.getType()
        ));
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

    private String[] convertCityObjectsToTitles() {
        Location locs[] = response.getGeonames();
        String[] titles = new String[locs.length];
        for (int i=0; i<titles.length; i++) {
            titles[i] = locs[i].getName();
        }
        return titles;
    }
}

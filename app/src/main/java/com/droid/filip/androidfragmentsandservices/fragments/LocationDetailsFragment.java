package com.droid.filip.androidfragmentsandservices.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droid.filip.androidfragmentsandservices.R;
import com.droid.filip.androidfragmentsandservices.mocks.MockedResponse;
import com.droid.filip.androidfragmentsandservices.stakes.Location;

import org.w3c.dom.Text;

public class LocationDetailsFragment extends Fragment {

    private int index = 0;

    public static LocationDetailsFragment newInstance(int index) {
        LocationDetailsFragment detailsFragment = new LocationDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("SAVED_POSITION", index);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    public static LocationDetailsFragment newInstance(Bundle args) {
        int index = args.getInt("SAVED_POSITION");
        return newInstance(index);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt("SAVED_POSITION");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_details, container, false);
        TextView geoId = (TextView)v.findViewById(R.id.text_location_id);
        TextView name = (TextView)v.findViewById(R.id.text_location_name);
        TextView countryCode = (TextView)v.findViewById(R.id.text_location_countryCode);
        TextView fclName = (TextView)v.findViewById(R.id.text_location_fclName);
        TextView toponym = (TextView)v.findViewById(R.id.text_location_toponym);
        TextView codeName = (TextView)v.findViewById(R.id.text_location_codeName);
        TextView wiki = (TextView)v.findViewById(R.id.text_location_wiki);
        TextView fcl = (TextView)v.findViewById(R.id.text_location_fcl);
        TextView fcode = (TextView)v.findViewById(R.id.text_location_fcode);
        TextView population = (TextView)v.findViewById(R.id.text_location_population);
        TextView latitude = (TextView)v.findViewById(R.id.text_location_latitude);
        TextView longitude = (TextView)v.findViewById(R.id.text_location_longitude);
        //
        Location currentLocation = MockedResponse.getMockedData().getGeonames()[index];
        //
        geoId.setText(String.valueOf(currentLocation.getGeonameId()));
        name.setText(currentLocation.getName());
        countryCode.setText(currentLocation.getCountrycode());
        fclName.setText(currentLocation.getFclName());
        toponym.setText(currentLocation.getToponymName());
        codeName.setText(currentLocation.getFcodeName());
        wiki.setText(currentLocation.getWikipedia());
        fcl.setText(currentLocation.getFcl());
        fcode.setText(currentLocation.getFcode());
        population.setText(String.valueOf(currentLocation.getPopulation()));
        latitude.setText(String.valueOf(currentLocation.getLat()));
        longitude.setText(String.valueOf(currentLocation.getLng()));
        //
        return v;
    }

    public int getShownIndex() {
        return index;
    }
}

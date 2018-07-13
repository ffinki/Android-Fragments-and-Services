package com.droid.filip.androidfragmentsandservices;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.droid.filip.androidfragmentsandservices.fragments.AsyncReferencerFragment;
import com.droid.filip.androidfragmentsandservices.fragments.ListLocationsFragment;
import com.droid.filip.androidfragmentsandservices.fragments.LocationDetailsFragment;
import com.droid.filip.androidfragmentsandservices.singleton.GsonSingleton;
import com.droid.filip.androidfragmentsandservices.stakes.Location;
import com.droid.filip.androidfragmentsandservices.stakes.LocationsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.view.Window.FEATURE_ACTION_BAR;

public class MainActivity extends AppCompatActivity {

    AsyncReferencerFragment progressBarFragment = null;
    ProgressBar pb = null;
    private FrameLayout listCitiesLayout;
    private FrameLayout cityDetailsLayout;
    //
    private LocationsResponse locationsResponse;
    private String temporatyLocationResponse;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarFragment = AsyncReferencerFragment.establishRetainedAsyncReferencerFragment(this);
        requestWindowFeature(FEATURE_ACTION_BAR);
        setContentView(R.layout.main_activity);
        //
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
        }
        //
        pb = (ProgressBar)findViewById(R.id.progress_bar);
        pb.setSaveEnabled(true);
        //
        listCitiesLayout = (FrameLayout)findViewById(R.id.list_locations);
        cityDetailsLayout = (FrameLayout)findViewById(R.id.location_details);
        locationsResponse = null;
        temporatyLocationResponse = null;
        gson = GsonSingleton.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        temporatyLocationResponse = savedInstanceState.getString("LOCATION");
        locationsResponse = gson.fromJson(temporatyLocationResponse, LocationsResponse.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationsResponse == null)
            progressBarFragment.startFragmentProgressBar();
        else
            showDetails(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("LOCATION", temporatyLocationResponse);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh_icon:
                if (isMultiPane())
                    showDetails(0);
                else {
                    LocationDetailsFragment detailsFragment = (LocationDetailsFragment)
                            getSupportFragmentManager().findFragmentById(R.id.location_details);
                    if (detailsFragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.remove(detailsFragment);
                        ListLocationsFragment locationsFragment = (ListLocationsFragment)
                                getSupportFragmentManager().findFragmentById(R.id.list_locations);
                        if (locationsFragment == null) {
                            locationsFragment = ListLocationsFragment.newInstance(0, temporatyLocationResponse);
                            ft.replace(R.id.list_locations, locationsFragment);
                        }
                        ft.commit();
                    }

                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isMultiPane() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void showDetails(int index) {
        ListLocationsFragment locationsFragment = (ListLocationsFragment)
                getSupportFragmentManager().findFragmentById(R.id.list_locations);
        LocationDetailsFragment detailsFragment = (LocationDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.location_details);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //We are in a landscape mode
        if (isMultiPane()) {
            if (locationsFragment == null) {
                locationsFragment = ListLocationsFragment.newInstance(index, temporatyLocationResponse);
                ft.replace(R.id.list_locations, locationsFragment);
            }
            if (detailsFragment == null || detailsFragment.getShownIndex() != index) {
                String strLocation = gson.toJson(locationsResponse.getGeonames()[index], new TypeToken<Location>(){}.getType());
                detailsFragment = LocationDetailsFragment.newInstance(index, strLocation);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.location_details, detailsFragment);
            }
        }
        //We are in a portrait mode
        else {
            //we are on beginning
            if (locationsFragment == null && detailsFragment == null) {
                locationsFragment = ListLocationsFragment.newInstance(index, temporatyLocationResponse);
                ft.replace(R.id.list_locations, locationsFragment);
            }
            //we come from landscape mode
            else if (locationsFragment != null && detailsFragment != null) {
                ft.remove(detailsFragment);
            }
            //we click in some menu item
            else if (locationsFragment != null && detailsFragment == null) {
                ft.remove(locationsFragment);
                String strLocation = gson.toJson(locationsResponse.getGeonames()[index], new TypeToken<Location>(){}.getType());
                detailsFragment = LocationDetailsFragment.newInstance(index, strLocation);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.location_details, detailsFragment);
            }
        }
        ft.commit();
    }

    public void saveListAfterAsyncTaskEnds(String httpResponse) {
        temporatyLocationResponse = httpResponse;
        locationsResponse = gson.fromJson(temporatyLocationResponse, LocationsResponse.class);
    }

    public LocationsResponse getLocationsResponse() {
        return locationsResponse;
    }
}

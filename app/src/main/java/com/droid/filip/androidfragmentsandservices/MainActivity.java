package com.droid.filip.androidfragmentsandservices;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droid.filip.androidfragmentsandservices.fragments.AsyncReferencerFragment;
import com.droid.filip.androidfragmentsandservices.fragments.ListLocationsFragment;
import com.droid.filip.androidfragmentsandservices.fragments.LocationDetailsFragment;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.Window.FEATURE_ACTION_BAR;

public class MainActivity extends AppCompatActivity {

    AsyncReferencerFragment progressBarFragment = null;
    ProgressBar pb = null;
    private boolean progressBarDone = false;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        progressBarDone = savedInstanceState.getBoolean("PROGRESS_DONE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!progressBarDone)
            progressBarFragment.startFragmentProgressBar();
        else
            showDetails(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("PROGRESS_DONE", progressBarDone);
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
                            locationsFragment = ListLocationsFragment.newInstance();
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
                locationsFragment = ListLocationsFragment.newInstance(index);
                ft.replace(R.id.list_locations, locationsFragment);
            }
            if (detailsFragment == null || detailsFragment.getShownIndex() != index) {
                detailsFragment = LocationDetailsFragment.newInstance(index);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.location_details, detailsFragment);
            }
        }
        //We are in a portrait mode
        else {
            //we are on beginning
            if (locationsFragment == null && detailsFragment == null) {
                locationsFragment = ListLocationsFragment.newInstance(index);
                ft.replace(R.id.list_locations, locationsFragment);
            }
            //we come from landscape mode
            else if (locationsFragment != null && detailsFragment != null) {
                ft.remove(detailsFragment);
            }
            //we click in some menu item
            else if (locationsFragment != null && detailsFragment == null) {
                ft.remove(locationsFragment);
                detailsFragment = LocationDetailsFragment.newInstance(index);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.replace(R.id.location_details, detailsFragment);
            }
        }
        ft.commit();
    }

    public void saveListAfterAsyncTaskEnds(boolean save) {
        this.progressBarDone = save;
    }


}

package com.droid.filip.androidfragmentsandservices.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.R;

public class PromptDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public static PromptDialog newInstance() {
        PromptDialog promptDialog = new PromptDialog();
        promptDialog.setCancelable(false);
        return promptDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle("Prompt")
                .setPositiveButton("YES", this)
                .setNegativeButton("NO", this)
                .setMessage("Do you want to go back?");
        return b.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_NEGATIVE) {
            this.dismiss();
            return;
        }
        if (i == DialogInterface.BUTTON_POSITIVE) {
            MainActivity activity = (MainActivity)getActivity();
            if (activity.isMultiPane())
                activity.showDetails(0);
            else {
                LocationDetailsFragment detailsFragment = (LocationDetailsFragment)
                        activity.getSupportFragmentManager().findFragmentById(R.id.location_details);
                if (detailsFragment != null) {
                    FrameLayout cityDetailsLayout = (FrameLayout)activity
                    .findViewById(R.id.location_details);
                    cityDetailsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            0, LinearLayout.LayoutParams.MATCH_PARENT));
                    FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                    ft.remove(detailsFragment).commit();
                    ListLocationsFragment locationsFragment = (ListLocationsFragment)
                            activity.getSupportFragmentManager().findFragmentById(R.id.list_locations);
                    if (locationsFragment == null) {
                        FrameLayout listCitiesLayout = (FrameLayout)activity.findViewById(R.id.list_locations);
                        listCitiesLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        ft = activity.getSupportFragmentManager().beginTransaction();
                        locationsFragment = ListLocationsFragment.newInstance(0, activity.getTemporatyLocationResponse());
                        ft.replace(R.id.list_locations, locationsFragment).commit();
                    }

                }

            }
        }

    }
}

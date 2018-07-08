package com.droid.filip.androidfragmentsandservices.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.droid.filip.androidfragmentsandservices.MainActivity;

public class MonitoredFragment extends Fragment {

    public static String TAG_NAME = "MonitoredFragment";
    private String tag = null;
    boolean bUiReady = false;

    public MonitoredFragment() {}

    public void init(String tagName, Bundle argsBundle) {
        argsBundle.putString(TAG_NAME, tagName);
        this.setArguments(argsBundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reConstructTagNameFromArgsBundle();
    }

    private void reConstructTagNameFromArgsBundle() {
        Bundle args = getArguments();
        if (args == null) return;
        String tagName = args.getString(TAG_NAME);
        if (tagName == null) return;
        tag = tagName;
    }

    @Override
    public void onStart() {
        super.onStart();
        bUiReady = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        bUiReady = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing())
            releaseResources();
    }

    public void releaseResources() {}

    public boolean isbUiReady() {
        return bUiReady;
    }

    public boolean isActivityReady() {
        MainActivity act = (MainActivity)requireActivity();
        return act != null;
    }
}

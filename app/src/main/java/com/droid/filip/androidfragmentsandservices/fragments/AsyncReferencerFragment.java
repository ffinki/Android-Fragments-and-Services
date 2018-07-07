package com.droid.filip.androidfragmentsandservices.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.asynctasks.MyLongTaskWithProgressBar;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObject;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObjectClient;

public class AsyncReferencerFragment extends MonitoredFragment implements IWorkerObjectClient {

    private final static String tag = "AsyncReferencerFragment";
    private final static String FRAGMENT_TAG = "RetainedAsyncReferencerFragment";

    MyLongTaskWithProgressBar longTaskWithProgressBar = null;
    int tryCount = 1;

    public AsyncReferencerFragment() {}

    public void init() {
        Bundle b = new Bundle();
        super.init(tag, b);
    }

    public static AsyncReferencerFragment newInstance() {
        AsyncReferencerFragment asyncReferencerFragment = new AsyncReferencerFragment();
        asyncReferencerFragment.init();
        return asyncReferencerFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity act = (MainActivity)getActivity();
        if (longTaskWithProgressBar != null)
            longTaskWithProgressBar.onStart(act);
    }

    public void startFragmentProgressBar() {
        longTaskWithProgressBar = new MyLongTaskWithProgressBar(FRAGMENT_TAG, this);
        longTaskWithProgressBar.registerClient(this, 1);
        longTaskWithProgressBar.execute();
    }

    public static AsyncReferencerFragment createRetainedAsyncReferencerFragment(AppCompatActivity act) {
        AsyncReferencerFragment arf = AsyncReferencerFragment.newInstance();
        arf.setRetainInstance(true);
        FragmentManager fm = act.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(arf, FRAGMENT_TAG);
        ft.commit();
        return arf;
    }

    public static AsyncReferencerFragment establishRetainedAsyncReferencerFragment(AppCompatActivity act) {
        AsyncReferencerFragment frag = getRetainedAsyncReferencerFragment(act);
        if (frag == null)
            return createRetainedAsyncReferencerFragment(act);
        return frag;

    }

    public static AsyncReferencerFragment getRetainedAsyncReferencerFragment(AppCompatActivity act) {
        Fragment frag = act.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (frag == null)
            return null;
        return (AsyncReferencerFragment)frag;
    }

    public void done(IWorkerObject wobj, int workerObjectPassThroughIdentifier) {
        if (workerObjectPassThroughIdentifier == 1)
            this.longTaskWithProgressBar = null;
    }

    public void releaseResources() {
        if (longTaskWithProgressBar != null)
            longTaskWithProgressBar.releaseResources();
    }
}

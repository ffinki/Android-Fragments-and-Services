package com.droid.filip.androidfragmentsandservices.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.droid.filip.androidfragmentsandservices.asynctasks.MyLongTaskWithProgressBar;

public class HeadlessAsyncFragment extends Fragment {

    public static String FRAGMENT_NAME = "HeadlessAsyncFragment";
    MyLongTaskWithProgressBar taskReference;

    public static HeadlessAsyncFragment newInstance() {
        HeadlessAsyncFragment headlessAsyncFragment = new HeadlessAsyncFragment();
        return headlessAsyncFragment;
    }

    public static HeadlessAsyncFragment createRetainedHeadlessAsyncFragment(AppCompatActivity act) {
        HeadlessAsyncFragment headlessAsyncFragment = HeadlessAsyncFragment.newInstance();
        headlessAsyncFragment.setRetainInstance(true);
        FragmentManager fm = act.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(headlessAsyncFragment, HeadlessAsyncFragment.FRAGMENT_NAME);
        ft.commit();
        return headlessAsyncFragment;
    }
}

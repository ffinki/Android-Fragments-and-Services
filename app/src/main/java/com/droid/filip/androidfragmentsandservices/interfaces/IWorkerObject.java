package com.droid.filip.androidfragmentsandservices.interfaces;

import android.support.v7.app.AppCompatActivity;

public interface IWorkerObject {

    public void registerClient(IWorkerObjectClient woc, int workerObjectPassbackIdentifier);
    public void onStart(AppCompatActivity act);
    public void releaseResources();
}

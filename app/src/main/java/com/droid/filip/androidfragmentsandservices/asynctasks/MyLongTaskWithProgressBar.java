package com.droid.filip.androidfragmentsandservices.asynctasks;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.R;
import com.droid.filip.androidfragmentsandservices.fragments.MonitoredFragment;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObject;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObjectClient;
import com.droid.filip.androidfragmentsandservices.mocks.MockedResponse;

public class MyLongTaskWithProgressBar extends AsyncTask<String, Integer, Integer>
    implements IWorkerObject {

    public String tag = null;
    private MonitoredFragment retainedFragment;
    int currProgress = 0;
    IWorkerObjectClient client = null;
    int workerObjectPassbackIdentifier = -1;
    private boolean bDoneFlag = false;

    public MyLongTaskWithProgressBar(String tag, MonitoredFragment retainedFragment) {
        this.tag = tag;
        this.retainedFragment = retainedFragment;
    }

    @Override
    protected void onPreExecute() {
        showProgressBar();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        for (int i=0; i<=5; i++) {
            try {
                Thread.sleep(1000);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        bDoneFlag = true;
        if (retainedFragment.isbUiReady())
            closeProgressBar();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Integer i = values[0];
        if (retainedFragment.isbUiReady())
            setProgressOnProgressBar(i);
    }

    private void showProgressBar() {
        ProgressBar pb = getProgressBar();
        pb.setProgress(0);
        pb.setMax(5);
        pb.setVisibility(View.VISIBLE);
    }

    private void setProgressOnProgressBar(int i) {
        this.currProgress = i;
        ProgressBar pb = getProgressBar();
        if (pb == null) return;
        pb.setProgress(i);
    }

    private void closeProgressBar() {
        ProgressBar pb = getProgressBar();
        if (pb == null) return;
        pb.setVisibility(View.GONE);
        MainActivity callingActivity = (MainActivity)retainedFragment.requireActivity();
        callingActivity.saveListAfterAsyncTaskEnds(true);
        callingActivity.showDetails(0);
        detatchFromParent();
    }

    private ProgressBar getProgressBar() {
        MainActivity act = (MainActivity)retainedFragment.requireActivity();
        if (act == null) return null;
        return (ProgressBar)act.findViewById(R.id.progress_bar);
    }

    @Override
    public void registerClient(IWorkerObjectClient woc, int workerObjectPassbackIdentifier) {
        client = woc;
        this.workerObjectPassbackIdentifier = workerObjectPassbackIdentifier;
    }

    private void detatchFromParent() {
        if (client == null)
            return;
        client.done(this, workerObjectPassbackIdentifier);
    }

    @Override
    public void onStart(AppCompatActivity act) {
        if (bDoneFlag) {
            closeProgressBar();
            return;
        }
        setProgressBarRightOnReattach();
    }

    private void setProgressBarRightOnReattach() {
        ProgressBar pb = getProgressBar();
        pb.setMax(5);
        pb.setProgress(currProgress);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void releaseResources() {
        cancel(true);
        detatchFromParent();
    }
}

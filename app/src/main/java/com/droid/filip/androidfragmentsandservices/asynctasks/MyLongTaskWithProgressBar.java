package com.droid.filip.androidfragmentsandservices.asynctasks;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.droid.filip.androidfragmentsandservices.MainActivity;
import com.droid.filip.androidfragmentsandservices.R;
import com.droid.filip.androidfragmentsandservices.fragments.MonitoredFragment;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObject;
import com.droid.filip.androidfragmentsandservices.interfaces.IWorkerObjectClient;
import com.droid.filip.androidfragmentsandservices.mocks.MockedResponse;
import com.droid.filip.androidfragmentsandservices.singleton.GsonSingleton;
import com.droid.filip.androidfragmentsandservices.stakes.LocationsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyLongTaskWithProgressBar extends AsyncTask<String, Integer, String>
    implements IWorkerObject {

    public String tag = null;
    private MonitoredFragment retainedFragment;
    IWorkerObjectClient client = null;
    int workerObjectPassbackIdentifier = -1;
    private boolean bDoneFlag = false;
    //
    private String userAgent;
    private Gson gson;

    public MyLongTaskWithProgressBar(String tag, MonitoredFragment retainedFragment) {
        this.tag = tag;
        this.retainedFragment = retainedFragment;
        gson = GsonSingleton.getInstance();
    }

    @Override
    protected void onPreExecute() {
        showProgressBar();
        userAgent = new WebView(retainedFragment.getActivity()).getSettings().getUserAgentString();
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection http = null;
        BufferedReader br = null;
        LocationsResponse locations = null;
        try {
            URL url = new URL(
                    "http://api.geonames.org/citiesJSON?north=42.3376462&south=40.9201646&east=22.5389436&west=20.89274&lang=en&username=finki");
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("User-Agent", userAgent);
            http.setRequestProperty("Accept", "application/json");
            br = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
            locations = (LocationsResponse)gson.fromJson(br, LocationsResponse.class);
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if (http != null)
                http.disconnect();
        } finally {
            if (http != null)
                http.disconnect();
        }
        return gson.toJson(locations, new TypeToken<LocationsResponse>(){}.getType());
    }

    @Override
    protected void onPostExecute(String response) {
        bDoneFlag = true;
        if (retainedFragment.isbUiReady()) {
            MainActivity activity = (MainActivity)retainedFragment.requireActivity();
            activity.saveListAfterAsyncTaskEnds(response);
            closeProgressBar();
        }

    }


    private void showProgressBar() {
        ProgressBar pb = getProgressBar();
        pb.setVisibility(View.VISIBLE);
    }


    private void closeProgressBar() {
        ProgressBar pb = getProgressBar();
        if (pb == null) return;
        pb.setVisibility(View.GONE);
        MainActivity callingActivity = (MainActivity)retainedFragment.requireActivity();
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
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void releaseResources() {
        cancel(true);
        detatchFromParent();
    }
}

package com.droid.filip.androidfragmentsandservices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droid.filip.androidfragmentsandservices.fragments.AsyncReferencerFragment;

import static android.view.Window.FEATURE_ACTION_BAR;

public class MainActivity extends AppCompatActivity {

    AsyncReferencerFragment progressBarFragment = null;

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
        ProgressBar pb = (ProgressBar)findViewById(R.id.progress_bar);
        pb.setSaveEnabled(true);
        progressBarFragment.startFragmentProgressBar();
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
                Toast.makeText(this, "Refresh pressed", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

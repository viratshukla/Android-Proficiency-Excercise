package com.example.virat.shukla.androidproficiency.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.virat.shukla.androidproficiency.R;
import com.example.virat.shukla.androidproficiency.fragments.MainActivityFragment;

public class MainActivity extends AppCompatActivity {
    private final String FRAGMENT_TAG = MainActivityFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set MainActivityFragment
        if (null == getFragment())
            setFragment();

    }

    private void setFragment() {
        MainActivityFragment fragment = new MainActivityFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_layout, fragment, FRAGMENT_TAG).commit();
        fragmentManager.executePendingTransactions();
    }

    private MainActivityFragment getFragment() {
        return (MainActivityFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

}

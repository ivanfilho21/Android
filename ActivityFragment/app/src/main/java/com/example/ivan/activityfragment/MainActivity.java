package com.example.ivan.activityfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnInformationChanged {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Fragment[] fragments = new Fragment[2];
        fragments[0] = new Fragment1();
        fragments[1] = new Fragment2();

        // Get fragment manager from the activity
        final FragmentManager fragmentManager = getSupportFragmentManager();

        ((Spinner) findViewById(R.id.chooseFragmentSpinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Opens a transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Replaces the current fragment
                ft.replace(R.id.fragmentContent, fragments[position]);

                // Commit the changes
                ft.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
            }
        });
    }

    @Override
    public void OnInformationChanged(String information) {
        // Shows the information sent from any fragment
        ((TextView) findViewById(R.id.resultFromFragment)).setText(information);
    }
}

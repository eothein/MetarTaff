package be.hogent.jensbuysse.metartaff.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.orhanobut.logger.Logger;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.activities.adapters.AirportAdapter;
import be.hogent.jensbuysse.metartaff.fragments.AirportDialog;
import be.hogent.jensbuysse.metartaff.models.Airport;
import io.objectbox.Box;

public class MainActivity extends AppCompatActivity  implements AirportDialog.AiportDialogListener{

    private RecyclerView mRecyclerView;
    private AirportAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.airportlist);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AirportAdapter(getApplicationContext(), (MetarApplication) getApplication(),

                new AirportAdapter.CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Logger.i("clicked position:" + position);

                    }
                });
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AirportDialog dialog = new AirportDialog();
                dialog.show(getFragmentManager(), "TAG");

            }
        });
    }

    @Override
    public void onDialogPositiveClick(String ICAO, String description) {
        //Creating the box for the persistency

        MetarApplication app = (MetarApplication)getApplication();
        Box<Airport> airportBox = app.getBoxStore().boxFor(Airport.class);
        Airport airport = new Airport();
        airport.setPlaatsindicator(ICAO);
        airport.setBeschrijving(description);

        airportBox.put(airport);
        mAdapter.setAirports();
        Logger.i( "Inserted new aiport: ID: " + airport.getPlaatsindicator());
    }
}

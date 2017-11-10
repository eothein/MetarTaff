package be.hogent.jensbuysse.metartaff.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.activities.adapters.AirportAdapter;
import be.hogent.jensbuysse.metartaff.fragments.AirportDialog;
import be.hogent.jensbuysse.metartaff.models.Airport;
import be.hogent.jensbuysse.metartaff.models.Metar;
import be.hogent.jensbuysse.metartaff.network.MetarDeserializer;
import be.hogent.jensbuysse.metartaff.network.MetarInterface;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements AirportDialog.AiportDialogListener{

    public static final String METARID = "METARID" ;
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

                        final Airport airport = mAdapter.getAiport(position);

                        Logger.i("Retrieving information for airport : "+ airport.getPlaatsindicator());

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Metar.class, new MetarDeserializer());
                        Gson gson = gsonBuilder.create();

                        Retrofit  retrofit = new Retrofit.Builder().baseUrl("https://avwx.rest/api/").
                        addConverterFactory(GsonConverterFactory.create(gson)).build();

                        MetarInterface metarService = retrofit.create(MetarInterface.class);
                        Call<Metar> call = metarService.retrieveMetar(airport.getPlaatsindicator());


                        // Set up progress before call
                        final ProgressDialog progressDoalog;
                        progressDoalog = new ProgressDialog(MainActivity.this);
                        progressDoalog.setMessage("Loading most recent METAR information");
                        progressDoalog.setTitle("Loading");
                        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        // show it
                        progressDoalog.show();



                        call.enqueue(new Callback<Metar>() {
                            @Override
                            public void onResponse(Call<Metar> call, Response<Metar> response) {
                                Logger.i("GOT a succesfull response");
                                Logger.i(response.body().getRawMetar());
                                progressDoalog.dismiss();
                                long metarId = saveMetar(response.body(), airport);
                                //TODO: save metar information, and send ID using the intent
                                Intent detailIntent = new Intent(getApplicationContext(),MetarDetailActivity.class);
                                detailIntent.putExtra(METARID,metarId);
                                startActivity(detailIntent);
                            }

                            @Override
                            public void onFailure(Call<Metar> call, Throwable t) {
                                Logger.i("The onfailure is called ");
                                t.printStackTrace();
                                progressDoalog.dismiss();
                            }
                        });



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

    private long saveMetar(Metar metar, Airport airport){
        MetarApplication app = (MetarApplication)getApplication();
        Box<Metar> metarBox = app.getBoxStore().boxFor(Metar.class);
        metar.airport.setTarget(airport);
        Logger.i("Added a new metar + "+metar.getRawMetar());
        return metarBox.put(metar);

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

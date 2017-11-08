package be.hogent.jensbuysse.metartaff;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import be.hogent.jensbuysse.metartaff.models.Airport;

import be.hogent.jensbuysse.metartaff.models.MyObjectBox;
import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by eothein on 07.11.17.
 */

public class MetarApplication extends Application {



    private BoxStore boxStore;

    /**
     * We need to initialize the database just once. MyObjectBox: Generated based on the entity classes,
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("Started application");
        Logger.addLogAdapter(new AndroidLogAdapter());

        boxStore = MyObjectBox.builder().androidContext(MetarApplication.this).build();

        //We need to check whether the database already has airports added. If not we need to add them
        Box<Airport> airportBox = boxStore.boxFor(Airport.class);
        long count = airportBox.count();
        if(count == 0){
            //We need to initialze the datbase with some standard airports
            String[] airportsNames = {"EBAK", "EBAW", "EBOS", "EBBR", "EBCI", "EBKT", "EBLG"};
            String [] airportsbeschrijvingen = {"Antwerp/Kiel Heliport", "Antwerp International Airport (Brussels Airport)", "Ostend–Bruges International Airport",
            "Brussels Airport", "Brussels South Charleroi Airport", "Flanders International Airport", "Liège Airport"};
            List<Airport> airports = new ArrayList<>();
            for(int i = 0; i< airportsNames.length; i++){
                Airport airport = new Airport();
                airport.setPlaatsindicator(airportsNames[i]);
                airport.setBeschrijving(airportsbeschrijvingen[i]);
                airports.add(airport);
                Logger.d("Added an Airport");
            }
            airportBox.put(airports);
        }
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}

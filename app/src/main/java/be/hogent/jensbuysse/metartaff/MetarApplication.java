package be.hogent.jensbuysse.metartaff;

import android.app.Application;

import be.hogent.jensbuysse.metartaff.models.MyObjectBox;
import io.objectbox.BoxStore;

/**
 * Created by eothein on 07.11.17.
 */

public class MetarApplication extends Application {


    /**
     * We need to initialize the database just once. MyObjectBox: Generated based on the entity classes,
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        BoxStore boxStore = MyObjectBox.builder().androidContext(this).build();
    }

   
}

package be.hogent.jensbuysse.metartaff.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Airport;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;

/**
 * Created by eothein on 07.11.17.
 */

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> implements DataObserver<List<Airport>>{


    List<Airport> airports ;
    private Context context;

    @Override
    public void onData(List<Airport> data) {
        airports = data;
        notifyDataSetChanged();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView name;
        public TextView description;
        public ImageView thumbNail;



        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            thumbNail = (ImageView)itemView.findViewById(R.id.thumbnail);
        }
    }


    public AirportAdapter(Context context, MetarApplication app) {
        this.context = context;
        Box<Airport> airportBox = app.getBoxStore().boxFor(Airport.class);
        Query<Airport> airportQuery = airportBox.query().build();
        airportQuery.subscribe().observer(this);
        airportQuery.find();

    }


    @Override
    public AirportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout,parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Airport airport = airports.get(position);
        holder.name.setText(airport.getPlaatsindicator());
        holder.description.setText(airport.getBeschrijving());
        Picasso.with(context).load(R.mipmap.airport).into(holder.thumbNail);

    }


    @Override
    public int getItemCount() {
        return airports.size();
    }


}

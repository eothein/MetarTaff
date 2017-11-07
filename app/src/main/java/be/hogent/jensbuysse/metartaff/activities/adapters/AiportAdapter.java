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

import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Aiport;

/**
 * Created by eothein on 07.11.17.
 */

public class AiportAdapter extends RecyclerView.Adapter<AiportAdapter.ViewHolder> {


    List<Aiport> airports ;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

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


    public AiportAdapter(Context context) {
        this.context = context;
        airports = new ArrayList<>();
        //Stub code
        String[] airportsNames = {"EBOS", "EBBR", "EBAK"};
        String [] airportsbeschrijvingen = {"Oostende", "Brussel (Brussels Airport)", "Antwerp/Kiel Heliport"};

        for(int i = 0; i< airportsNames.length; i++){
            Aiport airport = new Aiport();
            airport.setPlaatsindicator(airportsNames[i]);
            airport.setBeschrijving(airportsbeschrijvingen[i]);
            airports.add(airport);
        }

    }


    @Override
    public AiportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout,parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Aiport airport = airports.get(position);
        holder.name.setText(airport.getPlaatsindicator());
        holder.description.setText(airport.getBeschrijving());
        Picasso.with(context).load(R.mipmap.airport).into(holder.thumbNail);

    }


    @Override
    public int getItemCount() {
        return airports.size();
    }
}

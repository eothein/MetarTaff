package be.hogent.jensbuysse.metartaff.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Metar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetailFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailFragment extends Fragment {


    private Metar metar;

    private OnDetailFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    public void setMetar(Metar metar) {
        this.metar = metar;
        updateUI();
    }

    public void updateUI(){
        if(metar != null){
            TextView v = this.getActivity().findViewById(R.id.sight);
            v.setText(String.format("%d",metar.getSight()));
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
       // if(metar != null){
       //     updateUI();
       // }
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mListener = (OnDetailFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOldMetarInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDetailFragmentInteraction(Uri uri);
    }
}

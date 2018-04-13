package com.example.anthony_pc.pocketrecipe.fragments.inicio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inicio_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Inicio_Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<Item_Inicio> List = new ArrayList<>();
    String[] title = {"RECETAS SALUDABLES","RECETAS COMIDA RÁPIDA","RECETAS DULCES", "RECETAS OCACIONES ESPECIALES"};
    int[] images = {R.drawable.vegetales,R.drawable.comida_rapida,R.drawable.postres,R.drawable.carne};
    ArrayList Imagenes = new ArrayList<>();

    InicioAdapter adapter;
    ListView grid;

    public Inicio_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView text = (TextView) getView().findViewById(R.id.text);
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        grid = (ListView) view.findViewById(R.id.list);


        for(int i = 0;i<title.length;i++){

            List.add(new Item_Inicio(title[i],(Drawable)getResources().getDrawable(images[i])));
        }

        adapter = new InicioAdapter(getContext(),R.layout.list_view_items_inicio,List);
        grid.setAdapter(adapter);
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}

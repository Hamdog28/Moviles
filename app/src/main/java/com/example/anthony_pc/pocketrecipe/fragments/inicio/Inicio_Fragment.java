package com.example.anthony_pc.pocketrecipe.fragments.inicio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.fragments.fav.FavoritosFragment;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inicio_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Inicio_Fragment extends Fragment implements FavoritosFragment.OnFragmentInteractionListener{

    private OnFragmentInteractionListener mListener;
    ArrayList<Item_Inicio> List = new ArrayList<>();
    String[] title = {"RECETAS SALUDABLES","RECETAS COMIDA RÁPIDA","RECETAS DULCES", "TODAS LAS RECETAS"};
    String[] category = {"saludable","comida_rapida","dulce", "todas"};
    int[] images = {R.drawable.vegetales,R.drawable.comida_rapida,R.drawable.postres,R.drawable.carne};
    ArrayList Imagenes = new ArrayList<>();

    InicioAdapter adapter;
    ListView list;


    public Inicio_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView text = (TextView) getView().findViewById(R.id.text);
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        list = view.findViewById(R.id.list);


        for(int i = 0;i<title.length;i++){

            List.add(new Item_Inicio(title[i],(Drawable)getResources().getDrawable(images[i]),category[i]));
        }




        adapter = new InicioAdapter(getContext(),R.layout.list_view_items_inicio,List,list);
        list.setAdapter(adapter);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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

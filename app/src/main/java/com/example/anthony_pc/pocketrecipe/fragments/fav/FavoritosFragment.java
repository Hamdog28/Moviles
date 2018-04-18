package com.example.anthony_pc.pocketrecipe.fragments.fav;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.anthony_pc.pocketrecipe.Activites.InicioActivity;
import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FavoritosFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<Item> List = new ArrayList<>();
    String[] title = {"Vegetales","Hamburguesa","Helado", "Carne"};
    int[] images = {R.drawable.vegetales,R.drawable.comida_rapida,R.drawable.postres,R.drawable.carne};
    int[] stars = {3,4,1,2};
    int[] id = {3,4,1,2};
    ArrayList Imagenes = new ArrayList<>();

    Adapter adapter;
    GridView grid;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        String mensaje = getArguments().getString("mensaje");
        String orientacion = getArguments().getString("orientacion");
        Log.i("mensaje",mensaje);

        grid = (GridView) view.findViewById(R.id.grid);


        for(int i = 0;i<title.length;i++){

            List.add(new Item(title[i],(Drawable)getResources().getDrawable(images[i]),stars[i],id[i]));
        }

        adapter = new Adapter(getContext(),R.layout.grid_view_items,List);

        grid.setAdapter(adapter);
        if(orientacion.equals("list")){
            //list.setAdapter(adapter);
            grid.setNumColumns(1);
            setGridViewHeightBasedOnChildren( grid,1);
        }
        else{

            setGridViewHeightBasedOnChildren( grid,2);
        }

        //setGridViewHeightBasedOnChildren( grid,2);
        return view;
    }

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > columns ){
            x = items/columns;
            rows = (int) (x + 1);
            totalHeight *= rows-1;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

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

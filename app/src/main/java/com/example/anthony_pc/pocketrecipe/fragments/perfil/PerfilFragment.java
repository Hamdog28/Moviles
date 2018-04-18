package com.example.anthony_pc.pocketrecipe.fragments.perfil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.Activites.EditarPerfilActivity;
import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PerfilFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Fragment fragment;
    private PopupMenu mPopupMenu;

    public PerfilFragment() {
        // Required empty public constructor
    }

    CircleImageView profileImage;
    TextView nombreTV,publicacioneTV,descripcionTV;




    private Globals instance= Globals.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        setHasOptionsMenu(true);
        ImageButton imageButton = v.findViewById(R.id.settings_button);
        mPopupMenu = new PopupMenu(getContext(), imageButton);

        profileImage = v.findViewById(R.id.profile_image);
        nombreTV = v.findViewById(R.id.nombre);
        publicacioneTV = v.findViewById(R.id.publicacionesTV);
        descripcionTV = v.findViewById(R.id.descripcionTV);

        profileImage.setImageBitmap(instance.getActualUser().getFoto());
        nombreTV.setText(instance.getActualUser().getNombre());

        descripcionTV.setText(instance.getActualUser().getDescripcion());




        MenuInflater menuInflater = mPopupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.perfil_settings, mPopupMenu.getMenu());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupMenu.show();

                }
        });
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                Log.i("hola","hola");
                //noinspection SimplifiableIfStatement
                if (id == R.id.editar) {
                    Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


        return v;
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

    public void verSeguidores(View view){}
    public void verSeguidos(View view){}



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

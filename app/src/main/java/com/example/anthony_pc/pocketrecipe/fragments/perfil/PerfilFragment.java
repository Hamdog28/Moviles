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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

    Button seguir;
    boolean followed = false;
    boolean my_profile = true;


    private Globals instance= Globals.getInstance();

    View v;

    int id = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_perfil, container, false);
        setHasOptionsMenu(true);

        try{
            id = Integer.parseInt(getArguments().getString("mensaje"));
        }catch (NullPointerException e){
            Toast.makeText(getContext(),String.valueOf(id) +  "  Error",Toast.LENGTH_SHORT).show();
        }



        ImageButton imageButton = v.findViewById(R.id.settings_button);
        mPopupMenu = new PopupMenu(getContext(), imageButton);

        profileImage = v.findViewById(R.id.profile_image);
        nombreTV = v.findViewById(R.id.nombre);
        publicacioneTV = v.findViewById(R.id.publicacionesTV);
        descripcionTV = v.findViewById(R.id.descripcionTV);
        seguir = v.findViewById(R.id.seguir);
        seguir.setVisibility(View.GONE);
        imageButton.setVisibility(View.GONE);

        Log.e("onCREATE", String.valueOf(id));
        profileImage.setImageBitmap(instance.getUser(id).getFoto());
        nombreTV.setText(instance.getUser(id).getNombre());

        descripcionTV.setText(instance.getUser(id).getDescripcion());


        if (id == instance.getActualUser().getId()){
            imageButton.setVisibility(View.VISIBLE);
        }else{
            seguir.setVisibility(View.VISIBLE);
        }

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


    @Override
    public void onResume() {
        super.onResume();
        

       //id = Integer.parseInt(getArguments().getString("mensaje"));
        Log.e("onRESUME", String.valueOf(id));
        profileImage.setImageBitmap(instance.getUser(id).getFoto());
        nombreTV.setText(instance.getUser(id).getNombre());
        descripcionTV.setText(instance.getUser(id).getDescripcion());

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

    public void Follow(View view){

        if(!followed) {
            seguir.setText(" Dejar de seguir ");

            followed = true;
        }
        else {
            seguir.setText("Seguir");

            followed = false;
        }

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

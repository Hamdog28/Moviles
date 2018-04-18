package com.example.anthony_pc.pocketrecipe.Activites;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anthony_pc.pocketrecipe.Favoritos;
import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetaActivity extends AppCompatActivity {

    boolean fav = false;
    float total_rating = 18;
    int total_calificaciones = 5;
    float score; // calificacion dada por el susuario
    boolean calificado = false;
    RatingBar rating;

    private Globals instance= Globals.getInstance();
    String url = "https://moviles-backoffice.herokuapp.com/favorito/";



    boolean followed = false;

    String[] ingredientes = {"carne","aceite","carbon","mantequilla","barbacoa"};
    String[] pasos = {"paso1","paso2","paso3","paso4","paso5"};
    String[] tags = {"carne","familia","eventos_especiales","parrilla","rico"};
    String texto_notas = "Perfecto para fines de semana";

    LinearLayout lay_ingrediente, lay_pasos, lay_tags;
    List<String> carrito = new ArrayList<String>(Collections.nCopies(ingredientes.length, ""));

    String recetaActual = "-1";
    String deleteStringID = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Carne");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        recetaActual = intent.getStringExtra("id");
        deleteStringID = String.valueOf(instance.returnDeleteID(Integer.valueOf(recetaActual)));

        Log.e("id",String.valueOf(instance.returnDeleteID(3)));



        lay_ingrediente = (LinearLayout) findViewById(R.id.ingredientes_layout);
        lay_pasos = (LinearLayout) findViewById(R.id.pasos_layout);
        lay_tags = (LinearLayout) findViewById(R.id.tags_layout);

        TextView notas = (TextView)findViewById(R.id.tv_notas);
        notas.setText(texto_notas);

        rating = (RatingBar)findViewById(R.id.rating);
        rating.setRating(total_rating/total_calificaciones);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView foto = (ImageView)findViewById(R.id.foto);
        ImageView imagen = (ImageView)findViewById(R.id.imagen);

        foto.setImageDrawable(getResources().getDrawable(R.drawable.foto_perfil));
        imagen.setImageDrawable(getResources().getDrawable(R.drawable.carne));



        populateIngredients();
        populateProcedure();
        populateTags();
    }

    public boolean Atras(View view){
        finish();
        return true;
    }

    private void populateIngredients() {

        for(int i = 0; i<ingredientes.length;i++){
            final TextView ingrediente = new TextView(this);
            ingrediente.setText("   " + ingredientes[i]);
            ingrediente.setTextColor(Color.parseColor("#000000"));
            ingrediente.setId(i);
            ingrediente.setCompoundDrawablesWithIntrinsicBounds( R.drawable.shape_add_ingrediente, 0, 0, 0);
            lay_ingrediente.addView(ingrediente);
            ingrediente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(carrito.get(ingrediente.getId()) != "") {
                        ingrediente.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_remove_ingrediente, 0, 0, 0);
                        carrito.set(ingrediente.getId(),"");
                    }
                    else {
                        ingrediente.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_add_ingrediente, 0, 0, 0);
                        carrito.set(ingrediente.getId(),(String)ingrediente.getText());
                    }
                }
            });
        }
    }

    public void insertarFavorito(){

        StringRequest favoritoRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("persona",String.valueOf(instance.getActualUser().getId()));
                params.put("receta",recetaActual);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(favoritoRequest);

    }

    public void eliminarFavorito(){

        StringRequest eliminarFavorito = new StringRequest(Request.Method.DELETE, url+deleteStringID+"/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(eliminarFavorito);

    }



    private void populateProcedure() {

        for(int i = 0; i<pasos.length;i++){
            LinearLayout lay_paso = new LinearLayout(this);
            lay_paso.setOrientation(LinearLayout.HORIZONTAL);
            lay_paso.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            lay_paso.setDividerDrawable(getResources().getDrawable(R.drawable.empty_width_divider));

            TextView paso = new TextView(this);
            TextView icon = new TextView(this);

            icon.setText(Integer.toString(i+1));
            icon.setTextSize(20);
            icon.setGravity(Gravity.CENTER);
            icon.setTextColor(getResources().getColor(R.color.colorPrimary));
            icon.setTypeface(null, Typeface.BOLD);
            lay_paso.addView(icon);


            paso.setText(pasos[i]);
            paso.setTextColor(Color.parseColor("#000000"));
            paso.setId(i);
            lay_paso.addView(paso);

            icon.setBackground(getResources().getDrawable(R.drawable.shape_circulo));


            lay_pasos.addView(lay_paso);

        }
    }

    private void populateTags(){
        for(int i = 0; i<tags.length;i+=3){
            LinearLayout lay_tag = new LinearLayout(this);
            lay_tag.setOrientation(LinearLayout.HORIZONTAL);
            lay_tag.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            lay_tag.setDividerDrawable(getResources().getDrawable(R.drawable.empty_width_divider));

            for(int j = 0; j<3; j++){
                if(i+j == tags.length)
                    break;
                TextView tag = new TextView(this);

                tag.setText("  #" + tags[i+j] + "  ");
                tag.setBackground(getResources().getDrawable(R.drawable.button_shape_tag));
                tag.setTextColor(getResources().getColor(R.color.colorPrimaryLight));

                lay_tag.addView(tag);


            }
            lay_tags.addView(lay_tag);





        }

    }

    public void Follow(View view){
        Button seguir = (Button) findViewById(R.id.seguir);
        if(!followed) {
            seguir.setText(" Dejar de seguir ");

            followed = true;
        }
        else {
            seguir.setText("Seguir");

            followed = false;
        }

    }

    public void Like(View view){
        RatingBar calificacion = (RatingBar) findViewById(R.id.calificar);
        calificacion.getRating();

        Snackbar.make(view, "Gracias por su calificacion", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        if(!calificado){
            total_calificaciones++;
            calificado = true;
        }
        total_rating-=score;
        score = calificacion.getRating();
        total_rating += score;
        rating.setRating((total_rating/total_calificaciones));
    }


    public void Favorito(View view){
        ImageButton favorito = (ImageButton) findViewById(R.id.favorito);
        if(!fav) {
            favorito.setBackgroundResource(R.drawable.liked);
            insertarFavorito();
            Favoritos favoritoObject = new Favoritos(instance.returnLastIDFav(),Integer.valueOf(recetaActual),instance.getActualUser().getId());
            instance.addFavoritos(favoritoObject);
            fav = true;
            Snackbar.make(view, "Receta ha sido agregada a favoritos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else {
            favorito.setBackgroundResource(R.drawable.like);
            eliminarFavorito();
            instance.deleteFavorito(Integer.valueOf(recetaActual));
            fav = false;
        }

    }

}

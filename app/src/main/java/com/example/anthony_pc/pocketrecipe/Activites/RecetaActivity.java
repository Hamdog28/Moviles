package com.example.anthony_pc.pocketrecipe.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.anthony_pc.pocketrecipe.Receta;
import com.example.anthony_pc.pocketrecipe.Seguidores;
import com.example.anthony_pc.pocketrecipe.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetaActivity extends AppCompatActivity {

    boolean fav = false;
    float total_rating = 1;
    int total_calificaciones =1;
    float score; // calificacion dada por el susuario
    boolean calificado = false;
    RatingBar rating;

    private Globals instance= Globals.getInstance();
    String url = "https://moviles-backoffice.herokuapp.com/favorito/";
    String urlSeguidor = "https://moviles-backoffice.herokuapp.com/seguidor/";

    Button seguir;
    boolean followed = false;

    ArrayList<String> ingredientes = new ArrayList<>();
    ArrayList<String> pasos = new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>();
    ArrayList<String> texto_notas = new ArrayList<>();

    TextView nombre_autor;
    LinearLayout lay_ingrediente, lay_pasos, lay_tags;

    Bundle bundle = new Bundle();
    String correo_autor;

    List<String> carrito = new ArrayList<String>(Collections.nCopies(ingredientes.size(), ""));


    String recetaActual = "-1";
    String deleteStringID = "-1";

    Receta receta;

    boolean editable = false;

    Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        ImageButton favorito = (ImageButton) findViewById(R.id.favorito);
        recetaActual = intent.getStringExtra("id");
        Log.e("RECETA ID", recetaActual);
        receta = null;
        receta = instance.getReceta(Integer.valueOf(recetaActual));


        user = instance.getUser(receta.getAutor());


        correo_autor = user.getCorreo();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Correo_Activity.class);
                intent.putExtra("mensaje",correo_autor);
                startActivity(intent);
            }
        });


        if(instance.checkFav(receta.getId())){
            favorito.setBackgroundResource(R.drawable.liked);
            fav = true;

        }

        if(user.getId() == instance.getActualUser().getId()){
            editable = true;
            fab.setVisibility(View.GONE);
        }

        setTitle("Receta");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deleteStringID = String.valueOf(instance.returnDeleteID(Integer.valueOf(recetaActual)));

        Log.e("id",String.valueOf(instance.returnDeleteID(3)));


        seguir = (Button) findViewById(R.id.seguir);

        ingredientes = receta.getListaIngredientes();
        pasos = receta.getProcedimiento();
        tags = receta.getListaTags();

        lay_ingrediente = (LinearLayout) findViewById(R.id.ingredientes_layout);
        lay_pasos = (LinearLayout) findViewById(R.id.pasos_layout);
        lay_tags = (LinearLayout) findViewById(R.id.tags_layout);

        TextView notas = (TextView)findViewById(R.id.tv_notas);
        notas.setText(receta.getNotas());

        TextView nombre = (TextView)findViewById(R.id.nombre);
        nombre.setText(receta.getNombre());

        rating = (RatingBar)findViewById(R.id.rating);
        rating.setRating(receta.getCantCalificaciones()/receta.getCalificacion());




        final RatingBar calificacion = (RatingBar) findViewById(R.id.calificar);


        calificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
                        RatingBar rating_aux = (RatingBar)findViewById(R.id.rating);

                        if(!calificado){
                            total_calificaciones++;
                            calificado = true;
                        }
                        total_rating-=score;
                        score = calificacion.getRating();
                        calificacion.setRating(score);
                        total_rating += score;
                        Snackbar.make(getWindow().getDecorView().getRootView(), "Gracias por su calificacion", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        rating_aux.setRating((total_rating/total_calificaciones));

                }
        });

        ImageView foto = (ImageView)findViewById(R.id.foto);
        ImageView imagen = (ImageView)findViewById(R.id.foto);

        TextView autorNombre = (TextView)findViewById(R.id.autor);
        autorNombre.setText(user.getNombre());

        foto.setImageBitmap(user.getFoto());
        imagen.setImageBitmap(receta.getFoto());

        TextView publicacionDate = (TextView)findViewById(R.id.publicacionDate);
        String[] publicacion = receta.getPublicacion().split("T");
        publicacionDate.setText(publicacion[0]);

        TextView porcionesTV = (TextView)findViewById(R.id.porciones);
        //Log.e("porciones", receta.getPorciones());
        porcionesTV.setText(String.valueOf(receta.getPorciones()+" " + "porciones"));


        nombre_autor = (TextView)findViewById(R.id.autor);
        nombre_autor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
                Log.e("mensaje receta activity", String.valueOf(user.getId()));
                intent.putExtra("mensaje",String.valueOf(user.getId()));
                intent.putExtra("pantalla","perfil");
                startActivity(intent);

            }
        });


        TextView duracionTV = (TextView)findViewById(R.id.duracion);
        duracionTV.setText(String.valueOf(receta.getDuracion()+" " + "horas"));

        TextView dificultadTV = (TextView)findViewById(R.id.dificultad);
        dificultadTV.setText(String.valueOf(receta.getDificultad()));

        TextView costoTV = (TextView)findViewById(R.id.costo);
        costoTV.setText(String.valueOf(receta.getCosto()));


        populateIngredients();
        populateProcedure();
        populateTags();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(fav) {

            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (editable){
            getMenuInflater().inflate(R.menu.menu_receta, menu);
            seguir.setVisibility(View.GONE);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.editar) {
            Intent intent = new Intent(this, CURecetaActivity.class);
            intent.putExtra("mensaje","editar");
            startActivity(intent);


            return true;
        }
        return false;
    }

    private void populateIngredients() {

        for(int i = 0; i<ingredientes.size();i++){
            final TextView ingrediente = new TextView(this);
            ingrediente.setText("   " + ingredientes.get(i));
            ingrediente.setTextColor(Color.parseColor("#000000"));
            ingrediente.setId(i);
            ingrediente.setCompoundDrawablesWithIntrinsicBounds( R.drawable.shape_add_ingrediente, 0, 0, 0);
            lay_ingrediente.addView(ingrediente);
            ingrediente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(carrito.get(ingrediente.getId()) != "") {
                        ingrediente.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_add_ingrediente, 0, 0, 0);
                        carrito.set(ingrediente.getId(),"");
                    }
                    else {
                        ingrediente.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_remove_ingrediente, 0, 0, 0);
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
                Log.e("PERSONA FAV ID",String.valueOf(instance.getActualUser().getId()));
                Log.e("RECETA FAV ID",String.valueOf(recetaActual));
                params.put("persona",String.valueOf(instance.getActualUser().getId()));
                params.put("receta",recetaActual);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(favoritoRequest);

    }

    public void insertarSeguidor(){

        StringRequest insertarSeguidor = new StringRequest(Request.Method.POST, urlSeguidor, new Response.Listener<String>() {
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
                params.put("seguidor",String.valueOf(instance.getActualUser().getId()));
                params.put("seguido",String.valueOf(user.getId()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(insertarSeguidor);

    }

    public void eliminarFavorito(String url){

        StringRequest eliminarFavorito = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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

    public void eliminarSeguidor(String url){

        StringRequest eliminarSeguidor = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
        requestQueue.add(eliminarSeguidor);
    }


    private void populateProcedure() {

        for(int i = 0; i<pasos.size();i++){
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


            paso.setText(pasos.get(i));
            paso.setTextColor(Color.parseColor("#000000"));
            paso.setId(i);
            lay_paso.addView(paso);

            icon.setBackground(getResources().getDrawable(R.drawable.shape_circulo));


            lay_pasos.addView(lay_paso);

        }
    }

    private void populateTags(){
        for(int i = 0; i<tags.size();i+=3){
            LinearLayout lay_tag = new LinearLayout(this);
            lay_tag.setOrientation(LinearLayout.HORIZONTAL);
            lay_tag.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            lay_tag.setDividerDrawable(getResources().getDrawable(R.drawable.empty_width_divider));

            for(int j = 0; j<3; j++){
                if(i+j == tags.size())
                    break;
                TextView tag = new TextView(this);

                tag.setText("  #" + tags.get(i+j) + "  ");
                tag.setBackground(getResources().getDrawable(R.drawable.button_shape_tag));
                tag.setTextColor(getResources().getColor(R.color.colorPrimaryLight));

                lay_tag.addView(tag);


            }
            lay_tags.addView(lay_tag);

        }

    }

    public void Follow(View view){

        if(!followed) {
            seguir.setText(" Dejar de seguir ");
            insertarSeguidor();
            Seguidores seguidores = new Seguidores(instance.getFollowId()+1,instance.getActualUser().getId(),user.getId());
            instance.addSeguidor(seguidores);
            followed = true;
        }
        else {
            seguir.setText("Seguir");
            eliminarSeguidor(urlSeguidor+String.valueOf(instance.returnDeleteIdSeguidor(user.getId()))+"/");
            instance.deleteSeguidor(user.getId());
            followed = false;
        }
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
            eliminarFavorito(url+String.valueOf(instance.returnLastIDFav())+"/");
            instance.deleteFavorito(Integer.valueOf(recetaActual));
            fav = false;
        }

    }

}

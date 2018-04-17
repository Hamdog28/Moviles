package com.example.anthony_pc.pocketrecipe.Activites;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecetaActivity extends AppCompatActivity {





    boolean fav = false;
    float total_rating = 18;
    int total_calificaciones = 5;
    float score; // calificacion dada por el susuario
    boolean calificado = false;
    RatingBar rating;


    boolean followed = false;

    String[] ingredientes = {"carne","aceite","carbon","mantequilla","barbacoa"};
    String[] pasos = {"paso1","paso2","paso3","paso4","paso5"};
    String[] tags = {"carne","familia","eventos_especiales","parrilla","rico"};
    String texto_notas = "Perfecto para fines de semana";

    LinearLayout lay_ingrediente, lay_pasos, lay_tags;
    List<String> carrito = new ArrayList<String>(Collections.nCopies(ingredientes.length, ""));;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Carne");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton favorito = (ImageButton) findViewById(R.id.favorito);


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

        //populateIngredients();
        //populateProcedure();
        populateTags();
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

            fav = true;

            Snackbar.make(view, "Receta ha sido agregada a favoritos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
        else {
            favorito.setBackgroundResource(R.drawable.like);

            fav = false;
        }

    }

}

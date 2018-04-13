package com.example.anthony_pc.pocketrecipe.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;
import java.util.List;

public class CURecetaActivity extends AppCompatActivity {

    private String m_Text = "";
    List<String> ingredientes = new ArrayList<String>();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cureceta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Crear Receta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView agregar =(TextView)  findViewById(R.id.agregarIngrediente_button);
        agregar.setTypeface(font);

    }

    public void agarrarDatos(){

    }
    public void showstatesList() {

        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        View linearLayout =  findViewById(R.id.lista);
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();

        for(int i = 0; i<ingredientes.size(); i++){
            final TextView valueTV = new TextView(this);
            valueTV.setTextColor(getResources().getColor(R.color.black));
            //valueTV.setTextColor(Color.parseColor("#000000"));
            valueTV.setTextSize(18);
            valueTV.setId(((LinearLayout) linearLayout).getChildCount());
            params.setMargins(0,25,0,0);
            valueTV.setText("  " + ingredientes.get(i));
            valueTV.setBackgroundResource(R.color.colorPrimaryLight);
            //valueTV.setAlpha((float)0.2);
            valueTV.getBackground().setAlpha(80);
            valueTV.isClickable();

            valueTV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("¿Desea eliminar el ingrediente?");


                    // Set up the buttons
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ingredientes.remove(valueTV.getId());
                            showstatesList();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
            valueTV.setLayoutParams(params);
            ((LinearLayout) linearLayout).addView(valueTV);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void agregarIngrediente(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir ingrediente");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( ! (input.getText().toString().equals(""))) {
                    //go on here and dismiss dialog
                    m_Text = input.getText().toString();
                    ingredientes.add(m_Text);
                    showstatesList();
                }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }




}

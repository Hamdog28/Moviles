package com.example.anthony_pc.pocketrecipe;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        TextView titulo = findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView atras = findViewById(R.id.atras);
        atras.setTypeface(font);

    }
}

package com.example.anthony_pc.pocketrecipe;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegistroActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        String name =  getIntent().getExtras().getString("first_name")+" "+getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");
        editTextName.setText(name);
        editTextEmail.setText(email);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView atras =(TextView)  findViewById(R.id.atras);
        atras.setTypeface(font);

    }

    public void registerClick(View view){
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
    }
}

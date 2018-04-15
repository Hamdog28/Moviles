package com.example.anthony_pc.pocketrecipe.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anthony_pc.pocketrecipe.R;

public class EditarPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Editar Perfil");
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}

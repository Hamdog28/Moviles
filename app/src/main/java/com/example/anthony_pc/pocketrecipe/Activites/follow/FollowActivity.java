package com.example.anthony_pc.pocketrecipe.Activites.follow;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.Usuario;
import com.example.anthony_pc.pocketrecipe.fragments.inicio.InicioAdapter;
import com.example.anthony_pc.pocketrecipe.fragments.inicio.Item_Inicio;

import java.util.ArrayList;



public class FollowActivity extends AppCompatActivity {

    ArrayList<Follow_Item> List = new ArrayList<>();
    String[] title = {"RECETAS SALUDABLES","RECETAS COMIDA RÁPIDAaaaaaaaa","RECETAS DULCES", "TODAS LAS RECETAS"};
    int[] ids = {1,3,4, 6};
    boolean[] follow = {true,false,false, true};
    int[] images = {R.drawable.vegetales,R.drawable.comida_rapida,R.drawable.postres,R.drawable.carne};
    ArrayList Imagenes = new ArrayList<>();
    private Globals instance= Globals.getInstance();
    Adapter_Follow adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setTitle(getIntent().getStringExtra("titulo"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = (ListView) findViewById(R.id.list);


        Usuario user = instance.getActualUser();


        for(int i = 0;i<title.length;i++){

            List.add(new Follow_Item(title[i],user.getFoto(),  ids[i],follow[i]));
        }

        adapter = new Adapter_Follow(getApplicationContext(),R.layout.list_view_items_follow,List,list);


        list.setAdapter(adapter);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void Follow (View view){
        Button seguir = (Button) view;
        String  a = (String)seguir.getText();
        Log.i("jeje",a);
        if (a.equals("Seguir")) {
            seguir.setText(" Dejar de seguir ");

        } else {
            seguir.setText("Seguir");

        }

    }

}

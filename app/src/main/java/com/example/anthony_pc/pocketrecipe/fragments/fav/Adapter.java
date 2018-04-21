package com.example.anthony_pc.pocketrecipe.fragments.fav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.Activites.RecetaActivity;
import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {

    private ArrayList<Item> List = new ArrayList<>();
    private Context context;
    private int posicion;
    private GridView gridView;

    public Adapter(Context context, int textViewResourceId, ArrayList<Item> objects, GridView gridView) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.List = objects;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        System.out.println("cuenta"+ super.getCount());
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.grid_view_items, null);

        TextView textView = v.findViewById(R.id.titulo);
        ImageView imageView = v.findViewById(R.id.foto);
        RatingBar rating = v.findViewById(R.id.rating);

        posicion = position;
        //Log.e("ADAPTER",List.get(position).getName());
        //Log.e("nombreeee",String.valueOf(List.get(position).getName()));
        try{
            textView.setText(List.get(position).getName());
            imageView.setImageBitmap(List.get(position).getImage());
            rating.setRating(List.get(position).getStars());
        }
        catch(IndexOutOfBoundsException e){}


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("ID RECETA", String.valueOf(List.get(i).getId()));
                Intent intent = new Intent();
                intent.setClass(context, RecetaActivity.class);
                intent.putExtra("id", String.valueOf(List.get(i).getId())); //Optional parameters/
                context.startActivity(intent);
            }
        });

        return v;

    }


}
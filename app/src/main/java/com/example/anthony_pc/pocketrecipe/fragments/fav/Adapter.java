package com.example.anthony_pc.pocketrecipe.fragments.fav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.fragments.inicio.Item_Inicio;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {

    ArrayList<Item> List = new ArrayList<>();
    Context context;

    public Adapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        List = objects;
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
        ImageView imageView = v.findViewById(R.id.imagen);
        RatingBar rating = v.findViewById(R.id.rating);



        textView.setText(List.get(position).getName());
        imageView.setImageDrawable(List.get(position).getImage());
        rating.setRating(List.get(position).getStars());


        return v;

    }


}
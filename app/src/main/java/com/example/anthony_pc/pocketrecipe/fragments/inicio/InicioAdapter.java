package com.example.anthony_pc.pocketrecipe.fragments.inicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;

public class InicioAdapter extends ArrayAdapter {

    ArrayList<Item_Inicio> List = new ArrayList<>();
    Context context;

    public InicioAdapter(Context context, int textViewResourceId, ArrayList<Item_Inicio> objects) {
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
        v = inflater.inflate(R.layout.list_view_items_inicio, null);

        TextView textView = v.findViewById(R.id.titulo);
        ImageView imageView = v.findViewById(R.id.imagen);


        textView.setText(List.get(position).getName());
        imageView.setImageDrawable(List.get(position).getImage());


        return v;

    }

}
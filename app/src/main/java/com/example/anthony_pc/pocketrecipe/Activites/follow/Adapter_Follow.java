package com.example.anthony_pc.pocketrecipe.Activites.follow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony_pc.pocketrecipe.R;

import java.util.ArrayList;

public class Adapter_Follow extends ArrayAdapter {

    private ArrayList<Follow_Item> List = new ArrayList<>();
    private Context context;
    private int posicion;
    private ListView listView;

    public Adapter_Follow(Context context, int textViewResourceId, ArrayList<Follow_Item> objects, ListView listView) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.List = objects;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        System.out.println("cuenta"+ super.getCount());
        return super.getCount();
    }

    public static class ViewHolder {

        public TextView lyricsTxt, helloTxt;
        // public TextView text1;
        public ImageView imageView;
        public TextView textView;
        public Button seguir;

        // public TextView textWide;
        // ImageView image;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder holder;
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.list_view_items_follow, null);
            holder = new ViewHolder();
            holder.textView = v.findViewById(R.id.nombre);
            holder.imageView = v.findViewById(R.id.imagen);
            holder.seguir = v.findViewById(R.id.seguir);

            v.setTag(holder);
        }else {
            holder = (ViewHolder) v.getTag();
        }
        TextView textView = v.findViewById(R.id.nombre);
        ImageView imageView = v.findViewById(R.id.imagen);
        Button seguir = v.findViewById(R.id.seguir);

        //holder.seguir.setTag(position);

        posicion = position;
        //Log.e("nombreeee",String.valueOf(List.get(position).getName()));
        if (!List.get(position).getFollowed()) {
            seguir.setText("Seguir");

        } else {
            holder.seguir.setText(" Dejar de seguir ");

        }
        textView.setText(List.get(position).getName());
        imageView.setImageBitmap(List.get(position).getImage());

        holder.seguir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (!List.get(position).getFollowed()) {
                    Log.i("boton seguir",Boolean.toString(List.get(position).getFollowed()));
                    holder.seguir.setText(" Dejar de seguir ");

                    List.get(position).setFollowed(true);
                } else {
                    Log.i("boton seguir2",Boolean.toString(List.get(position).getFollowed()));
                    holder.seguir.setText("Seguir");

                    List.get(position).setFollowed(false);
                }
                Log.i("boton seguirsss",Boolean.toString(List.get(position).getFollowed()));


            }
        });



        return v;

    }
    /*
    public void Follow (int position){

        if (!List.get(position).getFollowed()) {
            Log.i("boton seguir",Boolean.toString(List.get(position).getFollowed()));
            holder.seguir.setText(" Dejar de seguir ");

            List.get(position).setFollowed(true);
        } else {
            Log.i("boton seguir2",Boolean.toString(List.get(position).getFollowed()));
            holder.seguir.setText("Seguir");

            List.get(position).setFollowed(false);
        }
    }*/
    public interface BtnClickListener {
        public abstract void onBtnClick(int position);
    }



}
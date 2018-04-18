package com.example.anthony_pc.pocketrecipe.fragments.inicio;


import android.graphics.drawable.Drawable;

public class Item_Inicio {

    String ListName;
    Drawable ListImage;
    String ListCategoria;


    public Item_Inicio(String Name,Drawable Image,String Categoria)
    {
        this.ListImage = Image;
        this.ListName = Name;
        this.ListCategoria = Categoria;
    }
    public String getName()
    {
        return ListName;
    }
    public Drawable getImage()
    {
        return ListImage;
    }
    public String getCategoria()
    {
        return ListCategoria;
    }

}
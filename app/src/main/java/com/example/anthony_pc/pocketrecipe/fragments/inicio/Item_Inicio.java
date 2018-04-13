package com.example.anthony_pc.pocketrecipe.fragments.inicio;


import android.graphics.drawable.Drawable;

public class Item_Inicio {

    String ListName;
    Drawable ListImage;


    public Item_Inicio(String Name,Drawable Image)
    {
        this.ListImage = Image;
        this.ListName = Name;
    }
    public String getName()
    {
        return ListName;
    }
    public Drawable getImage()
    {
        return ListImage;
    }

}
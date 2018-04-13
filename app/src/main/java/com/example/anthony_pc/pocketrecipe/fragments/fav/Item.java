package com.example.anthony_pc.pocketrecipe.fragments.fav;


import android.graphics.drawable.Drawable;

public class Item {

    String ListName;
    Drawable ListImage;
    int ListStars;


    public Item(String Name, Drawable Image, int Stars)
    {
        this.ListImage = Image;
        this.ListName = Name;
        this.ListStars = Stars;
    }
    public String getName()
    {
        return ListName;
    }
    public Drawable getImage()
    {
        return ListImage;
    }
    public int getStars()
    {
        return ListStars;
    }

}
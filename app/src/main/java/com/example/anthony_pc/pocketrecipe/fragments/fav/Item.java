package com.example.anthony_pc.pocketrecipe.fragments.fav;


import android.graphics.drawable.Drawable;

public class Item {

    String ListName;
    Drawable ListImage;
    int ListStars;
    int ListId;


    public Item(String Name, Drawable Image, int Stars, int Id)
    {
        this.ListImage = Image;
        this.ListName = Name;
        this.ListStars = Stars;
        this.ListId = Id;
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
    public int getId()
    {
        return ListId;
    }

}
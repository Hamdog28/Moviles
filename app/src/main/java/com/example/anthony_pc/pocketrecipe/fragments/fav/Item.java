package com.example.anthony_pc.pocketrecipe.fragments.fav;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Item {

    private String ListName;
    private Bitmap ListImage;
    private Float ListStars;
    private int ListId;


    public Item(String Name, Bitmap Image, Float Stars, int Id)
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
    public Bitmap getImage()
    {
        return ListImage;
    }
    public Float getStars()
    {
        return ListStars;
    }
    public int getId()
    {
        return ListId;
    }

}
package com.example.anthony_pc.pocketrecipe.Activites.follow;


import android.graphics.Bitmap;

public class Follow_Item {

    private String ListName;
    private Bitmap ListImage;
    private int ListId;
    private boolean ListFollowed;


    public Follow_Item(String Name, Bitmap Image, int Id,boolean Followed)
    {
        this.ListImage = Image;
        this.ListName = Name;
        this.ListId = Id;
        this.ListFollowed = Followed;
    }
    public String getName()
    {
        return ListName;
    }
    public Bitmap getImage()
    {
        return ListImage;
    }
    public int getId()
    {
        return ListId;
    }
    public boolean getFollowed()
    {
        return ListFollowed;
    }
    public void setFollowed(boolean follow)
    {
        ListFollowed = follow;
    }

}
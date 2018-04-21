package com.example.anthony_pc.pocketrecipe.Activites.follow;


import android.graphics.Bitmap;

public class Follow_Item {

    private String ListName;
    private Bitmap ListImage;
    private boolean ListFollowed;


    public Follow_Item(String Name, Bitmap Image,boolean Followed)
    {
        this.ListImage = Image;
        this.ListName = Name;
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
    public boolean getFollowed()
    {
        return ListFollowed;
    }
    public void setFollowed(boolean follow)
    {
        ListFollowed = follow;
    }

}
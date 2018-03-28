package com.example.anthony_pc.pocketrecipe;

/**
 * Created by Anthony-PC on 27/3/2018.
 */

public class Tags {
    private int id;
    private String tag;
    private int idReceta;

    public Tags(int id, String tag, int idReceta) {
        this.id = id;
        this.tag = tag;
        this.idReceta = idReceta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }
}

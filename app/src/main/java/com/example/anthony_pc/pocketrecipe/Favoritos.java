package com.example.anthony_pc.pocketrecipe;

/**
 * Created by Anthony-PC on 18/4/2018.
 */

public class Favoritos {

    private int id;
    private int idReceta;
    private int idUsuario;

    public Favoritos(int id, int idReceta, int idUsuario) {
        this.id = id;
        this.idReceta = idReceta;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}

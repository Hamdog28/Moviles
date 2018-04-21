package com.example.anthony_pc.pocketrecipe;

/**
 * Created by Anthony-PC on 20/4/2018.
 */

public class Seguidores {

    private int id;
    private int idSeguidor;
    private int idSeguido;

    public Seguidores(int id, int idSeguidor, int idSeguido) {
        this.id = id;
        this.idSeguidor = idSeguidor;
        this.idSeguido = idSeguido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(int idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public int getIdSeguido() {
        return idSeguido;
    }

    public void setIdSeguido(int idSeguido) {
        this.idSeguido = idSeguido;
    }
}

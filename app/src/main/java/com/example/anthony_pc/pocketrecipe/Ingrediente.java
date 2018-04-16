package com.example.anthony_pc.pocketrecipe;

/**
 * Created by Anthony-PC on 16/4/2018.
 */

public class Ingrediente {

    private int id;
    private int recetaID;
    private String nombre;
    private int cantidad;

    public Ingrediente(int id, int recetaID, String nombre, int cantidad) {
        this.id = id;
        this.recetaID = recetaID;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecetaID() {
        return recetaID;
    }

    public void setRecetaID(int recetaID) {
        this.recetaID = recetaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

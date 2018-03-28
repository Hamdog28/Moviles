package com.example.anthony_pc.pocketrecipe;

import android.graphics.drawable.Drawable;

/**
 * Created by Anthony-PC on 27/3/2018.
 */

public class Receta {
    private int id;
    private String nombre;
    private String procedimiento;
    private String dificultad;
    private int porciones;
    private Drawable foto;
    private String costo;

    public Receta(int id, String nombre, String procedimiento, String dificultad, int porciones, Drawable foto, String costo) {
        this.id = id;
        this.nombre = nombre;
        this.procedimiento = procedimiento;
        this.dificultad = dificultad;
        this.porciones = porciones;
        this.foto = foto;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public Drawable getFoto() {
        return foto;
    }

    public void setFoto(Drawable foto) {
        this.foto = foto;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
}

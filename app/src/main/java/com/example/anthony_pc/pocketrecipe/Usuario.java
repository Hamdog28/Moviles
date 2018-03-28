package com.example.anthony_pc.pocketrecipe;

import android.graphics.drawable.Drawable;

/**
 * Created by Anthony-PC on 27/3/2018.
 */

public class Usuario {
    private int id;
    private String nombre;
    private Drawable foto;
    private String correo;

    public Usuario(int id, String nombre, Drawable foto, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.correo = correo;
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

    public Drawable getFoto() {
        return foto;
    }

    public void setFoto(Drawable foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

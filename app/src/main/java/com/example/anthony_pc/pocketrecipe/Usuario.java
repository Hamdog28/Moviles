package com.example.anthony_pc.pocketrecipe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Anthony-PC on 27/3/2018.
 */

public class Usuario {
    private int id;
    private String nombre;
    private Bitmap foto;
    private String descripcion;
    private String correo;
    private String contrasena;
    private ArrayList<Receta> recetasFavoritas;

    public Usuario(int id, String nombre, Bitmap foto, String correo, String descripcion,String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.correo = correo;
        this.descripcion = descripcion;
        this.contrasena = contrasena;
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

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

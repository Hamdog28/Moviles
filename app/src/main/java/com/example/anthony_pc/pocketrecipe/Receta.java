package com.example.anthony_pc.pocketrecipe;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Anthony-PC on 27/3/2018.
 */

public class Receta {
    private int id;
    private String nombre;
    private String duracion;
    private ArrayList<String> procedimiento;
    private String dificultad;
    private int porciones;
    private Bitmap foto;
    private String costo;
    private Float calificacion;
    private int cantCalificaciones;
    private Boolean publico;
    private String notas;
    private int autor;
    private String publicacion;


    private ArrayList<String> listaIngredientes;
    private ArrayList<String> listaTags;

    public Receta(int id, String nombre, String duracion, ArrayList<String> procedimiento, String dificultad, int porciones, Bitmap foto,
                  String costo, Float calificacion, int cantCalificaciones, Boolean publico, String notas,
                  ArrayList<String> listaIngredientes,int autor,String publicacion, ArrayList<String> listaTags) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.procedimiento = procedimiento;
        this.dificultad = dificultad;
        this.porciones = porciones;
        this.foto = foto;
        this.costo = costo;
        this.calificacion = calificacion;
        this.cantCalificaciones = cantCalificaciones;
        this.publico = publico;
        this.notas = notas;
        this.listaIngredientes = listaIngredientes;
        this.autor = autor;
        this.publicacion = publicacion;
        this.listaTags = listaTags;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public ArrayList<String> getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(ArrayList<String> procedimiento) {
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

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public int getCantCalificaciones() {
        return cantCalificaciones;
    }

    public void setCantCalificaciones(int cantCalificaciones) {
        this.cantCalificaciones = cantCalificaciones;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public ArrayList<String> getListaIngredientes() {
        return listaIngredientes;
    }

    public void setListaIngredientes(ArrayList<String> listaIngredientes) {
        this.listaIngredientes = listaIngredientes;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public ArrayList<String> getListaTags() {
        return listaTags;
    }

    public void setListaTags(ArrayList<String> listaTags) {
        this.listaTags = listaTags;
    }
}

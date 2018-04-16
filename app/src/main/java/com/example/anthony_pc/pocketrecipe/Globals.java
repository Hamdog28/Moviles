package com.example.anthony_pc.pocketrecipe;

import com.facebook.Profile;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Anthony-PC on 4/4/2018.
 */

public class Globals {

    private static ArrayList<Usuario> usersList = new ArrayList<>();
    private static ArrayList<Receta> recipeList = new ArrayList<>();
    private static ArrayList<Ingrediente> ingredienteList = new ArrayList<>();
    private static Profile profile;

    private static Usuario actualUser;

    private static Globals instance;

    public static Globals getInstance(){
        if(instance == null){

            instance = new Globals();
            profile = null;
            actualUser = null;
        }
        return instance;
    }

    public Usuario getActualUser() {
        return actualUser;
    }

    public void setActualUser(Usuario actualUser) {
        Globals.actualUser = actualUser;
    }

    public void addUser(Usuario usuario){
        usersList.add(usuario);
    }

    public ArrayList<Usuario> getUserList(){
        return usersList;
    }

    public Profile getProfile() {
        return profile;
    }



    public void setProfile(Profile profile) {
        Globals.profile = profile;
    }

    public ArrayList<Receta> getRecipeList() {
        return recipeList;
    }

    public void addRecipe(Receta receta){
        recipeList.add(receta);
    }

    public void addIngrediente(Ingrediente ingrediente){
        ingredienteList.add(ingrediente);
    }

    public ArrayList<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

}

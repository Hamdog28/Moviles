package com.example.anthony_pc.pocketrecipe;

import android.nfc.Tag;
import android.util.Log;

import com.example.anthony_pc.pocketrecipe.Activites.follow.Follow_Item;
import com.example.anthony_pc.pocketrecipe.fragments.fav.Item;
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
    private static ArrayList<Tags> tagList = new ArrayList<>();
    private static ArrayList<Favoritos> favoritosList = new ArrayList<>();
    private static ArrayList<Seguidores> seguidoresList = new ArrayList<>();

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

    public void setListNull(){
        usersList = new ArrayList<>();
        recipeList = new ArrayList<>();
        ingredienteList = new ArrayList<>();
        tagList = new ArrayList<>();
        favoritosList = new ArrayList<>();
        seguidoresList = new ArrayList<>();
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

    public int returnLastIDTag(){
        int id = -1;
        if(tagList.isEmpty())
            return 0;
        for(Tags i : tagList){
            if(i.getId()>id){
                id = i.getId();
            }
        }return id;
    }

    public void addTag(Tags tag){tagList.add(tag);}

    public void addSeguidor(Seguidores seguidor){seguidoresList.add(seguidor);}

    public void addFavoritos(Favoritos favoritos){favoritosList.add(favoritos);}

    public ArrayList<Tags> getTagList() {
        return tagList;
    }

    public ArrayList<Seguidores> getSeguidoresList() {
        return seguidoresList;
    }

    public ArrayList<Favoritos> getFavoritosList() {
        return favoritosList;
    }

    public ArrayList<Follow_Item> returnSeguidores(int idUser){
        ArrayList<Follow_Item> listaSeguidores = new ArrayList<>();
        Log.e("LARGO LISTA SEGUIDORES", String.valueOf(seguidoresList.size()));
        for(Seguidores i : seguidoresList) {
            if (i.getIdSeguido() == idUser) {
                Usuario user = getUser(i.getIdSeguidor());
                if (user != null) {
                    if (i.getIdSeguidor() == instance.getActualUser().getId()) {
                        Follow_Item item = new Follow_Item(user.getNombre(), user.getFoto(), true);
                        listaSeguidores.add(item);
                    } else {
                        Follow_Item item = new Follow_Item(user.getNombre(), user.getFoto(), false);
                        listaSeguidores.add(item);
                    }
                }
            }
        }
        Log.e("LARGO LISTA SEGUIDORES", String.valueOf(listaSeguidores.size()));
        return listaSeguidores;
    }

    public int getFollowId(){
        int id = -1;
        if(seguidoresList.isEmpty())
            return 1;
        for(Seguidores i : seguidoresList){
            if(i.getId()>id){
                id = i.getId();
            }
        }return id;
    }

    public void deleteSeguidor(int idSeguido){
        for(Seguidores i : seguidoresList){
            if(i.getIdSeguidor() == getActualUser().getId() && i.getIdSeguido() == idSeguido){
                seguidoresList.remove(i);
            }
        }
    }

    public int returnDeleteID(int idReceta){
        if(favoritosList.isEmpty())
            return 0;
        for(Favoritos i : favoritosList){

            if(i.getIdReceta() == idReceta && i.getIdUsuario() == getActualUser().getId()){
                return i.getId();
            }
        }
        return -1;
    }

    public int returnDeleteIdSeguidor(int idSeguido){
        if(seguidoresList.isEmpty())
            return 0;
        for(Seguidores i : seguidoresList){
            if(i.getIdSeguidor() == actualUser.getId() && i.getIdSeguido() == idSeguido){
                return i.getId();
            }
        }
        return -1;
    }

    public int returnLastIDFav(){
        int id = -1;
        if(favoritosList.isEmpty())
            return 1;
        for(Favoritos i : favoritosList){
            if(i.getId()>id){
                Log.e("ID RETURN FAV ID",String.valueOf(id));
                id = i.getId();
            }
        }return id;
    }

    public void deleteFavorito(int idReceta){
        for(Favoritos i : favoritosList){
            if(i.getIdUsuario() == getActualUser().getId() && i.getIdReceta() == idReceta){
                favoritosList.remove(i);
            }
        }
    }

    public boolean checkFav(int idReceta){
        for(Favoritos i : favoritosList){
            if(i.getIdUsuario() == getActualUser().getId() && i.getIdReceta() == idReceta){
                return true;
            }
        }return false;
    }
    public ArrayList<Item> returnFavoritosList(String mensaje){

        ArrayList<Item> listaItems = new ArrayList<>();
        if(mensaje.equals("favorito")){
            for(Favoritos i : favoritosList){
                if(i.getIdUsuario() == getActualUser().getId()){
                    Receta receta = getReceta(i.getIdReceta());
                    if(receta != null) {
                        Log.e("receta",String.valueOf(receta.getNombre()));
                        Item item = new Item(receta.getNombre(), receta.getFoto(), receta.getCalificacion(), receta.getId());
                        listaItems.add(item);
                    }
                }
            }

        }else if(mensaje.equals("todas")){
            for (Receta i : recipeList) {
                Item item = new Item(i.getNombre(), i.getFoto(), i.getCalificacion(), i.getId());
                listaItems.add(item);
            }
        }
        else if(mensaje.equals("perfil")){
            for (Receta i : recipeList) {
                Receta receta = getReceta(i.getId());
                if (i.getAutor() == getActualUser().getId()) {
                    Item item = new Item(receta.getNombre(), receta.getFoto(), receta.getCalificacion(), receta.getId());
                    listaItems.add(item);
                }
            }
        }
        else if(mensaje.contains("T")){
            String[] idString = mensaje.split("T");
            int id = Integer.valueOf(idString[0]);
            for (Receta i : recipeList) {
                Receta receta = getReceta(i.getId());
                if (i.getAutor() == getUser(id).getId()) {
                    Item item = new Item(receta.getNombre(), receta.getFoto(), receta.getCalificacion(), receta.getId());
                    listaItems.add(item);
                }
            }

        }

        else {
            for (Receta i : recipeList) {
                Receta receta = getReceta(i.getId());
                for (String j : i.getListaTags()) {
                    if (j.equals(mensaje)) {
                        Item item = new Item(receta.getNombre(), receta.getFoto(), receta.getCalificacion(), receta.getId());
                        listaItems.add(item);
                    }
                }
            }
        }
        Log.e("Largo",String.valueOf(listaItems.size()));
        return listaItems;
    }

    public Receta getReceta(int recetaID){
        for(Receta i : recipeList){
            if(i.getId() == recetaID){
                return i;
            }
        }return null;
    }

    public Usuario getUser(int idUser){
        for(Usuario i : usersList){
            if(i.getId() == idUser){
                return i;
            }
        }return null;
    }

    public int lastIdUser(){
        int id = -1;
        if(usersList.isEmpty())
            return 1;
        for(Usuario i : usersList){
            if(i.getId()>id){
                id = i.getId();
            }
        }return id;
    }


}

package com.example.anthony_pc.pocketrecipe;

import java.util.ArrayList;

/**
 * Created by Anthony-PC on 4/4/2018.
 */

public class Globals {

    private static ArrayList<Usuario> usersList = new ArrayList<>();

    private static Globals instance;

    public static Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }

    public void addUser(Usuario usuario){
        usersList.add(usuario);
    }

    public ArrayList<Usuario> getUserList(){
        return usersList;
    }
}

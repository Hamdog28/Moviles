package com.example.anthony_pc.pocketrecipe;

import com.facebook.Profile;

import java.util.ArrayList;

/**
 * Created by Anthony-PC on 4/4/2018.
 */

public class Globals {

    private static ArrayList<Usuario> usersList = new ArrayList<>();
    private static Profile profile;
    private static int userID;

    private static Globals instance;

    public static Globals getInstance(){
        if(instance == null){

            instance = new Globals();
            instance.profile = null;
            instance.userID = -1;
        }
        return instance;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        Globals.userID = userID;
    }
}

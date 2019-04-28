package com.example.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple object representing a User.
 * Implements Serializable for easier passing between intents.
 */
public class User implements Serializable {
    private int UID;
    private String userType;
    private List<ClEnt> classList;  //List of Classes this user is enrolled in


    public User(){}

    public User(int UID, String userType){
        this(UID, userType, new ArrayList<ClEnt>());
    }

    public User(int UID, String userType, List<ClEnt> classList){
        this.UID = UID;
        this.userType = userType;
        this.classList = classList;
    }



    public int getUID() {
        return UID;
    }
    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<ClEnt> getClassList() {
        return classList;
    }
    public void setClassList(List<ClEnt> classList) {
        this.classList = classList;
    }
}

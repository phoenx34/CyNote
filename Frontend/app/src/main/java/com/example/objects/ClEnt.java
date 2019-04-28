package com.example.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple object representing a Class (ClassEntity).
 * Implements Serializable for easier passing between intents.
 */
public class ClEnt implements Serializable {
    private int CID;
    private String className;
    private List<Lecture> lectureList;  //List of lectures linked to this Class


    public ClEnt(){}

    public ClEnt(int CID, String className){
        this(CID, className, new ArrayList<Lecture>());
    }

    public ClEnt(int CID, String className, List<Lecture> lectureList){
        this.CID = CID;
        this.className = className;
        this.lectureList = lectureList;
    }



    public int getCID() {
        return CID;
    }
    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }
    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }
}

package com.example.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple object representing a Lecture/Module.
 * Implements Serializable for easier passing between intents.
 */
public class Lecture implements Serializable {
    private int LID;
    private String lectureName;
    private List<Message> shoutoutHistory;   //List of strings that represent messages to this Lecture's ShoutOut
    private List<Note> noteList;


    public Lecture(){}

    public Lecture(int LID, String lectureName){
        this(LID, lectureName, new ArrayList<Message>());
    }

    public Lecture(int LID, String lectureName, List<Message> shoutoutHistory){
        this(LID, lectureName, new ArrayList<Message>(), new ArrayList<Note>());
    }

    public Lecture(int LID, String lectureName, List<Message> shoutoutHistory, List<Note> noteList){
        this.LID = LID;
        this.lectureName = lectureName;
        this.shoutoutHistory = shoutoutHistory;
        this.noteList = noteList;
    }



    public int getLID() {
        return LID;
    }
    public void setLID(int LID) {
        this.LID = LID;
    }

    public String getLectureName() {
        return lectureName;
    }
    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public List<Message> getShoutoutHistory() {
        return shoutoutHistory;
    }
    public void setShoutoutHistory(List<Message> shoutoutHistory) {
        this.shoutoutHistory = shoutoutHistory;
    }

    public List<Note> getNoteList() {
        return noteList;
    }
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
}

package com.example.objects;

public class Note {
    private int NID;
    private String noteName;
    private String path;
    private int rating;

    public Note(){}

    public Note(int NID, String noteName, String path, int rating){
        this.NID = NID;
        this.noteName = noteName;
        this.path = path;
        this.rating = rating;
    }


    public int getNID() {
        return NID;
    }
    public void setNID(int NID) {
        this.NID = NID;
    }

    public String getNoteName() {
        return noteName;
    }
    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}

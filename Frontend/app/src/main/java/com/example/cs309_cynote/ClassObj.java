package com.example.cs309_cynote;

/**
 * Helper class for easier handling of class data in ClassSelection.java
 */
public class ClassObj {

    private int cid;
    private String name;

    public ClassObj(){}

    public ClassObj(int cid, String name){
        this.cid = cid;
        this.name = name;
    }


    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

package com.example.cs309_cynote;

/**
 * Helper class for easier handling of class data in ClassSelection.java
 */
public class ClassObj {

    private int cid;
    private String name;

    /**
     * Default constructor for a Class Object
     */
    public ClassObj(){}

    /**
     * Constructor for Class Object
     * @param cid
     * @param name
     */
    public ClassObj(int cid, String name){
        this.cid = cid;
        this.name = name;
    }


    /**
     * Returns CID for class object
     * @return cid
     */
    public int getCid() {
        return cid;
    }
    /**
     * Sets CID for class object
     * @param cid
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * Returns name for class object
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets name for class object
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}

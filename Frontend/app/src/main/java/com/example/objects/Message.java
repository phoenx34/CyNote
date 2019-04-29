package com.example.objects;

import java.io.Serializable;

/**
 * Simple object representing a ShoutOut Message.
 * Implements Serializable for easier passing between intents.
 */
public class Message implements Serializable {
    private String screenname;      //Username
    private String message;

    public Message(){}

    public Message(String screenname, String message){
        this.screenname = screenname;
        this.message = message;
    }


    public String getScreenname() {
        return screenname;
    }
    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

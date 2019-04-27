package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;

public class AddNewLecture extends AppCompatActivity {
    private EditText editInputLectureID;
    private int CID,LID;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lecture);
        editInputLectureID = findViewById(R.id.newLectureInput);//link editText

        Bundle extras = getIntent().getExtras();//get data from previous activity
        try{
            //----Ensuring the data actually exists----\\

            //If the extras does not exist, big oof
            if(extras == null)
                throw new Exception("No data received");

            //Try pulling data from extras
            setCid(extras.getInt("CID"));//get UID
            setClassName(extras.getString("className"));
            //If data does not exist, big oof
        }
        catch(JSONException e) {
            System.out.println("JSONException: ");
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addNewLecture(final View view){

        String inputStringCheck = editInputLectureID.getText().toString();
        //check if the Code is null
        if(inputStringCheck == null || inputStringCheck.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid Lecture ID, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        setLid(Integer.parseInt(inputStringCheck));//record lecture code from input

        final APICalls api = new APICalls(getApplicationContext());//new APICalls
        //create url link with CID and LID
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/classes/";
        url += getCid() + "/" + getLid()
        ;

        //get correct response to get class list and go to ClassSelection page
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //call API to get class list and go to ClassSelection page
                Toast.makeText(getApplicationContext(), "New Lecture is created!", Toast.LENGTH_LONG).show();
                api.getModuleList(view, getClassName(), getCid());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Cannot create lecture");
                System.out.println(error.getMessage());
            }
        };

        try {
            //try Get method to add user to a class
            api.volleyGet(url, responseListener, errorListener);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Set CID.
     * @param inputNumber int value for setting CID
     */
    public void setCid(int inputNumber){
        CID = inputNumber;
    }

    /**
     * get CID.
     * @return Return CID as int
     */
    public int getCid(){
        return CID;
    }

    /**
     * Set LID.
     * @param inputNumber int value for setting LID
     */
    public void setLid(int inputNumber){
        LID = inputNumber;
    }

    /**
     * get LID.
     * @return Return LID as int
     */
    public int getLid(){
        return LID;
    }

    /**
     * Set className.
     * @param inputString String value for setting className
     */
    public void setClassName(String inputString){
        className = inputString;
    }

    /**
     * get className.
     * @return Return className as String
     */
    public String getClassName(){
        return className;
    }
}

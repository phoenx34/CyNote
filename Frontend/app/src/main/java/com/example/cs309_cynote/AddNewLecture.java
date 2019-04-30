package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.objects.ClEnt;

import org.json.JSONException;

public class AddNewLecture extends AppCompatActivity {
    private EditText editInputLectureID, editInputLectureName;

    private ClEnt clEnt;
    private int LID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lecture);
        editInputLectureID = findViewById(R.id.newLectureInput);//link editText
        editInputLectureName = findViewById(R.id.newLectureName);//link editText


        //Grab the android intent
        Intent intent = getIntent();


        // Grab ClEnt passed through intent
        try{
            //Grab the received User
            ClEnt clEnt = (ClEnt)intent.getSerializableExtra("Class");
            if(clEnt == null)
                throw new Exception("No class received in AddNewLecture");

            this.clEnt = clEnt;
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }

    }


    public void addNewLecture(final View view){

        String inputCodeCheck = editInputLectureID.getText().toString();
        String inputNameCheck = editInputLectureName.getText().toString();
        //check if the Code is null
        if(inputCodeCheck == null || inputCodeCheck.trim().length() == 0 ||
                inputNameCheck == null || inputNameCheck.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid Lecture ID or Name, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        this.LID = (Integer.parseInt(inputCodeCheck)); //record lecture code from input

        final APICalls api = new APICalls(getApplicationContext());//new APICalls
        //create url link with CID
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/classes/";
        url += clEnt.getCID() + "/lecture";

        String json = "{\"id\":" + clEnt.getCID() + "," +
                "\"name\":" + inputNameCheck + "}";

        //get correct response to get class list and go to ClassSelection page
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //call API to get class list and go to ClassSelection page
                Toast.makeText(getApplicationContext(), "New Lecture is created!", Toast.LENGTH_LONG).show();

                //TODO
                //Here, because we have the lecture object, just add this to the list of lectures

                //api.getModuleList(view, getClassName(), getCid());
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
            api.volleyPost(url, json, responseListener, errorListener);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void goBackFromAddNewLecture(View view) {
        finish();
    }
}

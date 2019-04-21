package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Login page, this should be the initial page that shows up in APP
 *
 * @Author Zheming Fan, Sean Gordon
 * @Since 2019-02-17
 */
public class Login extends AppCompatActivity
{
    private EditText emailIn, passwordIn;
    private Button loginB, createB, jumpProfessor, jumpTA, jumpStudent;
    private int uID, userType;
    private String loginURL = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";
    String emailInString, passwordInString;
    boolean loginCondition = false;//initial login condition to false

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginB = findViewById(R.id.loginBot);
        //createB = findViewById(R.id.creationBut);
        //emailIn = findViewById(R.id.emailInput);      //Currently swapped for a username input
        passwordIn = findViewById(R.id.passwordInput);
        jumpProfessor = findViewById(R.id.professorJumpPage);
        jumpTA = findViewById(R.id.taJumpPage);
        jumpStudent = findViewById(R.id.studentJumpPage);



        jumpProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testForProPage = new Intent(Login.this, ProfessorMain.class);
                startActivity(testForProPage);
            }
        });
    }





    /**
     * Upon entering login information and hitting submit, this method calls getClassList
     * with the received UserID to make another get request for the list of classes
     * tied to the user.
     *
     * Screenname, password  -->  Server
     *               UserID  <--  Server
     *
     * @param view  View selected to submit login form
     */
    public void getUID(final View view){

        String url = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";    //Server-side url to receive screenname and password as params



        //Grab entered screenname
        EditText editScreenname = findViewById(R.id.screennameInput);
        String screenname = editScreenname.getText().toString();

        //Grab entered password
        EditText editPassword = findViewById(R.id.passwordInput);
        String password = editPassword.getText().toString();


        //Test for empty entries
        if(!isScreennameValid(screenname)){
            Toast.makeText(getApplicationContext(), "Invalid username, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isPasswordValid(password)){
            Toast.makeText(getApplicationContext(), "Invalid password, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        //Possibly test for valid emails later

        //Add login form data as parameters
        //url += "?screenname="+screenname+"&password="+password;
        url += "/"+screenname+"/"+password;



        APICalls api = new APICalls(getApplicationContext());

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Call another method to make another get request using the received stuff
                getClassList(view, response);
            }
        };

        //Set up listener for error case
        //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Login unsuccessful");
                System.out.println(error.getMessage());
            }
        };


        //Uses the APICalls generic volley get request
        try{
            api.volleyGet(url, responseListener, errorListener);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * Upon successfully submitting login form and receiving a UserID, call this method
     * (usually through a callback object) with received UID to get the list of classes
     * tied to UID
     *
     *         UID  -->  Server
     *  Class List  <--  Server
     *
     * @param view  View selected to submit login form
     * @param json  Received JSON string from login
     */
    public void getClassList(final View view, String json){

        System.out.println("UIDtoClassSelection: \n"+json);

        try{
            //JSON comes in the form of {"status":3,"UID":0}
            JSONObject jsonObj = new JSONObject(json);

            int status = jsonObj.getInt("status");
            int UID = jsonObj.getInt("UID");

            switch(status){
                case 3: throw new Exception("Username is incorrect");
                    //case 4: Case 4 clears, no need to throw an exception
                case 5: throw new Exception("Password is incorrect");
            }


            //            http://cs309-sd-7.misc.iastate.edu:8080//users_class//{id}
            String url = "http://cs309-sd-7.misc.iastate.edu:8080/users_class";    //Server-side url to receive list of classes for UID

            //Add UID to path
            url += "/" + UID;



            APICalls api = new APICalls(getApplicationContext());

            //Set up listener for success case
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("ClassList received:\n");
                    System.out.println(response);

                    Intent intent = new Intent(view.getContext(), ClassSelection.class);
                    intent.putExtra("data", response);  //Link received data to ClassSelection intent
                    startActivity(intent);
                }
            };

            //Set up listener for error case
            //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Login unsuccessful");
                    System.out.println(error.getMessage());
                }
            };


            //Uses the APICalls generic volley get request
            try{
                api.volleyGet(url, responseListener, errorListener);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }


        }catch(JSONException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }






    /**
     * Used to ensure the entered email is a valid one
     *
     * @param emailText Email String from input
     * @return boolean value about if the email is valid
     */
    public boolean isEmailValid(String emailText)//method that check email valid
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }
    public boolean isScreennameValid(String screenname){
        if(screenname == null || screenname.trim().length() == 0){
            return false;
        }
        return true;
    }
    public boolean isPasswordValid(String password){
        if(password == null || password.trim().length() == 0){
            return false;
        }
        return true;
    }



    /**
     * Upon clicking "New user?" text view, calls this function to change views
     * to the account creation page.
     *
     * @param view View selected to go to account creation page.
     */
    public void gotoAccCreation(View view){
        Intent intent = new Intent(this, AccCreation.class);
        startActivity(intent);
    }

    public void gotoClassSelection(View view){
        Intent intent = new Intent(this, ClassSelection.class);
        startActivity(intent);
    }

    public void gotoShoutout(View view){
        Intent intent = new Intent(this, ShoutOut.class);
        startActivity(intent);
    }
}
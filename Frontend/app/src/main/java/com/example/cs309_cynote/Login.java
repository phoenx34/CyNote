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
 * Login page, this should be the initial page that show up in APP
 *
 * @Author Zheming Fan
 * @Since 2019-02-17
 */
public class Login extends AppCompatActivity
{
    private EditText emailIn, passwordIn;
    private Button loginB, createB, jumpProfessor, jumpTA, jumpStudent;
    private int uID, userType;
    private String loginURL = "http://cs309-sd-7.misc.iastate.edu:8080/login";
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

        /* Directly setting loginB onClick

        loginB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailInString = emailIn.getText().toString();//Get email String from input
                passwordInString = passwordIn.getText().toString();//Get password String from input

                if(isEmailValid(emailInString))//check if email is valid
                {
                    //use APICalls to send json
                    sendRequest();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid email, try again!", Toast.LENGTH_LONG).show();//display massage that email is invalid.
                }

                if(loginCondition)//check if login successful
                {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();//display massage that login is successful
//                    Intent userPage = new Intent(Login.this, UserMain.class);//jump to user main page
//                    startActivity(userPage);//start user main page
                    if(userType == 0)//professor type is 0
                    {
                        Intent toProPage = new Intent(Login.this, ProfessorMain.class);
                        toProPage.putExtra("UID", uID);
                        startActivity(toProPage);
                    }
                    else if(userType == 1)//TA type is 1
                    {
                        Intent toTaPage = new Intent(Login.this, TaMain.class);
                        toTaPage.putExtra("UID", uID);
                        startActivity(toTaPage);
                    }
                    else//student type is 3, else
                    {
                        Intent toStudentPage = new Intent(Login.this, StudentMain.class);
                        toStudentPage.putExtra("UID", uID);
                        startActivity(toStudentPage);
                    }
                    finish();//kill this page
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login fails, email or password is wrong!", Toast.LENGTH_SHORT).show();//return massage
                }
            }
        });
        */

        /*
        createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creationPage = new Intent(Login.this, AccCreation.class);
                startActivity(creationPage);
                finish();
            }
        });*/

        jumpProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testForProPage = new Intent(Login.this, ProfessorMain.class);
                startActivity(testForProPage);
                finish();
            }
        });

        jumpTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testForTaPage = new Intent(Login.this, TaMain.class);
                startActivity(testForTaPage);
                finish();
            }
        });

        jumpStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testForStudentPage = new Intent(Login.this, StudentMain.class);
                startActivity(testForStudentPage);
                finish();
            }
        });
    }

    /**
     * Boolean method used to check if the email is valid by checking if it contains @.
     * @param emailText email input String.
     * @return boolean, true if it contains @.
     */
    private boolean isEmailValid(String emailText)//method that check email valid
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }

    /**
     * Upon clicking "New user?" text view, calls this function to change views
     * to the account creation page.
     *
     * @param view
     */
    public void gotoAccCreation(View view){
        Intent intent = new Intent(this, AccCreation.class);
        startActivity(intent);
    }

    public void gotoClassSelection(View view){
        Intent intent = new Intent(this, ClassSelection.class);
        startActivity(intent);
    }



/*
    //Server side login currently fucked, skipping login and using hardcoded ID
    public void loginToUID(final View view){
        UIDtoClassSelection(view, 1);
    }


*/





    /**
     * Upon submitting login form, call this method to send a get request to server
     * containing login information as url parameters, and receive a User ID with the
     * specified callback object
     *
     * @param view  View selected to submit login form
     */
    public void loginToUID(final View view){

        String url = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";    //Server-side url to receive screenname and password as params




        /*Server-side currently accepts only username and password, not email, so we will be treating
        //email as username for the time being
        EditText editEmail = findViewById(R.id.emailInput);
        String email = editEmail.getText().toString();*/


        //Grab entered screenname
        EditText editScreenname = findViewById(R.id.screennameInput);
        String screenname = editScreenname.getText().toString();

        //Grab entered password
        EditText editPassword = findViewById(R.id.passwordInput);
        String password = editPassword.getText().toString();

        //Test for empty entries
        if(screenname == null || screenname.trim().length() == 0){
            Toast.makeText(getApplicationContext(), "Invalid username, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(getApplicationContext(), "Invalid password, try again!", Toast.LENGTH_LONG).show();
            return;
        }

        //Add login form data as parameters
        //url += "?screenname="+screenname+"&password="+password;
        url += "/"+screenname+"/"+password;




        APICalls api = new APICalls(getApplicationContext());

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Call another method to make another get request using the received UID
                UIDtoClassSelection(view, Integer.parseInt(response));

                /*
                Intent intent = new Intent(view.getContext(), ClassSelection.class);
                intent.putExtra("data", response);  //Link received data to ClassSelection intent
                startActivity(intent);
                */
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
     * (usually through a callback object) with current view and received UID to get
     * request list of classes tied to UID
     *
     * @param view  View selected to submit login form
     * @param UID   UID to get request for classList
     */
    public void UIDtoClassSelection(final View view, int UID){
        //            http://cs309-sd-7.misc.iastate.edu:8080//users_class//{id}
        String url = "http://cs309-sd-7.misc.iastate.edu:8080//users_class//";    //Server-side url to receive list of classes for UID

        //Add UID to path
        url += UID;



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
    }





    public void sendRequest(){
        final RequestQueue ReqQueue = Volley.newRequestQueue(this);
        JsonObjectRequest loginReq = new JsonObjectRequest(Request.Method.POST,
                loginURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            uID = response.getInt("UID");
                            loginCondition = response.getBoolean("loginCondition");
                            userType = response.getInt("userType");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", emailInString);
                params.put("password", passwordInString);
                return params;
            }

        };
        ReqQueue.add(loginReq);

    }


    /*
    public void testGet(final View view) throws IOException, JSONException {


        String url = "http://webhook.site/aee170c8-ab5b-49d1-845c-b625a4768066";    //Server-side url to receive userID

        EditText editEmail = findViewById(R.id.emailInput);
        String email = editEmail.getText().toString();

        EditText editPassword = findViewById(R.id.passwordInput);
        String password = editPassword.getText().toString();

        url += "?email="+email+"&password="+password;



        APICalls api = new APICalls();

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                //Intent intent = new Intent(view.getContext(), ClassSelection.class);
                //intent.putExtra("data", response);  //Link received data to ClassSelection intent
                //startActivity(intent);
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
    */

}

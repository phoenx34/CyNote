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
    private int uID;
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
        emailIn = findViewById(R.id.emailInput);
        passwordIn = findViewById(R.id.passwordInput);
        jumpProfessor = findViewById(R.id.professorJumpPage);
        jumpTA = findViewById(R.id.taJumpPage);
        jumpStudent = findViewById(R.id.studentJumpPage);

        loginB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailInString = emailIn.getText().toString();//Get email String from input
                passwordInString = passwordIn.getText().toString();//Get password String from input

                if(isEmailValid(emailInString))//check if email is valid
                {
                    //use APIcalls to send json
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
                    if(uID == 1)
                    {
                        Intent toProPage = new Intent(Login.this, ProfessorMain.class);
                        toProPage.putExtra("UID", uID);
                        startActivity(toProPage);
                    }
                    else if(uID == 2)
                    {
                        Intent toTaPage = new Intent(Login.this, TaMain.class);
                        toTaPage.putExtra("UID", uID);
                        startActivity(toTaPage);
                    }
                    else
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
    private boolean isEmailValid(String emailText)
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

    /**
     * Upon submitting login form, call this method to send a get request to server
     * containing login information as url parameters, and upon callback use what
     * is defined in this personalized responseObject
     *
     * @param view  View selected to submit login form
     */
    public void loginToClassSelection(final View view){

        //TODO replace with correct url
        String url = "http://webhook.site/aee170c8-ab5b-49d1-845c-b625a4768066";    //Server-side url to receive userID

        EditText editEmail = findViewById(R.id.emailInput);
        String email = editEmail.getText().toString();

        EditText editPassword = findViewById(R.id.passwordInput);
        String password = editPassword.getText().toString();

        //Add login form data as parameters
        url += "?email="+email+"&password="+password;

        APICalls api = new APICalls();

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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


    public void testGet(final View view) throws IOException, JSONException {


        //TODO replace with correct url
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

}

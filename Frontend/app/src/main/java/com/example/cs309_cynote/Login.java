package com.example.cs309_cynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.objects.ClEnt;
import com.example.objects.User;

import java.util.List;

/**
 * Login page, this should be the initial page that shows up in APP
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
     * Used to grab entered login form data and kick off login process
     * @param view
     */
    public void formDatatoLogin(final View view){
        //Grab entered screenname
        EditText editScreenname = findViewById(R.id.screennameInput);
        String screenname = editScreenname.getText().toString();

        //Grab entered password
        EditText editPassword = findViewById(R.id.passwordInput);
        String password = editPassword.getText().toString();

        final APICalls apiCalls = new APICalls(this.getApplicationContext());


        //Define callbacks for call to getClassList
        final APICallbacks classCallbacks = new APICallbacks<User>() {
            @Override
            public void onResponse(User user) {
                //Now that this user is completed, move to ClassSelection as login is complete
                Intent intent = new Intent(view.getContext(), ClassSelection.class);
                intent.putExtra("User", user);         //Add User to ClassSelection intent
                startActivity(intent);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                System.out.println(error.getMessage());
            }
        };


        //Define callbacks for call to getUserNoClassList
        APICallbacks userCallbacks = new APICallbacks<User>() {
            @Override
            //getUserNoClasses returns a User object with an empty classList
            public void onResponse(User user) {
                //Received User is missing its classList, let's get that
                apiCalls.getClassList(user, classCallbacks);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                System.out.println(error.getMessage());
            }
        };

        //Begin the magic
        apiCalls.getUserNoClassList(screenname, password, userCallbacks);
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

    //Purely for testing purposes

    /**
     * Purely for testing
     * @param view
     */
    public void gotoClassSelection(View view){
        String sampleResponse = "{\"classes\":[{\"cid\":\"1\",\"name\":\"ComS 311\"},{\"cid\":\"2\",\"name\":\"ComS 309\"}]}";
        int sampleUID = 1;

        Intent intent = new Intent(this, ClassSelection.class);
        intent.putExtra("classList", sampleResponse);         //Add classList to ClassSelection intent
        intent.putExtra("UID", sampleUID);                    //Add UID to ClassSelection intent
        startActivity(intent);
    }

    /**
     * Moves intent to ShoutOut view
     * @param view
     */
    public void gotoShoutout(View view){
        Intent intent = new Intent(this, ShoutOut.class);
        startActivity(intent);
    }
}
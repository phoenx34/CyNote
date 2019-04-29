package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User creation page used to create new user.
 *
 * @author Sean Gordon, Zheming Fan
 * @since  2019-02-15
 */
public class AccCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_creation);
    }

    /**
    * Used to submit login form info to server. Takes current state of Username, Email, Password, and AccType,
    * adds them to a post request body, then sends them to the server. Receives success or faiure state and acts
    * accordingly.
    */
    public void submitForm(final View view) throws JSONException {

        EditText ACUsername = (EditText) findViewById(R.id.ACUsername); //Find the specific EditText
        final String username = ACUsername.getText().toString().trim();       //Get string, and trim for trailing whitespace

        EditText ACEmail = (EditText) findViewById(R.id.ACEmail);
        String email = ACEmail.getText().toString().trim();

        EditText ACPassword = (EditText) findViewById(R.id.ACPassword);
        final String password = ACPassword.getText().toString().trim();

        Spinner ACDropdown = (Spinner) findViewById(R.id.ACDropdown);
        String accType = ACDropdown.getSelectedItem().toString().trim();


        //Test for empty entries
        if(!isEmailValid(email)){
            Toast.makeText(getApplicationContext(), "Invalid email, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        // Screenname and Password are tested in APICalls getUserNoClassList()


        //TODO Server does not currently create new ID, so I'm using a number gen here. NEEDS to be fixed
        Random rand = new Random();
        int randInt = rand.nextInt(100000); //0-99999

        //Getting current account creation time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String creationTime =  sdf.format(cal.getTime());

        String json = "{\"UID\":\""+randInt+"\"," +
                      "\"screenname\":\""+username+"\"," +
                      "\"email\":\""+email+"\", " +
                      "\"password\":\""+password+"\", " +
                      "\"create_time\":\""+creationTime+"\", " +
                      "\"type\":\""+accType+"\"}";

        //Could create a pojo but doesn't really matter

        //String testUrl = "http://ptsv2.com/t/mp8ul-1550461405/post";
        String serverUrl = "http://cs309-sd-7.misc.iastate.edu:8080/users";



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
        final APICallbacks userCallbacks = new APICallbacks<User>() {
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

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);


                //Assuming data is correct each time...
                apiCalls.getUserNoClassList(username, password, userCallbacks);

            }
        };

        //Set up listener for error case
        //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Account creation unsuccessful");
                System.out.println(error.getMessage());

                //Assuming data is correct each time...
                apiCalls.getUserNoClassList(username, password, userCallbacks);
            }
        };

        System.out.println("Calling API");
        apiCalls.volleyPost(serverUrl, json, responseListener, errorListener);

    }





    /**
     * Used to ensure the entered email is a valid one
     *
     * @param emailText Email String from input
     * @return boolean value about if the email is valid
     */
    private boolean isEmailValid(String emailText)//method that check email valid
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }



    /**
     * Upon clicking "Back" text view, calls this function to change views
     * to the login page.
     *
     * @param view
     */
    public void gotoLogin(View view){
        finish();
        /*
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        */
    }
}

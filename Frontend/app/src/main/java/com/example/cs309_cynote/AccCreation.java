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
 * @author Sean Gordon
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
        //System.out.println("Submit pressed - ");

        EditText ACUsername = (EditText) findViewById(R.id.ACUsername); //Find the specific EditText
        final String username = ACUsername.getText().toString().trim();       //Get string, and trim for trailing whitespace

        EditText ACEmail = (EditText) findViewById(R.id.ACEmail);
        String email = ACEmail.getText().toString().trim();

        EditText ACPassword = (EditText) findViewById(R.id.ACPassword);
        final String password = ACPassword.getText().toString().trim();

        Spinner ACDropdown = (Spinner) findViewById(R.id.ACDropdown);
        String accType = ACDropdown.getSelectedItem().toString().trim();


        //Test for empty entries
        if(!isScreennameValid(username)){
            Toast.makeText(getApplicationContext(), "Invalid username, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isEmailValid(email)){
            Toast.makeText(getApplicationContext(), "Invalid email, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isPasswordValid(password)){
            Toast.makeText(getApplicationContext(), "Invalid password, try again!", Toast.LENGTH_LONG).show();
            return;
        }


        /*
        //Throw together the json
        String json = "{\"username\":\""+username+"\"," +
                       "\"email\":\""+email+"\", " +
                       "\"password\":\""+password+"\", " +
                       "\"type\":\""+accType+"\"}";
        */

        //Updated JSON to align with server side
        //Server does not currently create new ID, so I'm using a number gen here. NEEDS to be fixed
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

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);


                //Assuming data is correct each time...
                getUID(view, username, password);

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
                getUID(view, username, password);
            }
        };

        System.out.println("Calling API");
        APICalls api = new APICalls(getApplicationContext());
        api.volleyPost(serverUrl, json, responseListener, errorListener);


//        Intent goBackfromC2 = new Intent(MainActivity.this, Login.class);
//        startActivity(goBackfromC2);

    }


    /**
     * Given correct login information, this method calls the server for a UserID.
     * This method then calls getClassList with the received UserID to make another
     * get request for the list of classes tied to the user.
     *
     * Screenname, password  -->  Server
     *               UserID  <--  Server
     *
     * @param view  View selected to submit login form
     * @param screenname Screenname/Username to submit
     * @param password Password to submit
     */
    public void getUID(final View view, String screenname, String password){

        String url = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";    //Server-side url to receive screenname and password as params

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
                //Parse the received Json and call getClassList
                try{
                    //JSON comes in the form of
                    // {"status":3,"UID":0}
                    JSONObject jsonObj = new JSONObject(response);

                    int status = jsonObj.getInt("status");
                    int UID = jsonObj.getInt("UID");

                    switch(status){
                        case 3: throw new Exception("Username is incorrect");
                            //case 4: Case 4 clears, no need to throw an exception
                        case 5: throw new Exception("Password is incorrect");
                    }

                    //Call another method to make another get request using the received stuff
                    getClassList(view, UID);
                }
                catch(JSONException e){
                    System.out.println(e.getMessage());
                    return;
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    //Toast this
                    return;
                }
            }
        };

        //Set up listener for error case
        //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Get UID error");
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
     * @param UID  Received ID from login
     */
    public void getClassList(final View view, final int UID){

        System.out.println("getClassList: \n"+UID);

        try{

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
                    intent.putExtra("UID", UID);//Link UID to ClassSelection intent
                    startActivity(intent);
                }
            };

            //Set up listener for error case
            //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Get classList error");
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
        catch (Exception e){
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

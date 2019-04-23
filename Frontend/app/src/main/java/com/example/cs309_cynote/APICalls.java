package com.example.cs309_cynote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * API used to send information to the server, contains several types of HTTP requests
 *
 * @author Sean Gordon
 * @since 2019-02-17
 */
public class APICalls{
    private RequestQueue requestQueue;
    private Context applicationContext;

    /**
     * Default constructor for APICalls. DO NOT USE! Pass the constructor the
     * current applicationContext instead when creating this object
     */
    public APICalls(){
        //DO NOT USE
        //Android needs this for its reflection process
        //Debating making this throw an error
    }

    /**
     * Use this constructor when creating an APICalls object
     *
     * @param applicationContext
     */
    public APICalls(Context applicationContext){
        //Found using getApplicationContext()
        this.applicationContext = applicationContext;
    }

    /**
     * Returns the current request queue for this object
     *
     * @return requestQueue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(applicationContext);
        return requestQueue;
    }

    /**
     * Adds the request alongside the given tag to the request queue for this object
     *
     * @param request
     * @param tag
     */
    public void addToRequestQueue(Request request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? APICalls.class.getSimpleName() : tag);
        getRequestQueue().add(request);
    }

    /**
     * Adds the request alongside a default tag to the request queue for this object
     *
     * @param request
     */
    public void addToRequestQueue(Request request) {
        // set the default tag
        request.setTag(APICalls.class.getSimpleName());
        getRequestQueue().add(request);
    }

    /**
     * Removes all requests with the given tag from the request queue for this object
     *
     * @param tag
     */
    public void cancelAllRequests(String tag) {
        if (requestQueue != null)
            getRequestQueue().cancelAll(tag);
    }


    //----------------------------------------------------------------------------------------------
    //                                        Request Bodies
    //----------------------------------------------------------------------------------------------


    /**
     * Volley-based post request, used to send post requests to any formatted urlString and receive
     * data using Response listeners
     *
     * @param urlString        String representing the url to send data to
     * @param data             Data to be packaged with the post request to be sent to the supplied url
     * @param responseListener Response.Listener, used to handle success responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToUID'
     * @param errorListener    Response.ErrorListener, used to handle error responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToUID'
     */
    protected void volleyPost(String urlString, String data, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) throws JSONException {
        JSONObject jsonObj = new JSONObject(data);

        if (responseListener == null)
            throw new IllegalArgumentException("responseListener null!");
        if (errorListener == null)
            throw new IllegalArgumentException("errorListener null!");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                urlString,
                jsonObj,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onResponse(response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorListener.onErrorResponse(error);
                    }
                }
        );

        addToRequestQueue(jsonObjectRequest, "postRequest");
    }






    /**
     * Volley-based get request, used to send get requests to any formatted urlString and receive
     * data using Response listeners
     *
     * @param urlString        String representing the url to send request to, must include all parameters in the form
     *                         "sampleUrl.com/endpoint"    or    "sampleUrl.com/endpoint?data1=12345&data2=something"
     * @param responseListener Response.Listener, used to handle success responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToUID'
     * @param errorListener    Response.ErrorListener, used to handle error responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToUID'
     */
    public void volleyGet(String urlString, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) throws IllegalArgumentException {

        if (responseListener == null)
            throw new IllegalArgumentException("responseListener null!");
        if (errorListener == null)
            throw new IllegalArgumentException("errorListener null!");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlString,
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseListener.onResponse(response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorListener.onErrorResponse(error);
                    }
                }
        );

        addToRequestQueue(jsonObjectRequest, "getRequest");
    }

    //Identical to above, but returns a JSONArray rather than a JSONObject
    public void volleyGetArray(String urlString, final Response.Listener<String> responseListener, final Response.ErrorListener errorListener) throws IllegalArgumentException {

        if (responseListener == null)
            throw new IllegalArgumentException("responseListener null!");
        if (errorListener == null)
            throw new IllegalArgumentException("errorListener null!");


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlString,
                null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseListener.onResponse(response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorListener.onErrorResponse(error);
                    }
                }
        );

        addToRequestQueue(jsonArrayRequest, "getRequest");
    }



    //----------------------------------------------------------------------------------------------
    //                                    Commonly Used Requests
    //----------------------------------------------------------------------------------------------



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
            Toast.makeText(this.applicationContext, "Invalid username, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        if(!isPasswordValid(password)){
            Toast.makeText(this.applicationContext, "Invalid password, try again!", Toast.LENGTH_LONG).show();
            return;
        }

        //Add login form data as parameters
        //url += "?screenname="+screenname+"&password="+password;
        url += "/"+screenname+"/"+password;


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
                        case 3: throw new Exception("Invalid username, try again!");
                        //case 4: Case 4 clears, no need to throw an exception
                        case 5: throw new Exception("Invalid password, try again!");
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
            volleyGet(url, responseListener, errorListener);
        }
        catch (Exception e){
            Toast.makeText(this.applicationContext, e.getMessage(), Toast.LENGTH_LONG).show();
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



            //Set up listener for success case
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("ClassList received:\n");
                    System.out.println(response);

                    Intent intent = new Intent(view.getContext(), ClassSelection.class);
                    intent.putExtra("classList", response);         //Add classList to ClassSelection intent
                    intent.putExtra("UID", UID);                    //Add UID to ClassSelection intent
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       //We are starting an intent outside of an activity context, so this is needed
                    applicationContext.startActivity(intent);
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
                volleyGet(url, responseListener, errorListener);
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
     * Get request using class ID to get the list of modules associated with this class
     *
     *          CID  -->  Server
     *  Module List  <--  Server
     *
     * @param view  View selected to submit login form
     * @param CID  CID of class to search
     */
    public void getModuleList(final View view, final String className, final int CID){

        System.out.println("getModuleList: \n"+CID);

        try{
            //            http://cs309-sd-7.misc.iastate.edu:8080/classes/{CID}/lecture
            String url = "http://cs309-sd-7.misc.iastate.edu:8080/classes";    //Server-side url to receive list of modules/lectures for Class CID

            //Add CID to path
            url += "/" + CID + "/lecture";



            //Set up listener for success case
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Module list received:\n");
                    System.out.println(response);

                    Intent intent = new Intent(view.getContext(), ModuleSelection.class);
                    intent.putExtra("className", className);
                    intent.putExtra("moduleList", response);
                    intent.putExtra("CID", CID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       //We are starting an intent outside of an activity context, so this is needed
                    applicationContext.startActivity(intent);
                }
            };

            //Set up listener for error case
            //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(applicationContext, "There was an error retrieving the lecture list", Toast.LENGTH_LONG).show();
                    System.out.println("Get moduleList error");
                    System.out.println(error.getMessage());
                }
            };


            //Uses the APICalls generic volley get request
            try{
                //Gets an array rather than an object, important distinction
                volleyGetArray(url, responseListener, errorListener);
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
     * Get ShoutOut history of a lecture with id LID
     *
     *               LID  -->  Server
     *  ShoutOut History  <--  Server
     *
     * @param view  View selected to submit login form
     * @param LID  CID of class to search
     */
    public void getShoutOutHistory(final View view, final String lecName, final int LID){

        System.out.println("getShoutOutHistory: \n"+LID);

        try{
            //            http://cs309-sd-7.misc.iastate.edu:8080/shoutout/{LID}
            String url = "http://cs309-sd-7.misc.iastate.edu:8080/shoutout/";    //Server-side url to receive list of modules/lectures for Class CID

            //Add LID to path
            url += LID;



            //Set up listener for success case
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("ShoutOut history received:\n");
                    System.out.println(response);

                    Intent intent = new Intent(view.getContext(), ShoutOut.class);
                    intent.putExtra("lecName", lecName);
                    intent.putExtra("history", response);
                    intent.putExtra("LID", LID);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);       //We are starting an intent outside of an activity context, so this is needed
                    applicationContext.startActivity(intent);
                }
            };

            //Set up listener for error case
            //In the case of a bad login, returns a 401 for Unauthorized with a WWW-Authenticate header
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(applicationContext, "There was an error retrieving the ShoutOut history", Toast.LENGTH_LONG).show();
                    System.out.println("Get ShoutOut history error");
                    System.out.println(error.getMessage());
                }
            };


            //Uses the APICalls generic volley get request
            try{
                //Gets an array rather than an object, important distinction
                volleyGetArray(url, responseListener, errorListener);
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



    /*  I lied volley is easy. Keeping this here for reference

    //I don't have time to learn volley so I'm just forgetting about it
    protected String httpGet(String urlString, String data) throws IOException {

        //Make a new URL object with the given url, and add any given parameters
        URL url = new URL(urlString + "?" + data);
        //Ends up as "sampleUrl.com/endpoint?data1=12345&data2=something"
        //Data must be in the form of "" or "data1=12345&..."

        System.out.println("\n---------------------------------------\n");
        System.out.println(url.toString());     //For testing

        //Make a new HttpURLConnection
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();           //Grab the response code

        if (responseCode == HttpURLConnection.HTTP_OK) {    //If success...
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));                 //Read input

            String inputLine;
            StringBuffer response = new StringBuffer();     //Make a new StringBuffer
            // to contain response
            //While there is more reponse to read...
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);                 //Read more response
            }

            in.close();                     //Close the BufferedReader

            String resp = "{\"code\":\"" + responseCode + "\"," +
                    "\"data\":" + response.toString() + "}";     //'data' will contain another, nested, json string

            return resp;     //Return the response with the success code
        } else {
            return "{\"code\":\"" + responseCode + "\"}";
        }

    }
    */
}

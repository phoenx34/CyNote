package com.example.cs309_cynote;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * API used to send information to the server, contains several types of HTTP requests
 *
 * @author Sean Gordon
 * @since 2019-02-17
 */
public class APICalls extends Application {
    private static APICalls mInstance;
    private RequestQueue requestQueue;
    private String response = "No response";

    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized APICalls getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }

    public void addToRequestQueue(Request request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? APICalls.class.getSimpleName() : tag);
        getRequestQueue().add(request);
    }

    public void addToRequestQueue(Request request) {
        // set the default tag
        request.setTag(APICalls.class.getSimpleName());
        getRequestQueue().add(request);
    }

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
     *                         Example in Class 'Login', method 'loginToClassSelection'
     * @param errorListener    Response.ErrorListener, used to handle error responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToClassSelection'
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

        System.out.println("Sending data - ");
        System.out.println(data);
        APICalls.getInstance().addToRequestQueue(jsonObjectRequest, "postRequest");
    }






    /**
     * Volley-based get request, used to send get requests to any formatted urlString and receive
     * data using Response listeners
     *
     * @param urlString        String representing the url to send request to, must include all parameters in the form
     *                         "sampleUrl.com/endpoint"    or    "sampleUrl.com/endpoint?data1=12345&data2=something"
     * @param responseListener Response.Listener, used to handle success responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToClassSelection'
     * @param errorListener    Response.ErrorListener, used to handle error responses.
     *                         Object must be initialized with desired methods before being passed as parameter.
     *                         Example in Class 'Login', method 'loginToClassSelection'
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

        APICalls.getInstance().addToRequestQueue(jsonObjectRequest, "getRequest");
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

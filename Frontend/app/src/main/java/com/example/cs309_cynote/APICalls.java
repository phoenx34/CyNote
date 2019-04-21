package com.example.cs309_cynote;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

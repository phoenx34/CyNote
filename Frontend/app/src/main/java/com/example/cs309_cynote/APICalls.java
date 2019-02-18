package com.example.cs309_cynote;

import android.app.Application;

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
 * @since  2019-02-17
 */
public class APICalls extends Application {
    private static APICalls mInstance;
    private RequestQueue requestQueue;

    public void onCreate(){
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
        request.setTag(tag);
        getRequestQueue().add(request);
    }
    public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }

    /**
     * Simple HTTP post, used to send data to the supplied URL
     *
     * @param urlString Url to send data to
     * @param data      Data to send to the url
     */
    protected void httpPost(String urlString, String data) throws JSONException {
        JSONObject jsonObj = new JSONObject(data);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                urlString,
                jsonObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        //Success Callback
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        //Failure Callback
                    }
                }
        );

        System.out.println("Sending data");
        APICalls.getInstance().addToRequestQueue(jsonObjReq, "postRequest");
    }

    //Deprecated, forgot about volley
    /*protected void httpPost(String urlString, String data){
        OutputStream out = null;

        System.out.println("Reached API");

        try{
            System.out.println("Inside API try");

            URL url = new URL(urlString);   //Create a URL object with the url we were given
            //Set up a connection with the url
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            out = new BufferedOutputStream(connection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(out, "UTF-8")
            );             //use our BufferedOutputStream and set encoding to UTF-8

            writer.write(data);             //Writes the data
            writer.close();

            out.close();

            connection.connect();           //Do some magic
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }*/
}

package com.example.cs309_cynote;



/**
 * API used to send information to the server, contains several types of HTTP requests
 *
 * @author Sean Gordon
 * @since  2019-02-17
 */
public class APICalls {
    public APICalls(){
        //Empty constructor
    }

    /**
     * Simple HTTP post, used to send data to the supplied URL
     *
     * @param urlString Url to send data to
     * @param data      Data to send to the url
     */
    protected void httpPost(String urlString, String data){

        RequestQueue queue = Volley.newRequestQueue(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", response);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                return params;
            }
        };
        queue.add(postRequest);
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

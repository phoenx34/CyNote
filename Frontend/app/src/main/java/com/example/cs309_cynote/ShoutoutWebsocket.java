package com.example.cs309_cynote;

import android.os.AsyncTask;

import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.StatusLine;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class ShoutoutWebsocket extends AsyncTask<String, Void, String> {
    /**
     * The websocket to be used
     */
    private static WebSocket ws = null;

    /**
     * Boolean to control websocket state
     */
    private static boolean active = false;

    /**
     * Object used to return textMessages to ShoutOut
     */
    private static ShoutOut.WebsocketCallbacks callbackObj;

    public ShoutoutWebsocket(){}

    public ShoutoutWebsocket(String server, int timeout, ShoutOut.WebsocketCallbacks callbacks){
        callbackObj = callbacks;

        try{
            ws = buildWS(server, timeout);
        }
        catch(IOException e){
            System.out.println("IOException: ");
            System.out.println(e.getMessage());
        }
        catch(WebSocketException e){
            System.out.println("WebSocketException: ");
            System.out.println(e.getError());
            System.out.println(e.getCause());
        }
    }

    public void connect(){
        try{
            System.out.println("Websocket connecting...");
            ws.connect();
            active = true;
        }
        catch (WebSocketException e){
            System.out.println(e.getError());
        }
        /*
        catch (OpeningHandshakeException e)
        {
            // Status line.
            StatusLine sl = e.getStatusLine();
            System.out.println("=== Status Line ===");
            System.out.format("HTTP Version  = %s\n", sl.getHttpVersion());
            System.out.format("Status Code   = %d\n", sl.getStatusCode());
            System.out.format("Reason Phrase = %s\n", sl.getReasonPhrase());

            // HTTP headers.
            Map<String, List<String>> headers = e.getHeaders();
            System.out.println("=== HTTP Headers ===");
            for (Map.Entry<String, List<String>> entry : headers.entrySet())
            {
                // Header name.
                String name = entry.getKey();

                // Values of the header.
                List<String> values = entry.getValue();

                if (values == null || values.size() == 0)
                {
                    // Print the name only.
                    System.out.println(name);
                    continue;
                }

                for (String value : values)
                {
                    // Print the name and the value.
                    System.out.format("%s: %s\n", name, value);
                }
            }
        }
        */
    }
    public void disconnect(){
        System.out.println("Websocket disconnecting...");
        ws.disconnect();
        active = false;
    }

    public boolean getActive(){
        return active;
    }


    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... params) {
        //Vroom vroom
        connect();

        //Latency could cause problems with this conditional but I'll deal with that later
        while(active){
            //Just hold thread open


            /*
            try {
                System.out.println("EEEEEEE");
                ws.sendText("EEEEEEE");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }*/
        }

        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        //Result is returned from doInBackground
        System.out.println(result);
    }

    //Method can be used with publishProgress(param)
    @Override
    protected void onProgressUpdate(Void... values) {}



    public void sendMessage(String message) throws IllegalStateException{
        if(!active)
            throw new IllegalStateException("Websocket is not connected!");
        System.out.println("Message sent: ");
        System.out.println(message);
        ws.sendText(message);
    }



    /**
     * Build the websocket
     */
    private static WebSocket buildWS(String server, int timeout) throws IOException, WebSocketException{
        WebSocket temp =  new WebSocketFactory()
                .setConnectionTimeout(timeout)
                .createSocket(server)
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) {
                        //Send it on over to ShoutOut
                        callbackObj.onTextMessage(message);
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE);


        return temp;
    }
}

package com.example.cs309_cynote;

import android.os.AsyncTask;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * API used by the shoutout view to communicate with the server
 */
public class ShoutoutWebsocketUpdated extends AsyncTask<String, Void, String> {
    /**
     * The websocket to be used
     */
    private static WebSocketClient ws = null;

    /**
     * Boolean to control websocket state
     */
    private static boolean active = false;

    /**
     * Object used to return textMessages to ShoutOut
     */
    private static ShoutOut.WebsocketCallbacks callbackObj;

    /**
     * Default constructor of ShoutoutWebsocket
     */
    public ShoutoutWebsocketUpdated(){}

    /**
     * Constructor for ShoutoutWebsocket
     * @param url
     * @param callbacks
     */
    public ShoutoutWebsocketUpdated(String url, ShoutOut.WebsocketCallbacks callbacks){
        callbackObj = callbacks;

        Draft[] drafts = {new Draft_6455()};

        try{
            Log.d("Socket:", "Building socket");
            ws = new WebSocketClient(new URI(url), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("Open", "run() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String message) {
                    //Send it on over to ShoutOut
                    callbackObj.onTextMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("Close", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch(URISyntaxException e){
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
    }

    /**
     * Upon selecting 'connect', attempts to connect the websocket to the server
     */
    public void connect(){
        try{
            Log.d("Socket:", "Trying socket");
            ws.connect();
            active = true;
        }
        catch(Exception e){
            Log.d("Exception on connect", e.getMessage().toString());
        }
    }
    //Will be called upon leaving the Shoutout page as well even if socket was never opened just to be sure
    /**
     * Upon selecting 'disconnect' or exiting view, attempts to disconnect any existing websocket from server
     */
    public void disconnect(){
        System.out.println("Websocket disconnecting...");
        ws.close();
        active = false;
    }

    /**
     * Returns the active status of the websocket
     * @return active
     */
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



    //Now working with the Async

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


    /**
     * Sends a message to the server
     * @param message
     * @throws IllegalStateException
     */
    public void sendMessage(String message) throws IllegalStateException{
        if(!active)
            throw new IllegalStateException("Websocket is not connected!");
        System.out.println("Message sent: ");
        System.out.println(message);
        try{
            ws.send(message);
        }
        catch(Exception e){
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }

    }
}

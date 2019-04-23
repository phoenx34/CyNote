package com.example.cs309_cynote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoutOut extends AppCompatActivity{

    /** Interface defined to create callbacks for WebSocket textMessages */
    public interface WebsocketCallbacks {
        void onTextMessage(String message);
    }


    /* An echo server for testing. */
    //private static final String SERVER = "ws://echo.websocket.org";

    /** URL to open websocket on */
    private static final String SERVER = "ws://cs309-sd-7.misc.iastate.edu:8080/webSocket/1";


    /** Create the websocket for use later */
    //ShoutoutWebsocket SOWS = null;
    ShoutoutWebsocketUpdated SOWS = null;

    /** The timeout value in milliseconds for socket connection. */
    private static final int TIMEOUT = 5000;


    /** List of messages to be displayed */
    List<String> messageList;

    /** Name of the selected lecture */
    String lecName;

    /** ID of the selected lecture */
    int LID;


    /** Create an arrayAdapter to help update the listView with new messages */
    ArrayAdapter<String> arrayAdapter = null;

    /** Get reference of widgets from XML layout */
    TextView nameDisp = null;
    ListView lv = null;
    EditText edTxt = null;
    Button send = null;
    Button conn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_out);

        nameDisp = (TextView) findViewById(R.id.lecName);
        lv = (ListView) findViewById(R.id.msg_list_view);
        edTxt = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        conn = (Button) findViewById(R.id.connectButton);



        //Grab the extras passed through intent, received from the server upon login
        Bundle extras = getIntent().getExtras();

        //Initialize the list of messages
        messageList = new ArrayList<String>();
        lecName = "";
        LID = 0;


        //--------------------------------------------------------------------
        // Grabbing data passed through intent and parsing it
        //--------------------------------------------------------------------
        try {
            if (extras.isEmpty())
                throw new Exception("No extras received in ModuleSelection");

            //No need to check for invalid className, in order to get here you need one
            this.lecName = extras.getString("lecName");

            String history = extras.getString("history");
            if (history == null || history.isEmpty())
                throw new Exception("No history received in ShoutOut");

            //No need to check for invalid CID, in order to get here you need one
            this.LID = extras.getInt("LID");


            //Set the lecture name display to the received name
            nameDisp.setText(lecName);


            //Turn received moduleList into an array
            JSONArray arr = new JSONArray(history);
            //For every message in the array, add it to the message list
            for (int i = 0; i < arr.length(); i++) {

                //Grab the next lecture object
                String message = arr.get(i).toString();

                //Add this message to the list
                messageList.add(message);

            }
        }
        catch(JSONException e) {
            System.out.println("JSONException: ");
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }




        //Create an ArrayAdapter to facilitate ListView updates
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, messageList);

        //Set the ListView adapter to the one created above
        lv.setAdapter(arrayAdapter);

        //Select the bottom-most message to scroll to the bottom of a long line of text
        lv.setSelection(arrayAdapter.getCount() - 1);

        //Do not allow these widgets to be used until WebSocket is connected
        send.setClickable(false);
        edTxt.setEnabled(false);
        conn.setText(R.string.connect);







        //SOWS = new ShoutoutWebsocket(SERVER, TIMEOUT, callbacks);
        SOWS = new ShoutoutWebsocketUpdated(SERVER, callbacks);
    }

    /**
     * Implementing custom interface callbacks to work with WebSocket textMessages
     */
    WebsocketCallbacks callbacks = new WebsocketCallbacks() {
        @Override
        public void onTextMessage(String message) {
            System.out.println("Message received: ");
            System.out.println(message);

            addMessage(message);
            arrayAdapter.notifyDataSetChanged();
        }
    };


    public void connectButton(View view){
        //If the websocket is disconnected...
        if(!SOWS.getActive()){
            //Start the engine
            SOWS.execute();

            //Do not allow these widgets to be used until WebSocket is connected
            while(!SOWS.getActive()){}
            send.setClickable(true);
            edTxt.setEnabled(true);
            conn.setText(R.string.disconnect);
        }
        else{
            SOWS.disconnect();

            send.setClickable(false);
            edTxt.setEnabled(false);

            //Wait until disconnected
            while(SOWS.getActive()){}
            conn.setText(R.string.connect);
        }



    }

    public void sendMessage(View view){
        String message = edTxt.getText().toString();
        edTxt.setText("");

        SOWS.sendMessage(message);
        addMessage(message);
    }
    public void addMessage(String message){
        messageList.add(message);
        arrayAdapter.notifyDataSetChanged();
        //Notifies the attached observers that the underlying data has been
        //changed and any View reflecting the data set should refresh itself.
    }




    public void gotoModuleSelection(View view){
        //Make sure websocket is disconnected
        SOWS.disconnect();
        finish();
    }





    private static void thing(){
        /*
        try {
            // Connect to the echo server.
            WebSocket ws = connect();

            // The standard input via BufferedReader.
            BufferedReader in = getInput();

            // A text read from the standard input.
            String text;

            // Read lines until "exit" is entered.
            while ((text = in.readLine()) != null) {
                // If the input string is "exit".
                if (text.equals("exit")) {
                    // Finish this application.
                    break;
                }

                // Send the text to the server.
                ws.sendText(text);
            }

            // Close the WebSocket.
            ws.disconnect();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (WebSocketException e){
            System.out.println(e.getMessage());
        }
        */
    }
}

package com.example.cs309_cynote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShoutOut extends AppCompatActivity{

    /**
     * Interface defined to create callbacks for WebSocket textMessages
     */
    public interface WebsocketCallbacks {
        void onTextMessage(String message);
    }


    /**
     * The echo server on websocket.org.
     */
    //private static final String SERVER = "ws://echo.websocket.org";


    /**
     * URL to open websocket on
     */
    private static final String SERVER = "ws://cs309-sd-7.misc.iastate.edu:8080/webSocket/1";
    /**
     * The timeout value in milliseconds for socket connection.
     *
     *
     */
    private static final int TIMEOUT = 5000;


    /**
     * Create the websocket for use later
     */
    //ShoutoutWebsocket SOWS = null;
    ShoutoutWebsocketUpdated SOWS = null;


    /**
     * Create the list of messages to be displayed
     */
    List<String> messageList = new ArrayList<String>();

    /**
     * Create an arrayAdapter to help update the listView with new messages
     */
    ArrayAdapter<String> arrayAdapter = null;

    /**
     * Get reference of widgets from XML layout
     */
    ListView lv = null;
    EditText edTxt = null;
    Button send = null;
    Button conn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_out);

        lv = (ListView) findViewById(R.id.msg_list_view);
        edTxt = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        conn = (Button) findViewById(R.id.connectButton);

        //Create an ArrayAdapter to facilitate ListView updates
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, messageList);

        //Set the ListView adapter to the one created above
        lv.setAdapter(arrayAdapter);

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

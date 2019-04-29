package com.example.cs309_cynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.objects.Lecture;
import com.example.objects.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Main ShoutOut view, hooked up to the websocket to allow chat between individuals
 */
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

    /** Lecture object passed from ModuleSelection */
    Lecture lecture;

    /** List of messages to be displayed */
    List<String> messages;

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



        //Grab the android intent
        Intent intent = getIntent();



        //Initialize empty array of Messages
        List<Message> messageList = new ArrayList<Message>();


        //Initialize the list for compiled messages
        messages = new ArrayList<String>();

        //To add sample data:
        //messageList.add(new Message("User1", "Message"));


        // Grab Lecture passed through intent
        try{
            Lecture lecture = (Lecture)intent.getSerializableExtra("Lecture");

            if(lecture == null)
                throw new Exception("No Lecture received in ShoutOut");
            //ShoutOut history can be empty
            if(lecture.getShoutoutHistory() == null)
                throw new Exception("No ShoutOut History received in ShoutOut");


            //Set the lecture name display to the received name
            nameDisp.setText(lecture.getLectureName());

            this.lecture = lecture;
            messageList = lecture.getShoutoutHistory();
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }






        //For every Message in lecture, add it to the message list
        for (int i = 0; i < messageList.size(); i++) {
            //Grab the Message object
            Message message = messageList.get(i);

            String messageStr = message.getScreenname() + ": " + message.getMessage();

            //Add this message to the list
            messages.add(messageStr);

        }



        //Create an ArrayAdapter to facilitate ListView updates
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, messages);

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


    /**
     * Upon selecting 'connect' attempts to connect to the server websocket
     * @param view
     */
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

    /**
     * Sends a message to the server
     * @param view
     */
    public void sendMessage(View view){
        String message = edTxt.getText().toString();
        edTxt.setText("");

        SOWS.sendMessage(message);
        addMessage(message);
    }

    /**
     * Adds a message to the list of current viewable messages
     * @param message
     */
    public void addMessage(String message){
        messages.add(message);
        arrayAdapter.notifyDataSetChanged();
        //Notifies the attached observers that the underlying data has been
        //changed and any View reflecting the data set should refresh itself.
    }


    /**
     * Moves intent to ModuleSelection view
     * @param view
     */
    public void gotoModuleSelection(View view){
        //Make sure websocket is disconnected
        SOWS.disconnect();
        finish();
    }

}

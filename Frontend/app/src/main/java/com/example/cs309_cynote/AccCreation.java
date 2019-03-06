package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User creation page used to create new user.
 *
 * @author Sean Gordon
 * @since  2019-02-15
 */
public class AccCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc_creation);

        /*==
        setContentView(R.layout.activity_main);
        goBackToLogin = findViewById(R.id.goBackBut);

        goBackToLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent goBackfromC = new Intent(MainActivity.this, Login.class);
                startActivity(goBackfromC);
                finish();
            }
        });
        */
    }

    public void submitForm(View view) throws JSONException {
        //System.out.println("Submit pressed - ");

        EditText ACUsername = (EditText) findViewById(R.id.ACUsername); //Find the specific EditText
        String username = ACUsername.getText().toString().trim();       //Get string, and trim for trailing whitespace

        EditText ACEmail = (EditText) findViewById(R.id.ACEmail);
        String email = ACEmail.getText().toString().trim();

        EditText ACPassword = (EditText) findViewById(R.id.ACPassword);
        String password = ACPassword.getText().toString().trim();

        Spinner ACDropdown = (Spinner) findViewById(R.id.ACDropdown);
        String accType = ACDropdown.getSelectedItem().toString().trim();

        /*
        //Throw together the json
        String json = "{\"username\":\""+username+"\"," +
                       "\"email\":\""+email+"\", " +
                       "\"password\":\""+password+"\", " +
                       "\"type\":\""+accType+"\"}";
        */

        //Updated JSON to align with server side
        //Server does not currently create new ID, so I'm using a number gen here. NEEDS to be fixed
        Random rand = new Random();
        int randInt = rand.nextInt(100000); //0-99999

        //Getting current account creation time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String creationTime =  sdf.format(cal.getTime());

        String json = "{\"UID\":\""+randInt+"\"," +
                      "\"screenname\":\""+username+"\"," +
                      "\"email\":\""+email+"\", " +
                      "\"password\":\""+password+"\", " +
                      "\"create_time\":\""+creationTime+"\", " +
                      "\"type\":\""+accType+"\"}";

        //Could create a pojo but doesn't really matter

        //String testUrl = "http://ptsv2.com/t/mp8ul-1550461405/post";
        String serverUrl = "http://cs309-sd-7.misc.iastate.edu:8080/users";

        System.out.println("Calling API");
        APICalls api = new APICalls();
        api.httpPost(serverUrl, json);


//        Intent goBackfromC2 = new Intent(MainActivity.this, Login.class);
//        startActivity(goBackfromC2);

    }

    /**
     * Upon clicking "Back" text view, calls this function to change views
     * to the login page.
     *
     * @param view
     */
    public void gotoLogin(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

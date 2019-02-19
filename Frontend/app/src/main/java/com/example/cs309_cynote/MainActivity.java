package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;

/**
 * User creation page used to create new user.
 *
 * @author Sean Gordon
 * @since  2019-02-15
 */
public class MainActivity extends AppCompatActivity {
    private Button goBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //Throw together the json
        String json = "{\"username\":\""+username+"\"," +
                       "\"email\":\""+email+"\", " +
                       "\"password\":\""+password+"\", " +
                       "\"type\":\""+accType+"\"}";
        //Could create a pojo but doesn't really matter

        //String testUrl = "http://ptsv2.com/t/mp8ul-1550461405/post";
        String serverUrl = "http://cs309-sd-7.misc.iastate.edu:8080/users";

        System.out.println("Calling API");
        APICalls api = new APICalls();
        api.httpPost(serverUrl, json);

        Intent loginPagefromC = new Intent(MainActivity.this, Login.class);
        startActivity(loginPagefromC);
    }
}

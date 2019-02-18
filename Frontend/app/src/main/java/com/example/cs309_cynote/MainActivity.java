package com.example.cs309_cynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitForm(View view){
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

        String testUrl = "http://ptsv2.com/t/mp8ul-1550461405/post";

        System.out.println("Calling API");
        APICalls api = new APICalls();
        api.httpPost(testUrl, json);
    }
}

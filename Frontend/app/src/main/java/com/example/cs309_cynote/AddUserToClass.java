package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class AddUserToClass extends AppCompatActivity {

    private EditText editInputClassName;
    private Button cancelFromAddToClass, updateFromAddToClass;
    private int uid, cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_class);

        uid = getIntent().getIntExtra("UID",0);
        editInputClassName = findViewById(R.id.addToClassInput);

    }


    public void findCID(final View view) {

        String url = "http://cs309-sd-7.misc.iastate.edu:8080/";//waiting for backend path /*****
        String inputClassName = editInputClassName.getText().toString();

        if(inputClassName == null || inputClassName.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid class name, try again!", Toast.LENGTH_LONG).show();
            return;
        }

        url += inputClassName;
        APICalls api = new APICalls(getApplicationContext());


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Class ID received");
                System.out.println(response);

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    cid = jsonObj.getInt("CID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AddUserToClass();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Cannot receive class ID");
                System.out.println(error.getMessage());
            }
        };

        try {
            api.volleyGet(url, responseListener, errorListener);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void AddUserToClass() {

        String url = "http://cs309-sd-7.misc.iastate.edu:8080/users_class/";
        url += uid + "/" + cid;
        APICalls api = new APICalls(getApplicationContext());


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent goToClassSelection = new Intent(AddUserToClass.this, ClassSelection.class);
                goToClassSelection.putExtra("UID", uid);
                startActivity(goToClassSelection);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Cannot add user to this class");
                System.out.println(error.getMessage());
            }
        };

        try {
            api.volleyGet(url, responseListener, errorListener);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }


}

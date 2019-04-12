package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;

public class ProfessorMain extends AppCompatActivity {

    private TextView msgShow;
    private Button logoutbuttonfromP;
    String urlAddC, urlAddL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_main);
        msgShow = findViewById(R.id.professorMSG);



        logoutbuttonfromP = findViewById(R.id.prologoutBut);
        logoutbuttonfromP.setOnClickListener(new View.OnClickListener() //click this button to jump to login page
        {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(ProfessorMain.this, Login.class);//jump to login page
                startActivity(loginPage);//start login page
                finish();//kill this page
            }
        });
    }

    public void AddClassT(View view) throws JSONException{

        urlAddC = "http://cs309-sd-7.misc.iastate.edu:8080/classes";
        String json = "{\"cid\":" + 1 + "," +
                "\"name\":" + "\"TestClass1\"}";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Toast.makeText(getApplicationContext(), "Class Add!", Toast.LENGTH_LONG).show();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Add class error");
                System.out.println(error.getMessage());
            }
        };

        System.out.println("Calling API");
        APICalls api = new APICalls(getApplicationContext());
        api.volleyPost(urlAddC, json, responseListener, errorListener);

        Toast.makeText(getApplicationContext(), "Class Add!", Toast.LENGTH_LONG).show();
    }

    public void AddLectureT(View view) throws JSONException{

        urlAddL = "http://cs309-sd-7.misc.iastate.edu:8080/lectures";
        String json = "{\"lid\":" + 1 + "," +
                "\"cid\":" + 1 + "}";

        /*String json = "{\"lid\":" + 1 + "," +
                "\"classEntity\":" + "{\"cid\":" + 1 + "," +
                "\"name\":" + "\"TestClass1\"}" + "}";
        */
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Toast.makeText(getApplicationContext(), "Lecture Add!", Toast.LENGTH_LONG).show();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Add lecture error");
                System.out.println(error.getMessage());
                Toast.makeText(getApplicationContext(), "Lecture Add fail!", Toast.LENGTH_LONG).show();
            }
        };

        System.out.println("Calling API");
        APICalls api = new APICalls(getApplicationContext());
        api.volleyPost(urlAddL, json, responseListener, errorListener);
    }

}

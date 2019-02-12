package com.example.hibiki.simpleaddition;


import com.android.volley.Request.Method;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Requestmsg extends AppCompatActivity {

    private Button sendReq;
    private TextView recMSG;
//    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestmsg);

        final String url = "https://www.google.com";

        sendReq = findViewById(R.id.sendButton);
        recMSG = findViewById(R.id.receiveMSG);

//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.setCancelable(false);

        final RequestQueue q = Volley.newRequestQueue(this);
        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest strReq = new StringRequest(Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        recMSG.setText(response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        recMSG.setText("That didn't work!");
                    }
                });
                q.add(strReq);
            }
        });
    }
//
//    private void showProgressDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideProgressDialog() {
//        if (pDialog.isShowing())
//            pDialog.hide();
//    }
//    private void makeStringReq() {
//        showProgressDialog();
//
//        StringRequest strReq = new StringRequest(Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, response.toString());
//                recMSG.setText(response.toString());
//                hideProgressDialog();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
//            }
//        });
//    }
}

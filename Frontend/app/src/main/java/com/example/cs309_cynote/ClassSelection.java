package com.example.cs309_cynote;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ClassSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_selection);
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

    /**
     * Upon clicking a 'Class' button, calls this function to change views
     * to the Module Selection page.
     *
     * @param view
     */
    public void gotoModuleSelection(View view){
        Intent intent = new Intent(this, ModuleSelection.class);
        startActivity(intent);
    }
}

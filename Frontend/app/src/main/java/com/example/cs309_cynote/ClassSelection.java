package com.example.cs309_cynote;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ClassSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);
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
     * to the Module Selection page. This will send the class name selected.
     *
     * @param view
     */
    public void gotoModuleSelection(View view){
        Intent intent = new Intent(this, ModuleSelection.class);

        //Adding "class name" data to intent
        Button b = (Button)view;
        String buttonText = b.getText().toString();     //Get button text

        intent.putExtra("className", buttonText); //Add that to intent
        //Note: When get-requesting later, will need section id as well for correct modules

        startActivity(intent);
    }
}

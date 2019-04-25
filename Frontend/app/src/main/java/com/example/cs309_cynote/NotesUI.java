package com.example.cs309_cynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Sample view to display a notetaking ui
 */
public class NotesUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
    }

    /**
     * Moves intent to ModuleSelection view
     * @param view
     */
    public void gotoModuleSelection(View view){
        finish();
        /*
        Intent intent = new Intent(this, ModuleSelection.class);
        startActivity(intent);
        */
    }
}

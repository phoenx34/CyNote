package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ShoutOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_out);
    }

    public void gotoModuleSelection(View view){
        finish();
        /*
        Intent intent = new Intent(this, ModuleSelection.class);
        startActivity(intent);
        */
    }
}

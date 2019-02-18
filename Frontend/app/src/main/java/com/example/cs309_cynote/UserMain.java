package com.example.cs309_cynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserMain extends AppCompatActivity {

    private TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        textShow = findViewById(R.id.textMSG);
        String textString = textShow.getText().toString();
    }
}

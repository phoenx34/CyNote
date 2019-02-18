package com.example.cs309_cynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private EditText emailIn;
    private EditText passwordIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginB = findViewById(R.id.loginBot);
        emailIn = findViewById(R.id.emailInput);
        passwordIn = findViewById(R.id.passwordInput);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInString = emailIn.getText().toString();
                String passwordInString = passwordIn.getText().toString();
                

            }
        });


    }
}

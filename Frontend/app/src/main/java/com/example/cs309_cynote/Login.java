package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    private EditText emailIn;
    private EditText passwordIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginB = findViewById(R.id.loginBot);
        emailIn = findViewById(R.id.emailInput);
        passwordIn = findViewById(R.id.passwordInput);

        loginB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailInString = emailIn.getText().toString();//Get email String from input
                String passwordInString = passwordIn.getText().toString();//Get password String from input
                boolean loginCondition = false;//initial login condition to false

                if(isEmailValid(emailInString))//check if email is valid
                {
                    //use APIcalls to send json
                    loginCondition = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid email, try again!", Toast.LENGTH_LONG).show();//display massage that email is invalid.
                }

                if(loginCondition)//check if login successful
                {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();//display massage that login is successful
                    Intent userPage = new Intent(Login.this, UserMain.class);//jump to user main page
                    startActivity(userPage);//start user main page
                    finish();//kill this page
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login fails, email or password is wrong!", Toast.LENGTH_SHORT).show();//return massage
                }
            }
        });
    }

    /**
     * Boolean method used to check if the email is valid by checking if it contains @.
     * @param emailText email input String.
     * @return boolean, true if it contains @.
     */
    private boolean isEmailValid(String emailText)
    {
        if (emailText.contains("@"))
            return true;
        else
            return false;
    }

    /**
     * Upon clicking "New user?" text view, calls this function to change views
     * to the account creation page.
     *
     * @param view
     */
    public void gotoAccCreation(View view){
        Intent intent = new Intent(this, AccCreation.class);
        startActivity(intent);
    }

    public void gotoClassSelection(View view){
        Intent intent = new Intent(this, ClassSelection.class);
        startActivity(intent);
    }
}

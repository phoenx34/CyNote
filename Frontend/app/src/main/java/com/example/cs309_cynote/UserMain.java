package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * User main page used to display user main information after login successfully.
 */
public class UserMain extends AppCompatActivity {

    private TextView textShow;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        textShow = findViewById(R.id.textMSG);
        String textString = textShow.getText().toString();

        logoutButton = findViewById(R.id.userLogoutBut);
        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginPage = new Intent(UserMain.this, Login.class);
                startActivity(loginPage);
                finish();
            }
        });
    }
}

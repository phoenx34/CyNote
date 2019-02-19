package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfessorMain extends AppCompatActivity {

    private TextView msgShow;
    private Button logoutbuttonfromP;

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
}

package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Testing class for the student view
 */
public class StudentMain extends AppCompatActivity {
    private Button studentLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        studentLogout = findViewById(R.id.logoutfromStudent);
        studentLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginFromStudent = new Intent(StudentMain.this, Login.class);
                startActivity(loginFromStudent);
                finish();
            }
        });
    }
}

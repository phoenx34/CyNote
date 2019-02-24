package com.example.cs309_cynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaMain extends AppCompatActivity {
    private Button TaLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ta_main);

        TaLogout = findViewById(R.id.logoutfromStudent);
        TaLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginFromTa = new Intent(TaMain.this, Login.class);
                startActivity(loginFromTa);
                finish();
            }
        });
    }
}

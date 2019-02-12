package com.example.hibiki.simpleaddition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText firNum, secNum;
    private TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firNum = findViewById(R.id.firstNum);
        secNum = findViewById(R.id.secondNum);
        answer = findViewById(R.id.answerNum);

        Button cal = findViewById(R.id.btnCal);//link button
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNum1 = firNum.getText().toString();
                String inputNum2 = secNum.getText().toString();
                int num1 = Integer.valueOf(inputNum1).intValue();
                int num2 = Integer.valueOf(inputNum2).intValue();
                num1 = num1 + num2;
                inputNum1 = String.valueOf(num1);
                answer.setText(inputNum1);
                Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        Button send = findViewById(R.id.requestB);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reqPage = new Intent(MainActivity.this, Requestmsg.class);
                startActivity(reqPage);
            }
        });

    }
}

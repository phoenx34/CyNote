package com.example.qr_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void qrScannerBtn(View v)
    {
        Intent i = new Intent(MainActivity.this, QR_Scanner.class);
        startActivity(i);
        Toast.makeText(this,"QR Scanner Started",Toast.LENGTH_SHORT).show();
    }



    public void qrGeneratorBtn(View v)
    {
        Intent ie = new Intent(MainActivity.this,QR_Generator.class);
        startActivity(ie);
        Toast.makeText(this, "QR Generator",Toast.LENGTH_SHORT).show();
    }


}

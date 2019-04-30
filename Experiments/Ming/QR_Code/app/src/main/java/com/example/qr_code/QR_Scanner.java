package com.example.qr_code;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Scanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText r;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__scanner);
        r = (EditText)findViewById(R.id.resultEditText);
    }


    public void scanBtn(View v)
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"Scan has been cancelled or stopped",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

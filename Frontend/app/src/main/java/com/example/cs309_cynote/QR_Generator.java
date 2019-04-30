package com.example.cs309_cynote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR_Generator extends AppCompatActivity {
    EditText textg;
    ImageView imageg;
    String grtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__generator);

        textg = (EditText)findViewById(R.id.editTextInput);
        imageg = (ImageView)findViewById(R.id.imagev);
        Bundle extras = getIntent().getExtras();
        int CID = extras.getInt("CID");
        textg.setText(CID + "");
    }

    public void genBtn(View v)
    {
        grtext = textg.getText().toString().trim();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try
        {
            BitMatrix bitMatrix = multiFormatWriter.encode(grtext, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageg.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }



    }



}

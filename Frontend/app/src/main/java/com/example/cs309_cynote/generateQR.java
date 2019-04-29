package com.example.cs309_cynote;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;


public class generateQR extends AppCompatActivity {

    // The input string to convert into QR Code
    private TextView inputString;

    // The output QR code
    private ImageView outputQRcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        // Get the input string
        inputString = findViewById(R.id.result_scan_text);

        // Get the image
        outputQRcode = findViewById(R.id.qrcodeImage);
    }




    public void generate(View view) {
        String input = inputString.getText().toString();
        if(input == null || input.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Put the class Id first!", Toast.LENGTH_LONG).show();
            return;
        }


        Bitmap qrBitmap = generateBitmap(input,400, 400);
        outputQRcode.setImageBitmap(qrBitmap);
    }




    private Bitmap generateBitmap(String content,int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }



}

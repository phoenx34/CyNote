package com.example.cs309_cynote;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class UploadNote extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_note);
    }


    public void uploadFile(){
        //TODO actually get a file here
        File file = saveBitmapToFile(new File(""));
        sendFile(file);

    }

    public void sendFile(File file){
        //TODO update with correct URL
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";    //Server-side url to send file


        APICalls api = new APICalls(getApplicationContext());

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO do something maybe?
            }
        };

        //Set up listener for error case
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error: ");
                System.out.println(error.getMessage());
            }
        };


        //Uses the APICalls generic volley get request
        try{
            //TODO enable volley post for file
            //api.volleyPost(url, file, responseListener, errorListener);
        }
        catch (Exception e){
            System.out.println("VolleyPost error: ");
            System.out.println(e.getMessage());
        }
    }


    /**
     * Save an image to a file to post to server
     *
     * @param file  The image to compress and  save
     * @return
     */
    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            //Create a new image file and then return it.
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }



    public void gotoModuleSelection(View view){
        finish();
    }
}

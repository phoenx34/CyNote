package com.example.cs309_cynote;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/*  Built using
    https://www.bugsee.com/blog/android-file-upload-doesnt-have-to-be-hard/
 */

public class FileUploader  extends FirebaseMessagingService {
    public FileUploader(){}

    /**
     * Save an image to a file to post to server
     *
     * @param file  The image to compress and  save
     * @return
     */
    public File convertBitmapToFile(File file)
    {
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

    //TODO update this with the complete list of permissions (able to pass it as a parameter)
    public void checkPermissions(){
        /*
        if (ContextCompat.checkSelfPermission(FileUploader, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }
        */
    }
}

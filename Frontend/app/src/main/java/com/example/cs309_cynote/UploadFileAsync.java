package com.example.cs309_cynote;


//Gotten from
//https://stackoverflow.com/a/25398566
//new UploadFileAsync().execute("");

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.koushikdutta.ion.Ion;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.cs309_cynote.FileSelector.getContext;

/**
 * Testing class for uploading a file in a background thread
 */
public class UploadFileAsync extends AsyncTask<String, Void, String> {
    String UPLOAD_URL = "http://cs309-sd-7.misc.iastate.edu:8080/";

    @Override
    protected String doInBackground(String... params) {
        String sourceFileUri = params[0];
        String file_name = "";

        File file = new File(sourceFileUri);//create path from uri
        final String[] split = file.getPath().split(":");//split the path.
        sourceFileUri = split[1];//assign it to a string(your choice).


        System.out.println("EEEEEEEEEE");
        System.out.println(sourceFileUri);
        Uri uri = Uri.parse(sourceFileUri);

        Ion.with(getContext())
                .load("https://koush.clockworkmod.com/test/echo")
                //.uploadProgressBar(uploadProgressBar)
                //.setMultipartParameter("goop", "noop")
                //.setMultipartFile("archive", "application/zip", new File("/sdcard/filename.zip"))
                .setMultipartFile("image", "multipart/form-data", new File(sourceFileUri))
                .asJsonObject()
                .setCallback(null);

        try {
/*
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "xxxxxxxxxx";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1024 * 1024;
            //todo change URL as per client ( MOST IMPORTANT )
            URL url = new URL(UPLOAD_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs &amp; Outputs.
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST.
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=xxxxxxxxxx");
            FileInputStream fileInputStream;
            DataOutputStream outputStream;
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"reference\""+ lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes("my_refrence_text");
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadFile\";filename=\"" + uri.getLastPathSegment() +"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            fileInputStream = new FileInputStream(uri.getPath());
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            String result = null;
            System.out.println(serverResponseCode);
            if (serverResponseCode == 200) {
                StringBuilder s_buffer = new StringBuilder();
                InputStream is = new BufferedInputStream(connection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    s_buffer.append(inputLine);
                }
                result = s_buffer.toString();
            }
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            if (result != null) {
                Log.d("result_for upload", result);
                file_name = result;
            }
            /*
            //String sourceFileUri = "/mnt/sdcard/abc.png";
            //String sourceFileUri = Environment.getExternalStorageDirectory().getPath()+"/abc.png";

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            //File sourceFile = new File(sourceFileUri);
            File sourceFile = new File(uri.getPath());

            if (sourceFile.isFile()) {




                /*

                try {
                    //String upLoadServerUri = "http://website.com/abc.php?";
                    String upLoadServerUri = UPLOAD_URL;

                    // open a URL connection to the Servlet
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE",
                            "multipart/form-data");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("bill", sourceFileUri);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                            + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);

                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode == 200) {

                        // messageText.setText(msg);
                        //Toast.makeText(ctx, "File Upload Complete.",
                        //      Toast.LENGTH_SHORT).show();

                        // recursiveDelete(mDirectory1);

                    }

                    // close the streams //
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {

                    // dialog.dismiss();
                    System.out.println(e.getMessage());
                    e.printStackTrace();

                }
                */
                // dialog.dismiss();

        } // End else block


         catch (Exception ex) {
            // dialog.dismiss();

            ex.printStackTrace();
        }
        return "Executed";
    }



    public void Fuck(){
        System.out.print("FUCK");
    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
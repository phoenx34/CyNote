package com.example.cs309_cynote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Space;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ClassSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);


        //the layout on which you are working
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relLayout);

        //The dummy space used for easier horizontal alignment here
        Space dummy = (Space) findViewById(R.id.dummy);


        List<ClassObj> classes = new ArrayList<ClassObj>();



        /*  Server response will come in this form:
            {
                "classes" : [
                {
                    "cid":"1",
                    "name":"ComS 311"
                },
                {
                    "cid":"2",
                    "name":"ComS 309"
                }
                ]
            }
        */

        //Grab the extras passed through intent, received from the server upon login
        Bundle extras = getIntent().getExtras();
        String data = null;


        try{

            //----Ensuring the data actually exists----\\

            //If the extras does not exist, big oof
            if(extras == null)
                throw new Exception("No data received");

            //Try pulling data from extras
            data = extras.getString("data");
            //If data does not exist, big oof
            if(data == null || data.trim().length() == 0) {
                throw new Exception("No data received");
            }
            //-----------------------------------------\\


            //Turn received JSON into an object
            JSONObject jsonObject = new JSONObject(data);

            //Grab the 'classes' array from the object
            JSONArray arr = jsonObject.getJSONArray("classes");
            for (int i = 0; i < arr.length(); i++) {

                //Turn each index in the array into another object
                String className = arr.get(i).toString();
                JSONObject set = new JSONObject(className);

                //Grab the cid and the className from that object
                int cid = set.getInt("cid");
                String name = set.getString("name");
                //And create a ClassObj with them
                ClassObj classObj = new ClassObj(cid, name);

                //Add them to the array to be used with class button creation
                classes.add(classObj);
            }



        }
        catch(JSONException e) {
            System.out.println("JSONException: ");
            System.out.println(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }





        //I'm using this to generate a random color, won't be needed later
        Random rand = new Random();

        //For every class received
        for(int i = 0; i < classes.size(); i++){

            Button btn = new Button(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


            //These are constant for each button
            btn.setId(ViewCompat.generateViewId());
            btn.setWidth(dpToPx(165));
            btn.setHeight(dpToPx(117));
            btn.setOnClickListener(new View.OnClickListener() {                //Weird roundabout stuff needed here
                @Override
                public void onClick(View v) {
                    gotoModuleSelection(v);
                }
            });


            // Now things start to change ------------------------------------------

            //Generating random color, will be included in data package from server detailing preferences later
            //int color = 100000 + rand.nextInt(900000);        //Can generate dark colors

            //Generating a light color -
            String R = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF
            String G = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF
            String B = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF

            btn.setBackgroundColor(Color.parseColor("#" + R+G+B));
            //Grab the current ClassObj
            ClassObj currentClass = classes.get(i);
            //Then change the name using that ClassObj
            btn.setText(currentClass.getName());
            //TODO pass currentClass.getCid() somewhere for something


            //Every set of two buttons gets lower down
            int topMarg = (int)(i/2) * dpToPx(141);
            //Each button is always this far from the center, either left or right
            int lrMargin = dpToPx(12);

            //Every button is always below dummy
            params.addRule(RelativeLayout.BELOW, dummy.getId());


            //Switch case to determine the current next position
            switch(i%2) {     //2 possible locations, left or right of center
                case 0:
                    params.setMargins(0, topMarg, lrMargin, 0);
                    params.addRule(RelativeLayout.LEFT_OF, dummy.getId());
                    layout.addView(btn, params);
                    break;
                case 1:
                    params.setMargins(lrMargin, topMarg, 0, 0);
                    params.addRule(RelativeLayout.RIGHT_OF, dummy.getId());
                    layout.addView(btn, params);
                    break;
            }


            if(btn.getParent() != null) {
                ((ViewGroup)btn.getParent()).removeView(btn);
            }
            layout.addView(btn, params);

        }
    }

    /**
     * This method is used to convert the specified number of dp to the appropriate
     * number of pixels.
     *
     * @param dp
     * @return pixels
     */
    public int dpToPx(int dp){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        int pixels = (int) Math.ceil(dp * logicalDensity);
        return pixels;
    }
    /*
    <Button

        android:id="@+id/button1"
        android:layout_width="165dp"
        android:layout_height="117dp"
        android:layout_marginTop="22dp"
        android:layout_marginEnd="12dp"
        android:layout_marginRight="12dp"
        android:background="#4d4dff"
        android:text="@string/sample_class1"
        app:layout_constraintEnd_toStartOf="@+id/guideline"
        app:layout_constraintTop_toTopOf="@+id/view2"
        android:onClick="gotoModuleSelection"/>
     */

    /**
     * Upon clicking "Back" text view, calls this function to change views
     * to the login page.
     *
     * @param view
     */
    public void gotoLogin(View view){
        finish();
        /*
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        */
    }

    /**
     * Upon clicking a 'Class' button, calls this function to change views
     * to the Module Selection page. This will send the class name selected.
     *
     * @param view
     */
    public void gotoModuleSelection(View view){
        Intent intent = new Intent(this, ModuleSelection.class);

        //Adding "class name" data to intent
        Button b = (Button)view;
        String buttonText = b.getText().toString();     //Get button text

        intent.putExtra("className", buttonText); //Add that to intent
        //Note: When get-requesting later, will need section id as well for correct modules

        startActivity(intent);
    }

    public void goToAddNewClass(View view){
        Intent intent = new Intent(this, AddUserToClass.class);
        int uid = 4;//need received
        intent.putExtra("UID", uid);
        startActivity(intent);

    }


    public void addClass(){

    }


    public void gotoFileUploader(View view){
        Intent intent = new Intent(this, FileSelector.class);
        startActivity(intent);
    }
}

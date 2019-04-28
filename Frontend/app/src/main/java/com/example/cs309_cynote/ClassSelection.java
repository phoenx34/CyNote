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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.objects.ClEnt;
import com.example.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Hub page where user can select classes they are enrolled in, add more, or change settings.
 * Upon moving to this page, application will attempt to grab a list of classes from 'intent',
 * and if successful will display any classes as a series of buttons.
 * If unsuccessful will display a page with no buttons.
 */
public class ClassSelection extends AppCompatActivity {
    private User user = null;
    private List<ClEnt> classes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);


        //The layout on which we are working
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relLayout);

        //The dummy space used for easier horizontal alignment here
        Space dummy = (Space) findViewById(R.id.dummy);



        //Grab the android intent
        Intent intent = getIntent();


        //Initialize empty list of classes
        classes = new ArrayList<ClEnt>();

        //To add sample data:
        //classes.add(new ClEnt(1, "SampleClass"));


        // Grab User passed through intent
        try{
            //Grab the received User
            User user = (User)intent.getSerializableExtra("User");
            if(user == null || user.getClassList() == null || user.getClassList().isEmpty())
                throw new Exception("No classList received in ClassSelection");


            this.user = user;
            this.classes = user.getClassList();
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }






        //For every class received
        for(int i = 0; i < classes.size(); i++){

            Button btn = new Button(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            //These are constant for each button
            btn.setId(ViewCompat.generateViewId());
            btn.setWidth(dpToPx(165, metrics));
            btn.setHeight(dpToPx(117, metrics));
            btn.setOnClickListener(new View.OnClickListener() {                //Weird roundabout stuff needed here
                @Override
                public void onClick(View v) {
                    gotoModuleSelection(v);
                }
            });


            // Now things start to change ------------------------------------------

            //Create a random color for each button before preferences are implemented
            String randColor = randColor();
            int color = Color.parseColor(randColor);
            btn.setBackgroundColor(color);

            //Grab the current ClassObj
            ClEnt currentClass = classes.get(i);
            //Then change the name using that ClassObj
            btn.setText(currentClass.getClassName());


            //Every set of two buttons gets lower down
            int topMarg = (int)(i/2) * dpToPx(141, metrics);
            //Each button is always this far from the center, either left or right
            int lrMargin = dpToPx(12, metrics);

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





        //To add some flair if the user has no classes...
        addFlair(classes.size(), false);

    }

    /**
     * This method generates a random color for the class buttons.
     * In use because preferences are not implemented (adds some flair).
     *
     * @return color
     */
    public String randColor(){
      //Generating random color, will be included in data package from server detailing preferences later
      //int color = 100000 + rand.nextInt(900000);        //Can generate dark colors

        //I'm using this to generate a random color, won't be needed later
        Random rand = new Random();


        //Generating a light color -
      String R = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF
      String G = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF
      String B = Integer.toHexString(153 + rand.nextInt(102));     //Hex in range of 99 - FF

        String color = "#" + R+G+B;

      return color;
    }

    /**
     * This method is used to convert the specified number of dp to the appropriate
     * number of pixels based on the current screen DisplayMetrics.
     *
     * @param dp Specified number of dp
     * @param metrics Current screens display metrics
     * @return pixels
     */
    public int dpToPx(int dp, DisplayMetrics metrics){

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
     * To add some flair if the user has no classes
     *
     * @param classListSize
     */
    public void addFlair(int classListSize, boolean useEmoji){
        //Grab the "You have no classes" textview and make it initially invisible
        TextView noClass = findViewById(R.id.noClassMessage);
        noClass.setVisibility(View.GONE);

        //If there are no classes
        if(classListSize == 0){

            //Switch between using an ellipsis or an emoji
            String thinkingEmoji = "...";
            if(useEmoji)
                thinkingEmoji = " " + new String(Character.toChars(0x1F914));

            String noClassMessage = "Hmm, you don't appear to\n" +
                    "be in any classes"+thinkingEmoji+"\n\n"+
                    "Try adding some below";

            //Set the new message and make visible
            noClass.setText(noClassMessage);
            noClass.setVisibility(View.VISIBLE);

        }
    }



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
     * Upon clicking a 'Class' button, calls this function to get request for the list
     * of modules and changes views to the Module Selection page.
     *
     * @param view
     */

    public void gotoModuleSelection(final View view){
        Button b = (Button)view;
        String className = b.getText().toString();     //Get className from button text

        //Check through the list of classes to find the ClEnt that matches this className
        ClEnt clazz = new ClEnt();
        for(ClEnt claz : classes){
            if(claz.getClassName() == className)
                clazz = claz;
        }


        //Define callbacks for call to getModuleList
        APICallbacks moduleCallbacks = new APICallbacks<ClEnt>() {
            @Override
            public void onResponse(ClEnt clEnt) {
                //Now that this ClEnt is completed, move to ModuleSelection
                Intent intent = new Intent(view.getContext(), ModuleSelection.class);
                intent.putExtra("Class", clEnt);         //Add User to ClassSelection intent
                startActivity(intent);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                System.out.println(error.getMessage());
            }
        };

        //Make the call
        APICalls apiCalls = new APICalls(this.getApplicationContext());
        apiCalls.getModuleList(clazz, moduleCallbacks);


        /*

         */
    }




    /**
     * Upon clicking a 'Class' button, calls this function to change views
     * to the Module Selection page. This will send the class name selected.
     *
     * @param view
     */
    /*
    public void gotoModuleSelection(View view){
        Intent intent = new Intent(this, ModuleSelection.class);

        //Adding "class name" data to intent
        Button b = (Button)view;
        String buttonText = b.getText().toString();     //Get button text

        intent.putExtra("className", buttonText); //Add that to intent
        //Note: When get-requesting later, will need section id as well for correct modules

        startActivity(intent);
    }
    */

    /**
     * Upon selecting the add new class button, this method will change the currently active
     * page to the add new class page.
     * @param view
     */
    public void goToAddNewClass(View view){
        Intent intent = new Intent(this, AddUserToClass.class);
        intent.putExtra("UID", user.getUID());
        startActivity(intent);

    }

    /**
     * Purely for testing purposes
     * @param view
     */
    public void gotoFileUploader(View view){
        Intent intent = new Intent(this, FileSelector.class);
        startActivity(intent);
    }



}




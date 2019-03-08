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

        /*
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        */




        //Sample Data
        List<String> classNames = new ArrayList<String>();
        classNames.add("ComS 309");
        classNames.add("CprE 381");
        classNames.add("CprE 412");


        //I'm using this to generate a random color, won't be needed later
        Random rand = new Random();


        for(int i = 0; i < classNames.size(); i++){

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
            btn.setText(classNames.get(i));


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
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
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
}

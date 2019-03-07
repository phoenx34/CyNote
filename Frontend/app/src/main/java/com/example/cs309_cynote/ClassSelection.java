package com.example.cs309_cynote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class ClassSelection extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);


        //the layout on which you are working
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relLayout);

        //set the properties for button
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        btnTag.setWidth(165);
        btnTag.setHeight(117);
        btnTag.setBackgroundColor(Color.parseColor("#4d4dff"));
        btnTag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this.gotoModuleSelection(v);
            }
        });
        btnTag.setText("@string/sample_class1");

        btnTag.setText("Button");
        //btnTag.setId(some_random_id);

        //add button to the layout
        layout.addView(btnTag);

        /*
            android:id="@+id/button1"
            android:layout_width="165dp"
            android:layout_height="117dp"
            android:background="#4d4dff"
            android:onClick="gotoModuleSelection"
            android:text="@string/sample_class1"

            android:layout_marginLeft="8dp"
            android:layout_marginTop="24dp"
            android:layout_below="@+id/horizDivider"
         */

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

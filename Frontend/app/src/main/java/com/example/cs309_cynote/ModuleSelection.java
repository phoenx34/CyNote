package com.example.cs309_cynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hub for module/lecure selection, accessed by selecting a class. Shows individual lectures and their components.
 */
public class ModuleSelection extends AppCompatActivity {

    ExpandableListView expListView;                                     //Object that represents the list of modules
    com.example.expandablelistview.ExpandableListAdapter listAdapter;   //Helper object to handle data for expListView

    List<String> lectureList;                       //List that contains all the lecture headers and references their children
    List<Integer> idList;                           //Parallel list to hold lectureIDs (don't want to mess with this list api)
    HashMap<String, List<String>> lectureChildren;  //Map of children for every lecture

    String className;   //Name of the selected class
    int CID;            //CID of the selected class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);



        //Get the listView
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        //To use sample data:
        boolean sampleData = false;


        /*  Server response will come in this form:
            [
                {
                    "id":1,
                    "shoutout_history":[],
                    "clEnt":null
                },
                {
                    "id":2,
                    "shoutout_history":[],
                    "clEnt":null
                }
            ]
        */

        //Grab the extras passed through intent, received from the server upon login
        Bundle extras = getIntent().getExtras();


        lectureList = new ArrayList<String>();
        idList = new ArrayList<Integer>();
        lectureChildren = new HashMap<String, List<String>>();


        //--------------------------------------------------------------------
        // Grabbing data passed through intent and parsing it
        //--------------------------------------------------------------------
        try {
            //To use sample data
            if(sampleData)
                throw new Exception("Using sample data, skipping try/catch");


            if (extras.isEmpty())
                throw new Exception("No extras received in ModuleSelection");


            //No need to check for invalid className, in order to get here you need one
            this.className = extras.getString("className");

            String moduleList = extras.getString("moduleList");
            if (moduleList == null || moduleList.isEmpty())
                throw new Exception("No moduleList received in ModuleSelection");

            //No need to check for invalid CID, in order to get here you need one
            this.CID = extras.getInt("CID");


            //Turn received moduleList into an array
            JSONArray arr = new JSONArray(moduleList);
            //For every lecture object in the array (they are sent backwards, so iterate backwards)
            for (int i = arr.length()-1; i >= 0;  i--) {

                //Grab the next lecture object
                String lectureStr = arr.get(i).toString();
                JSONObject lecture = new JSONObject(lectureStr);

                //Grab the lecture ID
                int LID = lecture.getInt("id");

                //Add this lecture to the main list
                String lecString = "Lecture " + LID;
                lectureList.add(lecString);
                idList.add(LID);


                //Adding child data
                List<String> childData = new ArrayList<String>();
                //Every lecture has a Shoutout, so this can be added unconditionally
                childData.add("Shoutout");

                //If there are any notes included, we would add them here
                //However, that is not set up on back end yet

                //Add this to the main child list
                lectureChildren.put(lecString, childData); // Header, Child data
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

        if(sampleData)
            prepareListData();


        listAdapter = new com.example.expandablelistview.ExpandableListAdapter(this, lectureList, lectureChildren);
        //Setting list adapter
        expListView.setAdapter(listAdapter);


        //Update the "class_name" TextView with new String
        TextView classNameField = findViewById(R.id.textView9);
        classNameField.setText(className);



        // -------------------------------------------------------------
        //                          Listeners
        // -------------------------------------------------------------

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*//Printing that selected view was expanded
                Toast.makeText(getApplicationContext(),
                        lectureList.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
                        */
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*//Printing that selected view was collapsed
                Toast.makeText(getApplicationContext(),
                        lectureList.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
                        */

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Get the selected child String, contained in HashMap<String, List<String>>
                String data = lectureChildren.get(lectureList.get(groupPosition)).get(childPosition);


                if(data.equals("Shoutout")) {
                    String lecName = lectureList.get(groupPosition);
                    int lid = idList.get(groupPosition);

                    gotoShoutout(v, lid, lecName);
                }
                //There is no system set up for notes so we just go to a generic notes page
                else
                    gotoNotes(v);

                /*
                // T ODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                */
                return false;
            }
        });

        // -------------------------------------------------------------
        //                          End Listeners
        // -------------------------------------------------------------
    }


    //Lol
    /*
    public void fuckMockito(){
        prepareListData();
    }
    */

    /**
     * This method is for testing. Later, when front and back end are
     * hooked up, this will use a get request to populate the list.
     */
    private void prepareListData() {
        lectureChildren = new HashMap<String, List<String>>();
        lectureList = new ArrayList<String>();

        // Adding child data
        lectureList.add("Lecture 4");
        lectureList.add("Lecture 5");
        lectureList.add("Lecture 6");

        // Adding child data
        List<String> lec4 = new ArrayList<String>();
        lec4.add("Shoutout");
        lec4.add("Notes(Collaborative)");

        List<String> lec5 = new ArrayList<String>();
        lec5.add("Shoutout");
        lec5.add("Notes(Collaborative)");
        lec5.add("Notes(StudentID)");

        List<String> lec6 = new ArrayList<String>();
        lec6.add("Shoutout");
        lec6.add("Notes(Collaborative)");

        lectureChildren.put(lectureList.get(0), lec4); // Header, Child data
        lectureChildren.put(lectureList.get(1), lec5);
        lectureChildren.put(lectureList.get(2), lec6);
    }


    public void goToAddNewLecture(View view){

    }

    /**
     * Upon clicking "Back" button, calls this function to change views
     * to the Class Selection page.
     *
     * @param view
     */
    public void gotoClassSelection(View view){
        finish();
        /*
        Intent intent = new Intent(this, ClassSelection.class);
        startActivity(intent);
        */
    }

    /**
     * Upon clicking "Shoutout" child, calls this function to change views
     * to the Shoutout page.
     *
     * @param view
     */
    public void gotoShoutout(View view, int lid, String lecName){

        //Get the ShoutOut history for this lecture and then transition to ShoutOut
        APICalls apiCalls = new APICalls(this.getApplicationContext());
        apiCalls.getShoutOutHistory(view, lecName, lid);
    }

    /**
     * Upon clicking "Notes" child, calls this function to change views
     * to the Notes page.
     *
     * @param view
     */
    public void gotoNotes(View view){
        Intent intent = new Intent(this, NotesUI.class);
        startActivity(intent);
    }
}

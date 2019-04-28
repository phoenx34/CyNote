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

import com.example.objects.ClEnt;
import com.example.objects.Lecture;

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

    private List<String> dropdownHeaders;                       //List that contains all the lecture headers and references their children
    private List<Integer> idList;                           //Parallel list to hold lectureIDs (don't want to mess with this list api)
    private HashMap<String, List<String>> dropdownMap;  //Map of children for every lecture

    private ClEnt clEnt;                    //Class selected to get here
    private List<Lecture> lectures;         //List of lectures for that class

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


        /*
        Toast.makeText(applicationContext, "There was an error retrieving the lecture list", Toast.LENGTH_LONG).show();
                    System.out.println("Get moduleList error");
                    System.out.println(error.getMessage());
         */

        //Grab the android intent
        Intent intent = getIntent();


        //Initialize empty array of lectures
        lectures = new ArrayList<Lecture>();

        //To add sample data:
        //lectures.add(new Lecture(1, "Lecture 1", new ArrayList<String>()));


        // Grab ClEnt passed through intent
        try{
            //Grab the received User
            ClEnt clEnt = (ClEnt)intent.getSerializableExtra("Class");
            if(clEnt == null || clEnt.getLectureList() == null || clEnt.getLectureList().isEmpty())
                throw new Exception("No dropdownHeaders received in ModuleSelection");


            this.clEnt = clEnt;
            this.lectures = clEnt.getLectureList();
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }





        //Initialize various lists
        dropdownMap = new HashMap<String, List<String>>();
        dropdownHeaders = new ArrayList<String>();

        //For every lecture received
        for(int i = 0; i < lectures.size(); i++){
            //Grab the Lecture and its name from the list
            Lecture lecture = lectures.get(i);
            String lectureName = lecture.getLectureName();

            //Create a new dropdown header with lectureName
            dropdownHeaders.add(lectureName);

            //Create the dropdown menu for this lecture header
            List<String> dropdown = new ArrayList<String>();
            dropdown.add("Shoutout");
            dropdown.add("Notes(Collaborative)");
            //TODO get the list of static notes and add them here in order of rating

            //Map this lecture header to its dropdown menu
            dropdownMap.put(lectureName, dropdown);     //Header, Child data
        }


        listAdapter = new com.example.expandablelistview.ExpandableListAdapter(this, dropdownHeaders, dropdownMap);
        //Setting list adapter
        expListView.setAdapter(listAdapter);


        //Update the "class_name" TextView with new String
        TextView classNameField = findViewById(R.id.textView9);
        classNameField.setText(clEnt.getClassName());



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
                        dropdownHeaders.get(groupPosition) + " Expanded",
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
                        dropdownHeaders.get(groupPosition) + " Collapsed",
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
                String data = dropdownMap.get(dropdownHeaders.get(groupPosition)).get(childPosition);


                if(data.equals("Shoutout")) {
                    String lecName = dropdownHeaders.get(groupPosition);

                    //Check through the list of lectures to find the Lecture that matches this lectureName
                    Lecture lecture = new Lecture();
                    for(Lecture lec : lectures){
                        if(lec.getLectureName() == lecName)
                            lecture = lec;
                    }

                    //TODO pass the entire lecture, going to bed tho
                    gotoShoutout(v, lecture.getLID(), lecName);
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
        dropdownMap = new HashMap<String, List<String>>();
        dropdownHeaders = new ArrayList<String>();

        // Adding child data
        dropdownHeaders.add("Lecture 4");
        dropdownHeaders.add("Lecture 5");
        dropdownHeaders.add("Lecture 6");

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

        dropdownMap.put(dropdownHeaders.get(0), lec4); // Header, Child data
        dropdownMap.put(dropdownHeaders.get(1), lec5);
        dropdownMap.put(dropdownHeaders.get(2), lec6);
    }

    /**
     * Upon "Add New Lecture" button, calls this function to change views
     * to the AddNewLecture page.
     * @param view
     */
    public void goToAddNewLecture(View view){
        //TODO pass the whole clEnt, going to bed tho
        Intent intent = new Intent(this, AddNewLecture.class);
        intent.putExtra("CID", clEnt.getCID());
        intent.putExtra("className", clEnt.getClassName());
        startActivity(intent);

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

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

import com.android.volley.VolleyError;
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
                    "name":"Leture 15"
                },
                {
                    "id":2,
                    "name":"Leture 15"
                }
            ]
        */


        //Grab the android intent
        Intent intent = getIntent();


        //Initialize empty array of lectures
        lectures = new ArrayList<Lecture>();

        //To add sample data:
        //lectures.add(new Lecture(1, "Lecture 1", new ArrayList<String>()));


        // Grab ClEnt passed through intent
        try{
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



        //Build the dropdown list with the lectures variable


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //If all is well and the data exists...
        if(requestCode == AddNewLecture.RESULT_OK && data != null){
            //Grab the returned (updated) ClEnt and update this class's
            ClEnt clEnt = (ClEnt)data.getSerializableExtra("Class");
            this.clEnt = clEnt;
            this.lectures = clEnt.getLectureList();
        }
    }


    private void buildLectureList(){
        //Initialize various lists
        dropdownMap = new HashMap<String, List<String>>();
        dropdownHeaders = new ArrayList<String>();


        //For every lecture in the lecture list
        for(int i = 0; i < lectures.size(); i++){
            //Grab the Lecture and its name from the list
            Lecture lecture = lectures.get(i);
            String lectureName = lecture.getLectureName();

            //Create a new dropdown header with lectureName
            dropdownHeaders.add(lectureName);

            //Create the dropdown menu for this lecture header
            List<String> dropdown = new ArrayList<String>();

            //Adding default selections
            dropdown.add("ShoutOut");
            dropdown.add("Collaborative Notes");

            //Adding static notes
            //TODO get the list of static notes and add them here in order of rating

            //Give the option to upload a static note
            dropdown.add("Add a note +");

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
                // Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*//Printing that selected view was expanded
                Toast.makeText(getApplicationContext(), dropdownHeaders.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show(); */
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*//Printing that selected view was collapsed
                Toast.makeText(getApplicationContext(), dropdownHeaders.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show(); */
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Get the selected child String, contained in HashMap<String, List<String>>
                String data = dropdownMap.get(dropdownHeaders.get(groupPosition)).get(childPosition);

                //Grab the name of the lecture selected
                String lecName = dropdownHeaders.get(groupPosition);

                //Check through the list of lectures to find the Lecture that matches this lectureName
                Lecture lecture = new Lecture();
                for(Lecture lec : lectures){
                    if(lec.getLectureName() == lecName)
                        lecture = lec;
                }

                System.out.println("Dropdown option selected: " + data);

                //Switch case for dropdown option selection
                switch(data){
                    case "ShoutOut":
                        gotoShoutout(v, lecture);
                        break;

                    case "Collaborative Notes":
                        //TODO Direct the user to the EtherPad room
                        break;

                    case "Add a note +":
                        inflateAddNote();
                        break;

                    default:
                        //TODO The user has selected a static note. Find it and do something lol
                        break;
                }


                return false;
            }
        });

        // -------------------------------------------------------------
        //                          End Listeners
        // -------------------------------------------------------------
    }



    /**
     * Upon "Add New Lecture" button, calls this function to change views
     * to the AddNewLecture page.
     * @param view
     */
    public void goToAddNewLecture(View view){
        Intent intent = new Intent(this, AddNewLecture.class);
        intent.putExtra("Class", clEnt);
        startActivityForResult(intent, 0);

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
    public void gotoShoutout(final View view, Lecture lecture){


        //Define callbacks for call to ShoutOut History
        APICallbacks moduleCallbacks = new APICallbacks<Lecture>() {
            @Override
            public void onResponse(Lecture lecture) {
                //Now that this Lecture is completed, move to ShoutOut
                Intent intent = new Intent(view.getContext(), ShoutOut.class);
                intent.putExtra("Lecture", lecture);         //Add User to ClassSelection intent
                startActivity(intent);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), "There was an error retrieving the ShoutOut history", Toast.LENGTH_LONG).show();
                System.out.println("There was an error retrieving the ShoutOut history");
                System.out.println(error.getMessage());
            }
        };

        //Make the call
        APICalls apiCalls = new APICalls(this.getApplicationContext());
        apiCalls.getShoutOutHistory(lecture, moduleCallbacks);
    }

    private void inflateAddNote(){
        AddNoteModal addNoteModal = new AddNoteModal();
        addNoteModal.show(getSupportFragmentManager(), "missiles");
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

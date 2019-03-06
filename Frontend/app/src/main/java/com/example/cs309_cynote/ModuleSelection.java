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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleSelection extends AppCompatActivity {

    com.example.expandablelistview.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);



        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new com.example.expandablelistview.ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);












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
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Get the selected child String, contained in HashMap<String, List<String>>
                String data = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                //Purely for testing purposes, this code is super case specific.
                //In future builds, this will use server requests and this 'ID'
                //to grab the correct Shoutout data
                if(data.equals("Shoutout"))
                    gotoShoutout(v);
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


    /**
     * This method is for testing. Later, when front and back end are
     * hooked up, this will use a get request to populate the list.
     */
    private void prepareListData() {
        listDataChild = new HashMap<String, List<String>>();
        listDataHeader = new ArrayList<String>();

        // Adding child data
        listDataHeader.add("Lecture 4");
        listDataHeader.add("Lecture 5");
        listDataHeader.add("Lecture 6");

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

        listDataChild.put(listDataHeader.get(0), lec4); // Header, Child data
        listDataChild.put(listDataHeader.get(1), lec5);
        listDataChild.put(listDataHeader.get(2), lec6);
    }

    /**
     * Upon clicking "Back" button, calls this function to change views
     * to the Class Selection page.
     *
     * @param view
     */
    public void gotoClassSelection(View view){
        Intent intent = new Intent(this, ClassSelection.class);
        startActivity(intent);
    }

    /**
     * Upon clicking "Shoutout" child, calls this function to change views
     * to the Shoutout page.
     *
     * @param view
     */
    public void gotoShoutout(View view){
        Intent intent = new Intent(this, ShoutOut.class);
        startActivity(intent);
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

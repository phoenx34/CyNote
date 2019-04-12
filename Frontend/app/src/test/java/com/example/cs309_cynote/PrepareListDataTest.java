
package com.example.cs309_cynote;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PrepareListDataTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void PrepareListDataWorks() {
        //The sorter we are trying to test
        ModuleSelection moduleSelection = new ModuleSelection();


        //Call method, and ensure data is correct
        moduleSelection.fuckMockito();
        HashMap<String, List<String>> listData = moduleSelection.listDataChild;


        //Create test data
        //--------------------------------------------------------------------------------
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        ArrayList<String> listDataHeader = new ArrayList<String>();

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
        //--------------------------------------------------------------------------------


        Assert.assertEquals(listDataChild,listData);
    }

}

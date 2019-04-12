package com.example.cs309_cynote;


import android.util.DisplayMetrics;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Sean Gordon
 */
public class MockitoTestBySGordon {


    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void DptoPxTest_returnsValidInt() {
        //The class we are trying to test
        ClassSelection classSelection = new ClassSelection();

        //Classes the above class uses
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.density = 5;

        int color = classSelection.dpToPx(40, metrics);

        Assert.assertNotEquals(0, color);
    }

    
    @Test
    public void randColorTest_returnsValidInt() {
        //The method we are trying to test
        ClassSelection classSelection = new ClassSelection();

        String color = classSelection.randColor();

        Assert.assertNotEquals("#000000", color);
    }


    @Test
    public void PrepareListDataWorks() {
        //The method we are trying to test
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
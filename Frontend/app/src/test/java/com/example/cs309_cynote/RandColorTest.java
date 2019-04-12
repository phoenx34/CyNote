package com.example.cs309_cynote;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class RandColorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    //This method depends on specific device properties
    @Test
    public void randColorTest_returnsValidInt() {
        //The sorter we are trying to test
        ClassSelection classSelection = new ClassSelection();

        int color = classSelection.randColor();

        Assert.assertNotEquals(0, color);
    }

}

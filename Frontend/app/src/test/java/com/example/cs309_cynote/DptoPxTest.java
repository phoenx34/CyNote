
package com.example.cs309_cynote;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class DptoPxTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void DptoPxTest_returnsValidInt() {
        //The sorter we are trying to test
        ClassSelection classSelection = new ClassSelection();

        int color = classSelection.dpToPx(40);

        Assert.assertNotEquals(0, color);
    }

}

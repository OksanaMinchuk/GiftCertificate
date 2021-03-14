package com.epam.esm.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TagTest {

    private Tag tag;

    @Before
    public void initTag() {
        tag = new Tag();
    }

    @After
    public void clearTag() {
        tag = null;
    }

    @Test
    public void setName() {
        String expected = "travel";
        tag.setName("travel");
        String actual = tag.getName();
        assertEquals(expected, actual);
    }
}

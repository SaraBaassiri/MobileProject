package com.example.homsi.psf;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Daniel on 11/9/2017.
 */
public class EndTimeTest {
    @Test
    public void EndTimeisNotValid() throws Exception {
        EndTime end = new EndTime();
        assertFalse(end.isValid("111111"));
        assertFalse(end.isValid("abcd"));
        assertFalse(end.isValid("2222:22"));
        assertFalse(end.isValid("b3:b1"));
        assertFalse(end.isValid("3w:2w"));
        assertFalse(end.isValid("####"));
        assertFalse(end.isValid(""));
    }
    @Test
    public void EndTimeisValid() throws Exception {
        EndTime end = new EndTime();
        assertTrue(end.isValid("3:28"));
        assertTrue(end.isValid("4:20"));
        assertTrue(end.isValid("10:10"));
        assertTrue(end.isValid("12:40"));
    }

}
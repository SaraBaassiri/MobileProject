package com.example.homsi.psf;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Daniel on 11/9/2017.
 */
public class TimeActivityTest {
    @Test
    public void TimeisNotValid() throws Exception {
        TimeActivity time = new TimeActivity();
        assertFalse(time.isValid("asdyasdhkjasd"));
        assertFalse(time.isValid("a12312312"));
        assertFalse(time.isValid("bbb33:bbb22"));
        assertFalse(time.isValid("B0:B0"));
        assertFalse(time.isValid("Q0:Q0"));
        assertFalse(time.isValid(""));

    }
    @Test
    public void TimeisValid() throws Exception {
        EndTime end = new EndTime();
        assertTrue(end.isValid("1:11"));
        assertTrue(end.isValid("2:22"));
        assertTrue(end.isValid("3:33"));
        assertTrue(end.isValid("4:44"));
    }

}
package com.example.homsi.psf;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel on 11/9/2017.
 */
public class CalendarActivityTest {
    @Test
    public void notValidStartDate() throws Exception {
        CalendarActivity cal = new CalendarActivity();
        assertFalse(cal.isValidStartDate("aaaa"));
        assertFalse(cal.isValidStartDate("0200000"));
        assertFalse(cal.isValidStartDate("100/100/100"));
        assertFalse(cal.isValidStartDate("300/30/30"));

    }
    @Test
    public void isValidStartDate() throws Exception {
        CalendarActivity cal = new CalendarActivity();
        assertTrue(cal.isValidStartDate("2/08/1995"));
        assertTrue(cal.isValidStartDate("10/10/2017"));
        assertTrue(cal.isValidStartDate("10/30/2018"));
        assertTrue(cal.isValidStartDate("12/12/2017"));
    }
}
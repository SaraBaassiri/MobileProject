package com.example.homsi.psf;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Daniel on 11/9/2017.
 */
public class EndDateTest {
    @Test
    public void isNotValidEndDate() throws Exception {
        EndDate end = new EndDate();
        assertFalse(end.isValidEndDate("abcd"));
        assertFalse(end.isValidEndDate("111/22/3333"));
        assertFalse(end.isValidEndDate("100/1/1"));
        assertFalse(end.isValidEndDate("@31S"));
        assertFalse(end.isValidEndDate("@@@@"));
    }
    @Test
    public void isValidEndDate() throws Exception {
        EndDate end = new EndDate();
        assertTrue(end.isValidEndDate("02/18/2018"));
        assertTrue(end.isValidEndDate("10/10/2017"));
        assertTrue(end.isValidEndDate("12/12/2017"));
        assertTrue(end.isValidEndDate("1/30/2018"));
        assertTrue(end.isValidEndDate("02/08/1995"));
    }

    @Test
    public void notValidEndDates() throws Exception {
        EndDate end = new EndDate();
        assertFalse(end.validEnd("10/10/2017", "10/09/2017"));
        assertFalse(end.validEnd("10/09/2017", "1/10/2017"));
        assertFalse(end.validEnd("10/09/2018", "1/10/2015"));

    }
    @Test
    public void validEndDates() throws Exception {
        EndDate end = new EndDate();
        assertTrue(end.validEnd("10/10/2017", "10/10/2017"));
        assertTrue(end.validEnd("10/10/2017", "10/30/2017"));
        assertTrue(end.validEnd("10/10/2017", "1/10/2018"));

    }

}
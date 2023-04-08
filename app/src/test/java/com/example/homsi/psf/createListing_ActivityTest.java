package com.example.homsi.psf;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Created by Daniel on 11/9/2017.
 */

public class createListing_ActivityTest {


    @Test
    public void checkPrice0orLower() throws Exception {
        createListing_Activity test = new createListing_Activity();
        assertFalse(test.validPrice(0.0));
        assertFalse(test.validPrice(-10.0));
    }
    @Test
    public void checkPriceHigherthan0() throws Exception {
        createListing_Activity test = new createListing_Activity();
        assertTrue(test.validPrice(100.00));
        assertTrue(test.validPrice(200.00));
        assertTrue(test.validPrice(50.25));
    }
    @Test
    public void checkPriceCannotBeHigherThanBound1() throws Exception{
        createListing_Activity test = new createListing_Activity();
        assertTrue(test.PriceBound(100.00));

    }

    @Test
    public void checkPriceCannotBeHigherThanBound2() throws Exception{
        createListing_Activity test = new createListing_Activity();
        assertFalse(test.PriceBound(1000000));

    }




    @Test
    public void FalseAddress() throws Exception {
       createListing_Activity act = new createListing_Activity();
        assertFalse(act.validAddress("test"));
        assertFalse(act.validAddress("10"));
        assertFalse(act.validAddress("x"));
        assertFalse(act.validAddress(""));
    }
    @Test
    public void ValidAddress() throws Exception {
        createListing_Activity act = new createListing_Activity();
        assertTrue(act.validAddress("422 Test Way, San Jose, CA 95111"));
        assertTrue(act.validAddress("508 Software Way, San Jose, CA 95123"));
        assertTrue(act.validAddress("583 Egg Way, San Jose, CA95011"));

    }


}
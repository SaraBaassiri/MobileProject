package com.example.homsi.psf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Andy on 11/9/2017.
 */
public class RegisterActivityTest {
    RegisterActivity login = new RegisterActivity();
    Boolean output;

    @Test
    public void EmailTest() throws Exception{
        String input = "Andyhon@gmail.com";
        Boolean expected = true;

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void EmailTest2() throws Exception{
        String input = "Andyhon@gmailcom";
        Boolean expected = false;


        output = login.isEmailValid(input);

        assertFalse(login.isEmailValid(input));

    }

    @Test
    public void EmailTest3() throws Exception{
        String input = "Andyhongmail.com";
        Boolean expected = false;

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void EmailTest4() throws Exception{
        String input = "";
        Boolean expected = false;


        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }
    @Test
    public void EmailTest5() throws Exception{
        String input = "Andyhon1111111111111111111111111111111111111111111111111111@gmail.com";
        Boolean expected = true;

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest() throws Exception{
        String input = "password";
        Boolean expected = true;


        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest2() throws Exception{
        String input = "p";
        Boolean expected = false;


        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest3() throws Exception{
        String input = "pAs";
        Boolean expected = false;

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest4() throws Exception{
        String input = "@&#@;";
        Boolean expected = true;


        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest5() throws Exception{
        String input = "p@$$word";
        Boolean expected = true;

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }


    @Test
    public void NameTest2() throws Exception{
        String input = "";
        Boolean expected = false;

        output = login.isNameValid(input);

        assertEquals(expected, output);
    }



    @Test
    public void NameTest() throws Exception{
        String input = "";
        Boolean expected = false;

        output = login.isNameValid(input);

        assertEquals(expected, output);
    }

    @Test
    public void NameTest3() throws Exception{
        String input = "N@mE";
        Boolean expected = false;

        output = login.isNameValid(input);

        assertEquals(expected, output);
    }
}
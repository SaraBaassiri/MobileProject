package com.example.homsi.psf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andy on 11/9/2017.
 */
public class LoginActivityTest  {
    @Test
    public void EmailTest() throws Exception{
        String input = "Andyhon@gmail.com";
        Boolean output;
        Boolean expected = true;

        LoginActivity login = new LoginActivity();

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }
    @Test
    public void EmailTest2() throws Exception{
        String input = "Andyhon@gmailcom";
        Boolean output;
        Boolean expected = false;

        LoginActivity login = new LoginActivity();

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void EmailTest3() throws Exception{
        String input = "Andyhongmail.com";
        Boolean output;
        Boolean expected = false;

        LoginActivity login = new LoginActivity();

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void EmailTest4() throws Exception{
        String input = "";
        Boolean output;
        Boolean expected = false;

        LoginActivity login = new LoginActivity();

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }
    @Test
    public void EmailTest5() throws Exception{
        String input = "Andyhon1111111111111111111111111111111111111111111111111111@gmail.com";
        Boolean output;
        Boolean expected = true;

        LoginActivity login = new LoginActivity();

        output = login.isEmailValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest() throws Exception{
        String input = "password";
        Boolean output;
        Boolean expected = true;

        LoginActivity login = new LoginActivity();

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest2() throws Exception{
        String input = "p";
        Boolean output;
        Boolean expected = false;

        LoginActivity login = new LoginActivity();

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest3() throws Exception{
        String input = "pAs";
        Boolean output;
        Boolean expected = false;

        LoginActivity login = new LoginActivity();

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest4() throws Exception{
        String input = "@&#@;";
        Boolean output;
        Boolean expected = true;

        LoginActivity login = new LoginActivity();

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

    @Test
    public void PasswordTest5() throws Exception{
        String input = "p@$$word";
        Boolean output;
        Boolean expected = true;

        LoginActivity login = new LoginActivity();

        output = login.isPasswordValid(input);

        assertEquals(expected, output);

    }

}
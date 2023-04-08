package com.example.homsi.psf;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.google.firebase.auth.FirebaseUser;

public class UserActivityTest {
    @Test
    public void UserTest() throws Exception {
        FirebaseUser input;
        input = null;
        Boolean output;
        Boolean expected = false;

        UserActivity user = new UserActivity();

        output = user.userIsValid(input);

        assertEquals(expected, output);

    }
}

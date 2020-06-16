package Password;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static Password.PasswordValidator.*;

public class TestPasswordValidator {

    @Test
    public void TestPasswordValidator() throws IOException {

        // Password length (8 =< X <= 32)
        Assert.assertFalse(ValidatePasswordLength("1234567")); // Password length < 8
        Assert.assertTrue(ValidatePasswordLength("12345678")); // Password length = 8
        Assert.assertTrue(ValidatePasswordLength("123456789")); // Password length > 8 & < 32
        Assert.assertTrue(ValidatePasswordLength("12345678123456781234567812345678")); // Password length = 32
        Assert.assertFalse(ValidatePasswordLength("123456781234567812345678123456781")); // Password length > 32

        // Password capital letter [A-Z]
        Assert.assertTrue(ValidatePasswordCapitalLetter("Password"));
        Assert.assertTrue(ValidatePasswordCapitalLetter("passworD"));
        Assert.assertTrue(ValidatePasswordCapitalLetter("passWord"));
        Assert.assertFalse(ValidatePasswordCapitalLetter("password"));

        // Password number [0-9]
        Assert.assertTrue(ValidatePasswordNumber("1password"));
        Assert.assertTrue(ValidatePasswordNumber("password2"));
        Assert.assertTrue(ValidatePasswordNumber("pass3word"));
        Assert.assertFalse(ValidatePasswordNumber("password"));

        // Password special character [@#$%]
        Assert.assertTrue(ValidatePasswordSpecialCharacter("@password"));
        Assert.assertTrue(ValidatePasswordSpecialCharacter("password#"));
        Assert.assertTrue(ValidatePasswordSpecialCharacter("pass$word"));
        Assert.assertTrue(ValidatePasswordSpecialCharacter("%password%"));
        Assert.assertFalse(ValidatePasswordSpecialCharacter("password"));

        // Dictionary
        String filePath = "src/main/resources/Top10kCommonPasswords.txt";

        Assert.assertTrue(ValidatePasswordDictionary(filePath, "password")); // Dictionary password
        Assert.assertFalse(ValidatePasswordDictionary(filePath, "P4$$W0rD")); // Non dictionary password

    }
}


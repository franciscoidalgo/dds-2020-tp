import org.junit.Test;
import junit.framework.Assert;

@SuppressWarnings("static-access")
public class TestPasswordPolicyValidator
{
    @Test
    public void testLength()
    {
        PasswordPolicyValidator validator = PasswordPolicyValidator.buildValidator( false,
                                                                        false,
                                                                        false,
                                                                        8,
                                                                        32);
        // MaxLength <= 32
        Assert.assertTrue(validator.validatePassword("passwordpasswordpasswordpassword"));  // 32
        Assert.assertFalse(validator.validatePassword("passwordpasswordpasswordpasswordpassword")); // >32
        // MinLength >= 8
        Assert.assertTrue(validator.validatePassword("password")); // 8
        Assert.assertFalse(validator.validatePassword("pass")); // <8
    }

    @Test
    public void testForceNumeric()
    {
        PasswordPolicyValidator validator = PasswordPolicyValidator.buildValidator( false,
                                                                        false,
                                                                        true,
                                                                        8,
                                                                        32);
        // Numbers ? Yes
        Assert.assertTrue(validator.validatePassword("password123"));
        Assert.assertTrue(validator.validatePassword("123password"));
        Assert.assertTrue(validator.validatePassword("pass123word"));
        // Numbers ? No
        Assert.assertFalse(validator.validatePassword("password"));
    }

    @Test
    public void testForceCapitalLetter()
    {
        PasswordPolicyValidator validator = PasswordPolicyValidator.buildValidator( false,
                                                                        true,
                                                                        false,
                                                                        8,
                                                                        32);
        // Capitals ? Yes
        Assert.assertTrue(validator.validatePassword("Password"));
        Assert.assertTrue(validator.validatePassword("PassworD"));
        Assert.assertTrue(validator.validatePassword("PassWord"));
        // Capitals ? No
        Assert.assertFalse(validator.validatePassword("password"));
    }

    @Test
    public void testForceSpecialCharacter()
    {
        PasswordPolicyValidator validator = PasswordPolicyValidator.buildValidator( true,
                                                                        false,
                                                                        false,
                                                                        8,
                                                                        32);
        // Special Char ? Yes
        Assert.assertTrue(validator.validatePassword("$password"));
        Assert.assertTrue(validator.validatePassword("password$"));
        Assert.assertTrue(validator.validatePassword("pass$word"));
        // Special Char ? No
        Assert.assertFalse(validator.validatePassword("password"));
    }

}


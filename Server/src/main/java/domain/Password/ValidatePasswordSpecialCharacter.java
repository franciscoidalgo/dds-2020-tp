
package domain.Password;

import java.util.regex.Pattern;

public class ValidatePasswordSpecialCharacter implements PasswordCriteria {

    @Override
    public Boolean validatePassword (String password) {
        return Pattern.matches(".*[@#$%!_*?¿-].*", password);

    }
}

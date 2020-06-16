package Password;

import sun.font.TrueTypeFont;

public class ValidatePasswordLength {

    private static int longMin = 8;
    private static int longMax = 32;

    public Boolean ValidatePasswordLength (String password) {

        return true;
        // return password.length() >= longMin && password.length() <= longMax;

    }

}

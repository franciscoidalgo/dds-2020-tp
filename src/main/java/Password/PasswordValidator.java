package Password;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static int minLength = 8;
    private static int maxLength = 32;


    static boolean ValidatePasswordLength(String password) {

        return password.length() >= minLength && password.length() <= maxLength;

    }

    static boolean ValidatePasswordCapitalLetter(String password) {

        return Pattern.matches(".*[A-Z].*", password);

    }

    static boolean ValidatePasswordNumber(String password) {

        return Pattern.matches(".*[0-9].*", password);

    }

    static boolean ValidatePasswordSpecialCharacter(String password) {

        return Pattern.matches(".*[@#$%].*", password);

    }

    static boolean ValidatePasswordDictionary(String filePath, String password) throws IOException {

        BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
        ArrayList<String> listOfLines = new ArrayList<>();

        String line = bufReader.readLine();
        while (line != null) {
            listOfLines.add(line);
            line = bufReader.readLine();
        }

        bufReader.close();

        return listOfLines.contains(password);
    }
}



package domain.Password;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ValidatePasswordDictionary implements PasswordCriteria {

    String filePath = "src/main/resources/Top10kCommonPasswords.txt";

    @Override
    public Boolean validatePassword (String password) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
        ArrayList<String> listOfLines = new ArrayList<>();

        String line = bufReader.readLine();
        while (line != null) {
            listOfLines.add(line);
            line = bufReader.readLine();
        }

        bufReader.close();

        return !listOfLines.contains(password);
    }
}

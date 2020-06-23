package Password;

import java.io.IOException;
import java.util.ArrayList;

public class ValidatePassword {

    public ArrayList<PasswordCriteria> passwordCriteria;

    public Boolean validatePassword(String password) {

            return this.passwordCriteria.stream()
                    .map(criteria -> {
                        try {
                            return criteria.validatePassword(password);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        return null;
                    })
                    .reduce((aBoolean, aBoolean2) -> Boolean.logicalAnd(aBoolean, aBoolean2)).get();
    }
}





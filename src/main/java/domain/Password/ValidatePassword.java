package domain.Password;

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
                    .reduce(Boolean::logicalAnd).get();
    }
}





package Password;

import java.io.IOException;

public interface PasswordCriteria {

    public Boolean validatePassword (String password) throws IOException;

}

package Password;

import java.lang.*;

public interface PasswordValidator {

    public boolean ValidatePasswordLength (String password);
    public boolean ValidatePasswordCompositionRule (String password);
    public boolean ValidatePasswordCommon (String password);

}

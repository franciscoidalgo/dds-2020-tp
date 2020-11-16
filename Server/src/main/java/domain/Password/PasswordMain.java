package domain.Password;

import java.io.IOException;
import java.util.ArrayList;

public class PasswordMain {

    public static void main(String[] args) throws IOException {

        ValidatePassword validatePassword = new ValidatePassword();
        ValidatePasswordLength validatePasswordLength = new ValidatePasswordLength();
        ValidatePasswordNumber validatePasswordNumber = new ValidatePasswordNumber();
        ValidatePasswordCapitalLetter validatePasswordCapitalLetter = new ValidatePasswordCapitalLetter();
        ValidatePasswordSpecialCharacter validatePasswordSpecialCharacter = new ValidatePasswordSpecialCharacter();
        ValidatePasswordDictionary validatePasswordDictionary = new ValidatePasswordDictionary();

        ArrayList<PasswordCriteria> passwordCriteria = new ArrayList<>();

        passwordCriteria.add(validatePasswordLength);
        passwordCriteria.add(validatePasswordNumber);
        passwordCriteria.add(validatePasswordCapitalLetter);
        passwordCriteria.add(validatePasswordSpecialCharacter);
        passwordCriteria.add(validatePasswordDictionary);

        validatePassword.setPasswordCriteria(passwordCriteria);
        String password = "*_aroco20!-?";
        //String password = "P4ssw0rd$";

        if(validatePassword.validatePassword(password)){
            System.out.println("domain.Password length... OK");
        }else{
            System.out.println("domain.Password length... NOT OK");
        }


        // Test password length
        if (validatePasswordLength.validatePassword(password))
            System.out.println("domain.Password length... OK");
        else
            System.out.println("domain.Password length... NOT OK");

        // Test password number
        if (validatePasswordNumber.validatePassword(password))
            System.out.println("domain.Password number... OK");
        else
            System.out.println("domain.Password number... NOT OK");

        // Test password capital letter
        if (validatePasswordCapitalLetter.validatePassword(password))
            System.out.println("domain.Password capital letter... OK");
        else
            System.out.println("domain.Password capital letter... NOT OK");

        // Test password special character
        if (validatePasswordSpecialCharacter.validatePassword(password))
            System.out.println("domain.Password special character... OK");
        else
            System.out.println("domain.Password special character... NOT OK");

        // Test password capital letter
        if (validatePasswordDictionary.validatePassword(password))
            System.out.println("domain.Password dictionary... OK");
        else
            System.out.println("domain.Password dictionary... NOT OK");


    }
}

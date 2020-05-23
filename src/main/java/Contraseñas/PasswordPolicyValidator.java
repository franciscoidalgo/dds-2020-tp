package Contraseñas;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicyValidator {
    private static String pattern = null;
    private String blackListPath;

    StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

    Pattern p;
    Matcher m;
    File file;

    public PasswordPolicyValidator(String blackListPath,
                                   boolean forceSpecialChar,
                                   boolean forceCapitalLetter,
                                   boolean forceNumber,
                                   int minLength,
                                   int maxLength)
    {
        this.blackListPath = blackListPath;
        file = new File(this.blackListPath);

        if (forceSpecialChar)   patternBuilder.append("(?=.*[@#$%])"); //Forzamos caracteres especiales
        if (forceCapitalLetter) patternBuilder.append("(?=.*[A-Z])"); //Forzamos letras mayúsculas
        if (forceNumber)        patternBuilder.append("(?=.*[0-9])"); //Forzamos números

        patternBuilder.append(".{" + minLength + "," + maxLength + "})");
        pattern = patternBuilder.toString();
    }

    public void validatePassword(final String password)
    {
        boolean passwordOK = true;
        if(!verifyPolicies(password)){
            System.out.println("PASSWORD NO OK. No cumple políticas de seguridad.");
            passwordOK = false;
        } else try{
            if(isInBlackList(password)) System.out.println("PASSWORD NO OK. Password existe en diccionario.");
            passwordOK = false;
        }catch (FileNotFoundException e){
            System.out.print("La ruta al archivo de la blacklist era inválida...");
        }
        if(passwordOK) System.out.println("PASSWORD OK!!");
    }

    private boolean verifyPolicies(final String password){
        p = p.compile(pattern);
        m = p.matcher(password);
        return m.matches();

        /*
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
         */
    }

    private boolean isInBlackList(String password) throws FileNotFoundException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw e;
        }

        String tmpPasswordReader= null;
        while (true)
        {
            try {
                if (!((tmpPasswordReader = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(tmpPasswordReader.equals(password))
                return true;
        }
        return false;
    }
}


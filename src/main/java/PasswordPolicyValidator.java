package src.main.java;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicyValidator {
    private static String pattern = null;
    private String blackListPath = null;

    StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

    Pattern p;
    Matcher m;

    public PasswordPolicyValidator(String blackListPath,
                                   boolean forceSpecialChar,
                                   boolean forceCapitalLetter,
                                   boolean forceNumber,
                                   int minLength,
                                   int maxLength)
    {
        this.blackListPath = blackListPath;

        if (forceSpecialChar)   patternBuilder.append("(?=.*[@#$%])"); //Forzamos caracteres especiales
        if (forceCapitalLetter) patternBuilder.append("(?=.*[A-Z])"); //Forzamos letras mayúsculas
        if (forceNumber)        patternBuilder.append("(?=.*[0-9])"); //Forzamos números

        patternBuilder.append(".{" + minLength + "," + maxLength + "})");
        pattern = patternBuilder.toString();
    }

    public void validatePassword(final String password)
    {
        if(!verifyPolicies(password)) System.out.println("PASSWORD NO OK. No cumple políticas de seguridad.");
        if(isInBlackList(password)) System.out.println("PASSWORD NO OK. Password existe en diccionario.");
        System.out.println("PASSWORD OK!!");
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

    private boolean isInBlackList(String st2) {
        File file = new File(this.blackListPath);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st1= null;
        while (true)
        {
            try {
                if (!((st1 = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(st1.equals(st2))
                return true;
        }
        return false;
    }
}


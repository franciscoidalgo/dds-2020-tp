
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicyValidator {
    private static PasswordPolicyValidator INSTANCE = new PasswordPolicyValidator();
    private static String pattern = null;

    private PasswordPolicyValidator()
    {
        //
    }

    public static PasswordPolicyValidator buildValidator(boolean forceSpecialChar,
                                                         boolean forceCapitalLetter,
                                                         boolean forceNumber,
                                                         int minLength,
                                                         int maxLength)
    {
        StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

        if (forceSpecialChar)
        {
            patternBuilder.append("(?=.*[@#$%])");
        }

        if (forceCapitalLetter)
        {
            patternBuilder.append("(?=.*[A-Z])");
        }

        if (forceNumber)
        {
              patternBuilder.append("(?=.*[0-9])");
        }

        patternBuilder.append(".{" + minLength + "," + maxLength + "})");
        pattern = patternBuilder.toString();

        return INSTANCE;
    }

    public static boolean validatePassword(final String password)
    {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isInBlackList(String st2) {
        File file = new File("src/main/top10k.txt");

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


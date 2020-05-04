import java.util.Scanner;

public class Main
{
    public static void main (String[] args)
    {
        boolean forceSpecialChar = true;
        boolean forceCapitalLetter = true;
        boolean forceNumber = true;
        int minLength = 8;
        int maxLength = 32;

        PasswordPolicyValidator.buildValidator(forceSpecialChar,
                forceCapitalLetter,
                forceNumber,
                minLength,
                maxLength);

        System.out.print("Password: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();

/*        if (PasswordPolicyValidator.isInBlackList(password))
            System.out.println("Password... NOT OK: Found on dictionary");
        else
            System.out.println("Password... OK: Not found on dictionary");
*/
        if (!PasswordPolicyValidator.validatePassword(password))
        {
            System.out.println("PASSWORD NO OK. No cumple políticas de seguridad.");
            return;
        }
        if (PasswordPolicyValidator.isInBlackList(password))
        {
            System.out.println("PASSWORD NO OK. Password existe en diccionario.");
            return;
        }
        System.out.println("PASSWORD OK!!");
        return;
    }
}
package Principal;

import java.util.Scanner;
import Contraseñas.PasswordPolicyValidator;

public class Main
{
    public static void main (String[] args)
    {
        passwordCreation();
    }

    public static void passwordCreation(){
        //Luego se puede setear pidiendo que se ingrese por consola
        String blackListPath = "src/main/top10k.txt";
        boolean forceSpecialChar = true;
        boolean forceCapitalLetter = true;
        boolean forceNumber = true;
        int minLength = 8;
        int maxLength = 32;

        /*
        Scanner s = new Scanner(System.in);

        System.out.print("Path de la blacklist: ");
        blackListPath = scanner.nextLine();

        System.out.print("Desea que la clave posea caracteres especiales?: ");
        forceSpecialChar = scanner.nextLine();

        System.out.print("Desea que la clave posea letras mayúsculas obligatorias?: ");
        forceCapitalLetter = scanner.nextLine();

        System.out.print("Desea que la clave posea números?: ");
        forceNumber = scanner.nextLine();

        System.out.print("Longitud mínima de la contraseña: ");
        minLenth = scanner.nextLine();

        System.out.print("Longitud máxima de la contraseña: ");
        maxLenth = scanner.nextLine();
        */

        PasswordPolicyValidator passwordPolicyValidator = new PasswordPolicyValidator(
                blackListPath, forceSpecialChar, forceCapitalLetter, forceNumber, minLength, maxLength
        );

        System.out.print("Password: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();

        passwordPolicyValidator.validatePassword(password);
    }
}
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
        String blackListPath = "src/main/java/Contraseñas/top10k.txt";
        boolean forceSpecialChar = true;
        boolean forceCapitalLetter = true;
        boolean forceNumber = true;
        int minLength = 8;
        int maxLength = 32;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Responder SI si desea especificar la ruta de la blacklist: ");
        String decision = scanner.nextLine();
        if(decision.equals("SI")){
            System.out.print("Path de la blacklist: ");
            blackListPath = scanner.nextLine();
        }

        System.out.print("Responder SI si desea cambiar las politicas de seguridad: ");
        decision = scanner.nextLine();
        if(decision.equals("SI")){
            System.out.print("Responder SI si desea que la clave posea caracteres especiales: ");
            decision = scanner.nextLine();
            if(decision.equals("SI")) forceSpecialChar = true;
            else forceSpecialChar = false;

            System.out.print("Responder SI si desea que la clave posea letras mayúsculas obligatorias: ");
            decision = scanner.nextLine();
            if(decision.equals("SI")) forceCapitalLetter = true;
            else forceCapitalLetter = false;

            System.out.print("Responder SI si desea que la clave posea números: ");
            decision = scanner.nextLine();
            if(decision.equals("SI")) forceNumber = true;
            else forceNumber = false;

            System.out.print("Longitud mínima de la contraseña: ");
            decision = scanner.nextLine();
            try {
                minLength = Integer.parseInt(decision);
            }catch (Exception e){
                System.out.print("Hubo error. Quedó longitud mínima 8");
            }

            System.out.print("Longitud máxima de la contraseña: ");
            decision = scanner.nextLine();
            try {
                maxLength = Integer.parseInt(decision);
            }catch (Exception e){
                System.out.print("Hubo error. Quedó longitud máxima 32");
            }
        }

        PasswordPolicyValidator passwordPolicyValidator = new PasswordPolicyValidator(
                blackListPath, forceSpecialChar, forceCapitalLetter, forceNumber, minLength, maxLength
        );

        System.out.print("Password: ");
        String password = scanner.nextLine();

        passwordPolicyValidator.validatePassword(password);
    }
}
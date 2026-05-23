package utils;

import java.util.Scanner;

public class Login{
    public static void showLogin(Scanner entrada){
        while(true) {
            System.out.println("=================== LOGIN ====================");
            System.out.print("\n                  USER:");
            String user = entrada.nextLine();
            System.out.print("\n              PASSWORD:");
            String password = entrada.nextLine();
            System.out.println("\n==============================================");

            if(validate(user, password))
                break;
            
            System.out.println("\n\nUsuário ou senha não encontradas, tente novamente\n\n");
        }
        System.out.println("\n\nUsuário encontrado, entrando\n\n");
    }

    private static boolean validate(String user, String password){
        return (user.equals("adm") && password.equals("123"));
    }
}
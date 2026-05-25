package utils;

import java.util.Scanner;
import java.util.List;

import model.Funcionario;
import utils.BancoDeDados;

public class Login{
    public static void showLogin(Scanner entrada){
        BancoDeDados.serializeFun();
        while(true) {
            System.out.println("=================== LOGIN ====================");
            System.out.print("\n                   CPF:");
            String user = entrada.nextLine();
            System.out.print("\n              PASSWORD:");
            String password = entrada.nextLine();
            System.out.println("\n==============================================");

            if(validate(user, password))
                break;
            
            System.out.println("\n\nUsuário ou senha não encontradas, tente novamente\n\n");
        }
        String user = BancoDeDados.funcionarioV.getFuncLogado().getNomePessoa();
        System.out.println("\n\nVocê entrou como " + user + "\n\n");
    }

    private static boolean validate(String user, String password){
        List<Funcionario> funcs = BancoDeDados.funcionarioV.getElementList();

        for(Funcionario f : funcs){
            if (f.getCpfPessoa().equals(user) && f.getSenha().equals(password)) {
                BancoDeDados.funcionarioV.setFuncLogado(f);
                return true;
            }
        }

        return false;
    }
}
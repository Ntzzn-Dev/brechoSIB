package utils;

import view.*;
import java.util.Scanner;

public class Dados{
    public static int requestCod(String msgPrincipal, String action, boolean aceitaVazio, BaseView view, Scanner entrada){
        try{
            while (true) {
                System.out.println("----------------------------------------------");
                System.out.println(msgPrincipal);
                System.out.println("Comandos: [/list] [/search] [/cancel]");
                System.out.println("----------------------------------------------");
                System.out.print("CÓDIGO: ");
                try {
                    String input = entrada.nextLine();

                    if(input.equals("/list")){
                        System.out.println("");
                        view.list(entrada);
                        continue;
                    } else if (input.equals("/search")){
                        System.out.println("");
                        view.read(entrada);
                        continue;
                    } else if (input.equals("/cancel")){
                        throw new CancelOperationException(action);
                    } else if(input.isBlank() && !aceitaVazio){
                        System.out.println("Digite um código válido.");
                        continue;
                    }else if(input.isBlank() && aceitaVazio){
                        input = "-1";
                    }

                    view.searchByPK(input);

                    System.out.println("----------------------------------------------\n");
                    return Integer.parseInt(input);       
                } catch (NumberFormatException e) {
                    Formatacao.patternError(e);
                } catch (IllegalArgumentException e) {
                    Formatacao.patternError(e);
                }
            }
        } catch (CancelOperationException e) {
            throw new CancelOperationException(e.getMessage());
        }
    }

    public static String requestCpf(String msgPrincipal, String action, boolean aceitaVazio, BaseView view, Scanner entrada){
        try{
            while (true) {
                System.out.println("----------------------------------------------");
                System.out.println(msgPrincipal);
                System.out.println("Comandos: [/list] [/search] [/cancel]");
                System.out.println("----------------------------------------------");
                System.out.print("CPF: ");
                try {
                    String input = entrada.nextLine();

                    if(input.equals("/list")){
                        System.out.println("");
                        view.list(entrada);
                        continue;
                    } else if (input.equals("/search")){
                        System.out.println("");
                        view.read(entrada);
                        continue;
                    } else if (input.equals("/cancel")){
                        throw new CancelOperationException(action);
                    } else if (!aceitaVazio || !input.isBlank()){
                        input = Formatacao.formatarCpf(input);
                    } else if(input.isBlank() && !aceitaVazio){
                        System.out.println("Digite um CPF.");
                        continue;
                    }

                    view.searchByPK(input);

                    System.out.println("----------------------------------------------\n");
                    return input;       
                } catch (IllegalArgumentException e) {
                    Formatacao.patternError(e);
                }
            }
        } catch (CancelOperationException e) {
            throw new CancelOperationException(e.getMessage());
        }
    }

    public static String requestValue(String msgPrincipal, String request, String action, boolean aceitaVazio, Scanner entrada){
        try {
            while (true) {
                System.out.println("----------------------------------------------");
                System.out.println(msgPrincipal);
                System.out.println("Comandos: [/review] [/cancel]");
                System.out.println("----------------------------------------------");
                System.out.print(request);
                try {
                    String input = entrada.nextLine();

                    if (input.equals("/cancel")){
                        throw new CancelOperationException(action);
                    } else if (input.equals("/review")){
                        throw new ReviewOperationException(action);
                    } 
                    if (!aceitaVazio && input.isBlank()){
                        throw new IllegalArgumentException("Campo obrigatório!");
                    }

                    if(request.equals("CPF: ")){
                        input = Formatacao.formatarCpf(input);
                    } else if(request.equals("CEP: ")){
                        input = Formatacao.formatarCep(input);
                    } else if(request.equals("UF: ")){
                        input = Formatacao.formatarUF(input);
                    } else if(request.equals("NUMERO: ")){
                        input = Formatacao.limparNum(input);
                    } else if(request.equals("SALÁRIO: ") || request.equals("VALOR: ") || request.equals("R$: ")){
                        Double.parseDouble(input); //Tenta, se der erro refaz, se nao retorna a string mesmo
                    }

                    System.out.println("----------------------------------------------\n");
                    return input;       
                } catch (NumberFormatException e) {
                    Formatacao.patternError(e);
                }catch (IllegalArgumentException e) {
                    Formatacao.patternError(e);
                }
            }
        } catch (CancelOperationException e) {
            throw new CancelOperationException(e.getMessage());
        }
    }

    public static void reviewForm(Runnable action, Runnable review) {
        while (true) {
            try {
                action.run();
                break;
            } catch (ReviewOperationException e) {
                Formatacao.patternError(e);
                review.run();
            }
        }
    }
}
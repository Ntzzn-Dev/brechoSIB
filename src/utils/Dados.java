package utils;

import view.*;
import model.Endereco;
import utils.BancoDeDados;
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
                        throw new CancelOperationException("< CANCELANDO " + action + " >");
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
                        throw new CancelOperationException("< CANCELANDO " + action + " >");
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
                        throw new CancelOperationException("< CANCELANDO " + action + " >");
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

    public static Endereco searchEnd(Scanner entrada){
        Endereco end = new Endereco();
        while (true) {
            System.out.println("-----------------------+----------------------");
            System.out.println("                ENDEREÇAMENTO");
            System.out.println("Comandos: [/review] [/cancel]");
            System.out.println("1 - Realizar uma consulta");
            System.out.println("2 - Cadastrar novo endereço");
            System.out.println("3 - Indicar o endereço para essa pessoa");
            System.out.println("-----------------------+----------------------");
            System.out.print("Opção: ");
            try{
                String input = entrada.nextLine();
                int codEnd, opcao;

                if (input.equals("/cancel")){
                    throw new CancelOperationException("< CANCELANDO ENDEREÇAMENTO >");
                } else if (input.equals("/review")){
                    throw new ReviewOperationException("ENDEREÇAMENTO");
                } else {
                    opcao = Integer.parseInt(input);
                }

                switch(opcao){
                    case 1:
                        BancoDeDados.enderecoV.read(entrada);
                        break;
                    case 2:
                        codEnd = BancoDeDados.enderecoV.createAndSaveCod(entrada);
                        end = BancoDeDados.enderecoV.requestByCod(codEnd);
                        if(end != null) return end;
                        break;
                    case 3:
                        System.out.print("Digite o código do endereço: ");
                        codEnd = entrada.nextInt();
                        entrada.nextLine();
                        end = BancoDeDados.enderecoV.requestByCod(codEnd);
                        if(end != null) return end;
                        break;
                }

                return end;
            } catch (NumberFormatException e) {
                Formatacao.patternError(e);
            } catch (IllegalArgumentException e) {
                Formatacao.patternError(e);
            }
        }
    }

    public static Endereco searchEnd(Scanner entrada, Endereco end){
        while (true) {
            System.out.println("-----------------------+----------------------");
            System.out.println("                ENDEREÇAMENTO");
            System.out.println("Comandos: [/review] [/cancel]");
            System.out.println("1 - Realizar uma consulta");
            System.out.println("2 - Cadastrar novo endereço");
            System.out.println("3 - Indicar o endereço para essa pessoa");
            System.out.println("4 - Manter endereço atual");
            System.out.println("-----------------------+----------------------");
            System.out.print("Opção: ");
            try {
                String input = entrada.nextLine();
                int codEnd, opcao;

                if (input.equals("/cancel")){
                    throw new CancelOperationException("< CANCELANDO ENDEREÇAMENTO >");
                } else if (input.equals("/review")){
                    throw new ReviewOperationException("ENDEREÇAMENTO");
                } else {
                    opcao = Integer.parseInt(input);
                }

                switch(opcao){
                    case 1:
                        BancoDeDados.enderecoV.read(entrada);
                        break;
                    case 2:
                        codEnd = BancoDeDados.enderecoV.createAndSaveCod(entrada);
                        end = BancoDeDados.enderecoV.requestByCod(codEnd);
                        if(end != null) return end;
                        break;
                    case 3:
                        System.out.print("Digite o código do endereço: ");
                        codEnd = entrada.nextInt();
                        entrada.nextLine();
                        end = BancoDeDados.enderecoV.requestByCod(codEnd);
                        if(end != null) return end;
                        break;
                    case 4: 
                        if(end != null) return end;
                        break;
                } 

                return end;
            }catch (NumberFormatException e) {
                Formatacao.patternError(e);
            } catch (IllegalArgumentException e) {
                Formatacao.patternError(e);
            }
        }
    }
}
import model.*;
import view.*;
import utils.*;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        Scanner entrada = new Scanner(System.in);
        int opcao = 0;

        Login.showLogin(entrada);

        try{
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("==================== MENU ====================");
                System.out.println("                                              ");
                System.out.println("1 -               Endereços                - 1"); 
                System.out.println("2 -              Funcionário               - 2");
                System.out.println("3 -                Cliente                 - 3");
                System.out.println("4 -          Métodos de pagamento          - 4");
                System.out.println("5 -                 Marcas                 - 5");
                System.out.println("6 -               Categorias               - 6");
                System.out.println("7 -                 Vendas                 - 7");
                System.out.println("8 -                Produtos                - 8");
                System.out.println("9 -    X             Sair             X    - 9");
                System.out.println("");
                System.out.print("Digite a opção desejada: ");
                String input = entrada.nextLine();

                try {
                    opcao = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    Formatacao.patternError();
                    continue;
                }

                System.out.println("");

                switch(opcao){
                    case 1:
                        BancoDeDados.serializeEnd();
                        BancoDeDados.enderecoV.showCRUD(entrada);
                        break;
                    case 2:
                        BancoDeDados.serializeFun();
                        BancoDeDados.funcionarioV.showCRUD(entrada);
                        break;
                    case 3:
                        BancoDeDados.serializeCli();
                        BancoDeDados.clienteV.showCRUD(entrada);
                        break;
                    case 4:
                        BancoDeDados.serializeMtd();
                        BancoDeDados.metodoPagamentoV.showCRUD(entrada);
                        break;
                    case 5:
                        BancoDeDados.serializeMar();
                        BancoDeDados.marcaV.showCRUD(entrada);
                        break;
                    case 6:
                        BancoDeDados.serializeCat();
                        BancoDeDados.categoriaV.showCRUD(entrada);
                        break;
                    case 7:
                        BancoDeDados.serializeVen();
                        BancoDeDados.vendaV.showCRUD(entrada);
                        break;
                    case 8:
                        BancoDeDados.serializePro();
                        BancoDeDados.produtoV.showCRUD(entrada);
                        break;
                    case 9:
                        System.out.println("Fechando SIB");
                        break;
                }
            } while (opcao != 9);
        } catch (Exception e){
            System.out.println("\nX>>==--");
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class MetodoPagamentoView implements BaseView<MetodoPagamento>{
    private List<MetodoPagamento> elementList = new ArrayList<>();

    public void insertList(List<MetodoPagamento> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<MetodoPagamento> getElementList(){
        return this.elementList;
    }
        
    public MetodoPagamento requestByCod(int codMtd){
        for (MetodoPagamento m : elementList) {
             if (m.getCodMetodoPag() == codMtd) {
                return m;
            }
        }
        throw new IllegalArgumentException("Método de pagamento não encontrado");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (MetodoPagamento m : elementList) {
             if (m.getCodMetodoPag() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }

    // Controle de Métodos de pagamento =================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("============ MÉTODO DE PAGAMENTO =============");
                System.out.println("                                              ");
                System.out.println("1 -        Criar método de pagamento       - 1");
                System.out.println("2 -       Editar método de pagamento       - 2");
                System.out.println("3 -       Excluir método de pagamento      - 3");
                System.out.println("4 -   Listar todos métodos de pagamento    - 4");
                System.out.println("5 -      Procurar método de pagamento      - 5");
                System.out.println("9 -              <- Voltar ->              - 9");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.print("Digite a opção desejada: ");
                opcao = entrada.nextInt();
                entrada.nextLine();
                System.out.println("");

                switch(opcao){
                    case 1:
                        create(entrada);
                        break;
                    case 2:
                        update(entrada);
                        break;
                    case 3:
                        delete(entrada);
                        break;
                    case 4:
                        list(entrada);
                        break;
                    case 5:
                        read(entrada);
                        break;
                    case 9:
                        System.out.println("Voltando pra o menu \n");
                        break;
                }

            } while (opcao != 9);
        } catch (Exception e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void create(Scanner entrada){
        try{
            System.out.println("CRIAR NOVO MÉTODO DE PAGAMENTO: ");
            
            String descMetodoPag = Dados.requestValue(
                "Digite a descrição do método de pagamento: ", 
                "NOME: ", 
                "CRIAÇÃO",
                false,
                entrada
            );
            System.out.println("");

            elementList.add(new MetodoPagamento(elementList.size() + 1, descMetodoPag));

            System.out.println("Método de pagamento registrado!");
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR MÉTODO DE PAGAMENTO: ");
            
            MetodoPagamento mtd = requestByCod(
                Dados.requestCod(
                    "Digite o código do método de pagamento que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.metodoPagamentoV,
                    entrada
                )
            );

            String descMetodoPag = Dados.requestValue(
                "Deixe em branco para manter ("+mtd.getDescMetodoPag()+")\nDigite a nova descrição: ", 
                "DESCRIÇÃO: ", 
                "< CANCELANDO EDIÇÂO >",
                true,
                entrada
            );
            descMetodoPag = descMetodoPag.isEmpty() ? mtd.getDescMetodoPag() : descMetodoPag;

            boolean ativoMetodoPag;
            while (true) {
                System.out.println("Deixe em branco para manter (" + mtd.getAtivoMetodoPag() + ")");
                System.out.print("O método de pagamento está ativo? (S/N): ");

                String input = entrada.nextLine().trim().toUpperCase();

                if (input.isEmpty()) {
                    ativoMetodoPag = mtd.getAtivoMetodoPag();
                    break;
                }
                if (input.equals("S")) {
                    ativoMetodoPag = true;
                    break;
                }
                if (input.equals("N")) {
                    ativoMetodoPag = false;
                    break;
                }

                Formatacao.patternError();
            }
            System.out.println("");

            MetodoPagamento newMtd = new MetodoPagamento(mtd.getCodMetodoPag(), descMetodoPag, ativoMetodoPag);

            System.out.println("----------------------------------------------");
            System.out.println("MÉTODO DE PAGAMENTO ANTIGO: ");
            mtd.showMetodoPag();
            System.out.println("MÉTODO DE PAGAMENTO ATUALIZADO): ");
            newMtd.showMetodoPag();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição do Método de pagamento? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    mtd.copyFrom(newMtd);
                    System.out.println("\nMétodo de pagamento atualizado!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de método de pagamento descartada!");
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }
            System.out.println("==============================================\n");
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override 
    public void delete(Scanner entrada){
        try{
            System.out.println("REMOVER MÉTODO DE PAGAMENTO: ");

            MetodoPagamento mtd = requestByCod(
                    Dados.requestCod(
                    "Digite o código do método de pagamento que deseja apagar: ", 
                    "REMOÇÃO",
                    false,
                    BancoDeDados.metodoPagamentoV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            mtd.showMetodoPag();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover o método de pagamento? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(mtd);
                    System.out.println("\nRemoção do método de pagamento concluida!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nOperação de remoção cancelada!");
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }
            System.out.println("==============================================\n");
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void list(Scanner entrada){
        System.out.println("MÉTODOS DE PAGAMENTO REGISTRADOS: ");
        System.out.println("----------------------------------------------");
        for (MetodoPagamento mtd : elementList) {
            mtd.showMetodoPag();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR MÉTODO DE PAGAMENTO: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoMetodoPag = entrada.nextLine();

        List<MetodoPagamento> termosMtdEncontrados = new ArrayList<>();
        for (MetodoPagamento mtd : elementList) {
            if (mtd.searchByTerm(termoMetodoPag)) {
                termosMtdEncontrados.add(mtd);
            }
        }

        System.out.println("RESULTADOS [MÉTODO DE PAGAMENTO]: ");

        if(termosMtdEncontrados.isEmpty()){
            System.out.println("Nenhum método de pagamento com esse termo encontrado\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (MetodoPagamento mtd : termosMtdEncontrados) {
            mtd.showMetodoPag();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}
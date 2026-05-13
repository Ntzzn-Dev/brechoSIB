package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.*;
import utils.*;

public class VendaView implements BaseView<Venda>{
    private List<Venda> elementList = new ArrayList<>();
    private List<VendaPagamento> vendasPagamentosList = new ArrayList<>();

    public void insertList(List<Venda> list){
        this.elementList = new ArrayList<>(list);
    }
    public void insertVendasPagamentosList(List<VendaPagamento> list){
        this.vendasPagamentosList = new ArrayList<>(list);
    }

    public List<Venda> getElementList(){
        return this.elementList;
    }
    public List<VendaPagamento> getVendasPagamentosList(){
        return this.vendasPagamentosList;
    }
        
    public Venda requestByCod(int codVenda){
        for (Venda v : elementList) {
             if (v.getCodVenda() == codVenda) {
                return v;
            }
        }
        throw new IllegalArgumentException("Venda não encontrada");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Venda v : elementList) {
             if (v.getCodVenda() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }
    

    // Controle de Vendas ===============================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("=================== VENDAS ===================");
                System.out.println("                                              ");
                System.out.println("1 -               Criar venda              - 1");
                System.out.println("2 -               Pagar venda              - 2");
                System.out.println("3 -              Cancelar venda            - 3");
                System.out.println("4 -           Listar todas vendas          - 4");
                System.out.println("5 -             Procurar venda             - 5");
                System.out.println("6 -   Listar formas de pagamento da venda  - 6");
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
                    case 6:
                        showMetodoPagVenda(entrada);
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
            System.out.println("CRIAR NOVA VENDA: ");

            Cliente cliente = BancoDeDados.clienteV.requestByCpf(
                Dados.requestCpf(
                    "Digite o CPF do cliente: ", 
                    "CRIAÇÃO",
                    false,
                    BancoDeDados.clienteV,
                    entrada
                )
            );
            System.out.println("Cliente selecionado: " + cliente.getNomePessoa());    

            Funcionario func = BancoDeDados.funcionarioV.requestByCpf(
                Dados.requestCpf(
                    "Digite o cpf do funcionário: ", 
                    "CRIAÇÃO",
                    false,
                    BancoDeDados.funcionarioV,
                    entrada
                )
            );
            System.out.println("Funcionário selecionado: " + func.getNomePessoa());

            double vtVenda = Double.parseDouble(Dados.requestValue(
                    "Digite o valor total dessa venda: ", 
                    "VALOR: ",
                    "CRIAÇÃO",
                    false,
                    entrada
                )
            );
            System.out.println("");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

            elementList.add(new Venda(elementList.size() + 1, LocalDateTime.now().format(formatter), cliente, func, vtVenda, 0));

            System.out.println("Venda registrada!");
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override 
    public void update(Scanner entrada){
        try{
            System.out.println("PAGAR VENDA: "); 

            Venda venda = requestByCod(
                Dados.requestCod(
                    "Digite o código da venda que deseja pagar: ", 
                    "< ABORTAR PAGAMENTO >",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );

            if(venda.getStatus() == 1) throw new CancelOperationException("Venda já está finalizada!!!");
            else if(venda.getStatus() == 2) throw new CancelOperationException("Venda já está cancelada!!!");

            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            double vlrTotal = venda.getValorTotal();

            do{

                MetodoPagamento mtdP = BancoDeDados.metodoPagamentoV.requestByCod(
                    Dados.requestCod(
                        "Escolha o método de pagamento: ", 
                        "< ABORTAR PAGAMENTO >",
                        false,
                        BancoDeDados.metodoPagamentoV,
                        entrada
                    )
                );

                if(!mtdP.getAtivoMetodoPag()){
                    System.out.println("Método desabilitado, escolha outra forma de pagamento");
                    continue;
                }

                double valorPago = Double.parseDouble(
                    Dados.requestValue(
                        "Digite o valor a ser pago: ", 
                        "R$: ", 
                        "< ABORTAR PAGAMENTO >",
                        false,
                        entrada
                    )
                );

                if(vlrTotal < valorPago) {
                    valorPago = vlrTotal;
                    vlrTotal = 0;
                    
                    System.out.println("Valor maior que necessário, ajustado para: R$" + String.format("%.2f", valorPago));
                    System.out.println("");
                } else {
                    vlrTotal -= valorPago;
                }

                vendasPagamentosList.add(new VendaPagamento(mtdP, venda, valorPago));
            } while (vlrTotal > 0);

            for(VendaPagamento vp : vendasPagamentosList){
                vp.showVendaPag();
                System.out.println("==============================================\n");
            }

            while (true) {
                System.out.print("Deseja concluir o pagamento dessa venda? (S/N)\n[Essa opção não pode ser desfeita] > ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    venda.setStatus(1);
                    System.out.println("\nVenda paga!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nOperação de pagamento abortada!");
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override 
    public void delete(Scanner entrada){
        try{
            System.out.println("CANCELAR VENDA: ");

            Venda venda = requestByCod(
                Dados.requestCod(
                    "Digite o código da venda que deseja cancelar: ", 
                    "< ABORTAR CANCELAMENTO >",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );

            if(venda.getStatus() == 1) throw new CancelOperationException("Venda já está finalizada!!!");
            else if(venda.getStatus() == 2) throw new CancelOperationException("Venda já está cancelada!!!");

            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            while (true) {
                System.out.print("Deseja realmente cancelar a venda? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    venda.setStatus(2);
                    System.out.println("\nVenda cancelada!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nOperação de cancelamento abortada!");
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
        System.out.println("VENDAS REGISTRADAS: ");
            System.out.println("------------------+-----------------");
        for (Venda venda : elementList) {
            venda.showVenda();
            System.out.println("------------------+-----------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR VENDA: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoVenda = entrada.nextLine();

        List<Venda> termosVendasEncontradas = new ArrayList<>();
        for (Venda venda : elementList) {
            if (venda.searchByTerm(termoVenda)) {
                termosVendasEncontradas.add(venda);
            }
        }

        System.out.println("RESULTADOS [VENDA]: ");

        if(termosVendasEncontradas.isEmpty()){
            System.out.println("Nenhuma venda com esse termo encontrada\n");
        } else {
            System.out.println("------------------+-----------------");
        }

        for (Venda venda : termosVendasEncontradas) {
            venda.showVenda();
            System.out.println("------------------+-----------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void showMetodoPagVenda(Scanner entrada){
        try{
            System.out.println("MÉTODOS DE PAGAMENTO DA VENDA: "); 

            Venda venda = requestByCod(
                Dados.requestCod(
                    "Digite o código da venda que deseja visualizar: ", 
                    "< CANCELANDO VISUALIZAÇÃO DE LISTA DE PAGAMENTOS >",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );

            if(venda.getStatus() == 0) throw new CancelOperationException("Venda não está finalizada!!!");
            else if(venda.getStatus() == 2) throw new CancelOperationException("Venda está cancelada!!!");

            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            System.out.println("          FORMAS DE PAGAMENTO       ");
            System.out.println("------------------+-----------------");
            for(VendaPagamento vp : vendasPagamentosList){
               if (vp.getVenda().getCodVenda() == venda.getCodVenda()) {
                    vp.showVendaPag();
                    System.out.println("------------------+-----------------");
                }
            }

            System.out.println("\n======= Pressione ENTER para continuar =======\n");
            entrada.nextLine();
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

}
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

    public void insertList(List<Venda> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Venda> getElementList(){
        return this.elementList;
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
                System.out.println("2 -       Adicionar produtos na venda      - 2");
                System.out.println("3 -               Pagar venda              - 3");
                System.out.println("4 -              Cancelar venda            - 4");
                System.out.println("5 -           Listar todas vendas          - 5");
                System.out.println("6 -             Procurar venda             - 6");
                System.out.println("7 -            Visualizar venda            - 7");
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
                        payVenda(entrada);
                        break;
                    case 4:
                        delete(entrada);
                        break;
                    case 5:
                        list(entrada);
                        break;
                    case 6:
                        read(entrada);
                        break;
                    case 7:
                        viewVenda(entrada);
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

            Venda venda = new Venda();
            venda.setCodVenda(elementList.size() + 1);
            venda.setDataVenda(LocalDateTime.now().format(formatter));
            venda.setFuncVenda(BancoDeDados.funcionarioV.getFuncLogado());

            Dados.reviewForm(() -> {
                if (venda.getClienteVenda() == null) {
                    Cliente cliente = BancoDeDados.clienteV.requestByCpf(
                        Dados.requestCpf(
                            "Digite o CPF do cliente: ", 
                            "CRIAÇÃO",
                            false,
                            BancoDeDados.clienteV,
                            entrada
                        )
                    );
                    venda.setClienteVenda(cliente);
                    System.out.println("Cliente selecionado: " + cliente.getNomePessoa() + "\n");    
                }
            }, () -> {
                review(venda, entrada);
            });
            
            System.out.println("");

            elementList.add(venda);
            
            System.out.println("Venda registrada!");
            System.out.println("==============================================\n");

            while (true) {
                System.out.print("Deseja adicionar produtos a essa venda? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                entrada.nextLine();
                if(opcao == 'S'){
                    insertItens(entrada, venda);
                    break;
                } else if(opcao == 'N') {
                    break;
                } else {
                    Formatacao.patternError(opcao);
                }
            }
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override 
    public void update(Scanner entrada){
        try{
            System.out.println("ADICIONAR PRODUTOS NA VENDA: "); 

            Venda venda = requestByCod(
                Dados.requestCod(
                    "Digite o código da venda: ", 
                    "< ABORTAR ADIÇÃO >",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );

            if(venda.getStatus() == 1) throw new CancelOperationException("Venda já está finalizada!!!");
            else if(venda.getStatus() == 2) throw new CancelOperationException("Venda já está cancelada!!!");

            insertItens(entrada, venda);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    public void insertItens(Scanner entrada, Venda venda){
        try{
            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            while (true){
                Produto pVenda = BancoDeDados.produtoV.requestByCod(
                    Dados.requestCod(
                        "Escolha um produto: ", 
                        "< ABORTAR CARRINHO DE VENDA >",
                        false,
                        BancoDeDados.produtoV,
                        entrada
                    )
                );

                if(pVenda.getStatusVendido()){
                    System.out.println("Esse produto ja foi vendido");
                    continue;
                }

                venda.setValorTotal(venda.getValorTotal() + pVenda.getPrecoProd());
                venda.addProdutos(pVenda);

                System.out.println("Produto adicionado: " + pVenda.getDescProd());

                System.out.println("\nDeseja escolher outro item? (S/N)");
                char opcao = entrada.next().toUpperCase().charAt(0);
                entrada.nextLine();
                if(opcao == 'N') {
                    break;
                } else if(opcao != 'S'){
                    Formatacao.patternError(opcao);
                }
            }

            for(Produto p : venda.getProdutos()){
                p.showProp();
                System.out.println("==============================================");
            }
            System.out.println("");

            while (true) {
                System.out.print("Deseja fazer o pagamento dessa venda? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                entrada.nextLine();
                if(opcao == 'S'){
                    payVenda(entrada, venda);
                    break;
                } else if(opcao == 'N') {
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
                entrada.nextLine();
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
        try{
            System.out.println("====================LISTAR====================");
            System.out.println("0 -            Listar em aberto            - 0");
            System.out.println("1 -              Listar pagas              - 1");
            System.out.println("2 -           Listar canceladas            - 2");
            System.out.println("3 -              Listar todas              - 3");
            System.out.println("==============================================");
            System.out.println("");
            System.out.print("Digite a opção desejada: ");

            int opcao = entrada.nextInt();
            entrada.nextLine();
            System.out.println("");

            listStatus(opcao, entrada);
        } catch (Exception e){
            Formatacao.patternError(e);
        }
    }

    public void listStatus(int i, Scanner entrada){
        System.out.println("VENDAS REGISTRADAS: ");
        System.out.println("------------------+-----------------");
        for (Venda venda : elementList) {
            if(venda.getStatus() == i || i == 3){
                venda.showVenda();
                System.out.println("------------------+-----------------");
            } 
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

    public void payVenda(Scanner entrada){
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
        
            payVenda(entrada, venda);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    public void payVenda(Scanner entrada, Venda venda){
        try{
            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            double vlrTotal = venda.getValorTotal();
            List<VendaPagamento> pags = new ArrayList<>();

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

                pags.add(new VendaPagamento(mtdP, valorPago));
            } while (vlrTotal > 0);

            for(VendaPagamento vp : pags){
                vp.showVendaPag();
                System.out.println("==============================================");
            }
            System.out.println("");

            while (true) {
                System.out.print("Deseja concluir o pagamento dessa venda? (S/N)\n[Essa opção não pode ser desfeita] > ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                entrada.nextLine();
                if(opcao == 'S'){
                    venda.setPagamentos(pags);
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

    public void viewVenda(Scanner entrada){
        try{
            System.out.println("VISUALIZAR VENDA: "); 

            Venda venda = requestByCod(
                Dados.requestCod(
                    "Digite o código da venda que deseja visualizar: ", 
                    "< CANCELANDO VISUALIZAÇÃO DE VENDA >",
                    false,
                    BancoDeDados.vendaV,
                    entrada
                )
            );

            System.out.println("------------------+-----------------");
            venda.showVenda();
            System.out.println("------------------+-----------------");

            System.out.println("         PRODUTOS NO CARRINHO       ");
            System.out.println("------------------+-----------------");
            for(Produto p : venda.getProdutos()){
                p.showProp();
                System.out.println("------------------+-----------------");
            }

            System.out.println("          FORMAS DE PAGAMENTO       ");
            System.out.println("------------------+-----------------");
            for(VendaPagamento vp : venda.getPagamentos()){
                vp.showVendaPag();
                System.out.println("------------------+-----------------");
            }

            System.out.println("\n======= Pressione ENTER para continuar =======\n");
            entrada.nextLine();
        }catch (IllegalArgumentException e) {
            Formatacao.patternError(e);
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    public void review(Venda venda, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("Código:        " + (venda.getCodVenda() == -1 ? "Não preenchido ainda" : venda.getCodVenda()));
        System.out.println("Data da venda: " + venda.getDataVenda());
        System.out.println("Cliente:       " + (venda.getClienteVenda() == null ? "Não preenchido ainda" : venda.getClienteVenda().getNomePessoa()));
        System.out.println("Funcionário:   " + (venda.getFuncVenda() == null ? "Não preenchido ainda" : venda.getFuncVenda().getNomePessoa()));
        System.out.println("Valor total:   " + (venda.getValorTotal() == 0.0 ? "Não preenchido ainda" : venda.getValorTotal()));
        System.out.println("Status:        " + venda.getStatusString());
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}

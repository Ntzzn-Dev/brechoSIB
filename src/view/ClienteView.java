package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class ClienteView implements BaseView<Cliente>{
    private List<Cliente> elementList = new ArrayList<>();

    public void insertList(List<Cliente> list){
        this.elementList = new ArrayList<>(list);
    }
    
    public List<Cliente> getElementList(){
        return this.elementList;
    }

    public Cliente requestByCpf(String cpf){
        for (Cliente f : elementList) {
            if (Formatacao.limparCpf(f.getCpfPessoa()).equals(Formatacao.limparCpf(cpf))) {
                return f;
            }
        }
        throw new IllegalArgumentException("Cliente não encontrado");
    }

    public boolean searchByPK(String value){
        for (Cliente f : elementList) {
            if (Formatacao.limparCpf(f.getCpfPessoa()).equals(Formatacao.limparCpf(value))) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um CPF válido");
    }

    // Controle de clientes ===================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 9;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================== CLIENTE ===================");
                System.out.println("                                              ");
                System.out.println("1 -             Criar cliente              - 1");
                System.out.println("2 -             Editar cliente             - 2");
                System.out.println("3 -            Excluir cliente             - 3");
                System.out.println("4 -          Listar todos clientes         - 4");
                System.out.println("5 -            Procurar cliente            - 5");
                System.out.println("9 -              <- Voltar ->              - 9");
                System.out.println("");
                System.out.println("");
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
            System.out.println("CRIAR NOVO CLIENTE: ");

            Cliente cli = new Cliente();
            
            Dados.reviewForm(() -> {
                if (cli.getCpfPessoa() == null) {
                    cli.setCpfPessoa(
                        Dados.requestValue(
                            "Digite o CPF:",
                            "CPF: ",
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }
                
                if (cli.getNomePessoa() == null) {
                    cli.setNomePessoa(
                        Dados.requestValue(
                            "Digite o nome:",
                            "NOME: ",
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }

                if (cli.getTelPessoa() == null) {
                    cli.setTelPessoa(
                        Dados.requestValue(
                            "Digite o telefone:",
                            "TEL: ",
                            "CRIAÇÃO",
                            true,
                            entrada
                        )
                    );
                }
                
                if (cli.getEndPessoa() == null) {
                    cli.setEndPessoa(
                        BancoDeDados.enderecoV.searchEnd(entrada)
                    );
                }
            }, () -> {
                review(cli, entrada);
            });
            
            System.out.println("");

            elementList.add(cli);

            System.out.println("Cliente registrado!");
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR CLIENTE: ");

            Cliente cliOld = requestByCpf(
                Dados.requestCpf(
                    "Digite o CPF do Cliente que deseja alterar: ", 
                    "EDIÇÃO",
                    false,
                    BancoDeDados.clienteV,
                    entrada
                )
            );

            Cliente cli = new Cliente();
            cli.copyFrom(cliOld);
            
            Dados.reviewForm(() -> {
                if (cli.getCpfPessoa().equals(cliOld.getCpfPessoa())) {
                    String newCpfClient = Dados.requestValue(
                        "Deixe em branco para manter ("+cli.getCpfPessoa()+")\nDigite o CPF do Cliente que deseja alterar: ",
                        "CPF: ",
                        "EDIÇÃO",
                        false,
                        entrada
                    );
                    newCpfClient = newCpfClient.isEmpty() ? cli.getCpfPessoa() : newCpfClient;
                    cli.setCpfPessoa(newCpfClient);
                }
                
                if (cli.getNomePessoa().equals(cliOld.getNomePessoa())) {
                    String nomeClient = Dados.requestValue(
                        "Deixe em branco para manter ("+cli.getNomePessoa()+")\nDigite o novo nome: ", 
                        "NOME: ",
                        "EDIÇÃO",
                        false,
                        entrada
                    );
                    nomeClient = nomeClient.isEmpty() ? cli.getNomePessoa() : nomeClient;
                    cli.setNomePessoa(nomeClient);
                }

                if (cli.getTelPessoa().equals(cliOld.getTelPessoa())) {
                    String telClient = Dados.requestValue(
                        "Deixe em branco para manter ("+cli.getTelPessoa()+")\nDigite o novo telefone: ", 
                        "TEL: ",
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    telClient = telClient.isEmpty() ? cli.getTelPessoa() : telClient;
                    cli.setTelPessoa(telClient);
                }
                
                if (cli.getEndPessoa() == cliOld.getEndPessoa()) {
                    cli.setEndPessoa(
                        BancoDeDados.enderecoV.searchEnd(entrada, cli.getEndPessoa())
                    );
                }
            }, () -> {
                review(cli, entrada);
            });

            
            System.out.println("");

            System.out.println("----------------------------------------------");
            System.out.println("CLIENTE ANTIGO: ");
            cliOld.showProp();
            System.out.println("CLIENTE ATUALIZADO): ");
            cli.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição do Cliente? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    cliOld.copyFrom(cli);
                    System.out.println("\nCliente atualizado!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de cliente descartada!");
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
            System.out.println("REMOVER CLIENTE: ");
    
            Cliente client = requestByCpf(
                Dados.requestCpf(
                    "Digite o CPF do Cliente que deseja apagar: ",
                    "REMOÇÃO",
                    false,
                    BancoDeDados.clienteV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            client.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover o cliente? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(client);
                    System.out.println("\nRemoção do cliente concluida!");
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
        System.out.println("CLIENTES REGISTRADOS: ");
        System.out.println("----------------------------------------------");
        for (Cliente client : elementList) {
            client.showProp();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR CLIENTE: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoClient = entrada.nextLine();

        List<Cliente> termosClientEncontrados = new ArrayList<>();
        for (Cliente client : elementList) {
            if (client.searchByTerm(termoClient)) {
                termosClientEncontrados.add(client);
            }
        }

        System.out.println("RESULTADOS [CLIENTE]: ");

        if(termosClientEncontrados.isEmpty()){
            System.out.println("Nenhum cliente com esse termo encontrado\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Cliente client : termosClientEncontrados) {
            client.showProp();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void review(Cliente cli, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("CPF:      " + cli.getCpfPessoa());
        System.out.println("Nome:     " + cli.getNomePessoa());
        System.out.println("Telefone: " + cli.getTelPessoa());
        System.out.println("\nEndereco: "); cli.getEndPessoa().showProp();
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}
package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class FuncionarioView implements BaseView<Funcionario>{
    private List<Funcionario> elementList = new ArrayList<>();
    private Funcionario funcLogado;

    public void insertList(List<Funcionario> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Funcionario> getElementList(){
        return this.elementList;
    }

    public Funcionario requestByCpf(String cpf){
        for (Funcionario f : elementList) {
            if (Formatacao.limparCpf(f.getCpfPessoa()).equals(Formatacao.limparCpf(cpf))) {
                return f;
            }
        }
        throw new IllegalArgumentException("Funcionário não encontrado");
    }
    
    public boolean searchByPK(String value){
        for (Funcionario f : elementList) {
            if (Formatacao.limparCpf(f.getCpfPessoa()).equals(Formatacao.limparCpf(value))) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um CPF válido");
    }

    public void setFuncLogado(Funcionario funcLogado){
        this.funcLogado = funcLogado;
    }

    public Funcionario getFuncLogado(){
        return funcLogado;
    }


    // Controle de funcionários ==============================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================ FUNCIONARIO =================");
                System.out.println("                                              ");
                System.out.println("1 -            Criar funcionário           - 1");
                System.out.println("2 -            Editar funcionário          - 2");
                System.out.println("3 -           Excluir funcionário          - 3");
                System.out.println("4 -       Listar todos funcionários        - 4");
                System.out.println("5 -          Procurar funcionário          - 5");
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
            System.out.println("CRIAR NOVO FUNCIONÁRIO: ");

            Funcionario func = new Funcionario();

            Dados.reviewForm(() -> {
                if (func.getCpfPessoa() == null) {
                    func.setCpfPessoa(
                        Dados.requestValue(
                        "Digite o CPF: ", 
                        "CPF: ", 
                        "CRIAÇÃO",
                        false,
                        entrada)
                    );
                }

                if (func.getNomePessoa() == null) {
                        func.setNomePessoa(
                            Dados.requestValue(
                            "Digite o nome: ", 
                            "NOME: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }


                if (func.getTelPessoa() == null) {
                    func.setTelPessoa(
                        Dados.requestValue(
                            "Digite o telefone: ", 
                            "TEL: ", 
                            "CRIAÇÃO",
                            true,
                            entrada
                        )
                    );
                }
            
                if (func.getEndPessoa() == null) {
                    func.setEndPessoa(
                        Dados.searchEnd(entrada)
                    );
                }

                if (func.getSalarioFunc() == 0) {
                    func.setSalarioFunc(
                        Double.parseDouble(
                            Dados.requestValue(
                                "Digite o salário: ", 
                                "SALÁRIO: ", 
                                "CRIAÇÃO",
                                true,
                                entrada
                            )
                        )
                    );
                }

                if (func.getSenha() == null) {
                    func.setSenha(
                        Dados.requestValue(
                            "Digite a senha do funcionario: ", 
                            "SENHA: ", 
                            "CRIAÇÃO",
                            true,
                            entrada
                        )
                    );
                }
            },() -> {
                review(func, entrada);
            });
            System.out.println("");

            elementList.add(func);

            System.out.println("Funcionário registrado!");
            System.out.println("==============================================\n");
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR FUNCIONÁRIO: ");

            Funcionario funcOld = requestByCpf(
                Dados.requestCpf(
                    "Digite o CPF do Funcionário que deseja alterar: ",
                    "CPF: ",
                    false, 
                    BancoDeDados.funcionarioV,
                    entrada
                )
            );

            Funcionario func = new Funcionario();
            func.copyFrom(funcOld);

            Dados.reviewForm(() -> {
                if (func.getCpfPessoa().equals(funcOld.getCpfPessoa())) {
                    String newCpfFunc = Dados.requestValue(
                        "Deixe em branco para manter ("+func.getCpfPessoa()+")\nDigite o novo CPF: ",
                        "CPF: ",
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    newCpfFunc = newCpfFunc.isEmpty() ? func.getCpfPessoa() : newCpfFunc;
                    func.setCpfPessoa(newCpfFunc);
                }

                if (func.getNomePessoa().equals(funcOld.getNomePessoa())) {
                    String nomeFunc = Dados.requestValue(
                        "Deixe em branco para manter ("+func.getNomePessoa()+")\nDigite o novo nome: ", 
                        "NOME: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    nomeFunc = nomeFunc.isEmpty() ? func.getNomePessoa() : nomeFunc;
                    func.setNomePessoa(nomeFunc);
                }

                if (func.getTelPessoa().equals(funcOld.getTelPessoa())) {
                    String telFunc = Dados.requestValue(
                        "Deixe em branco para manter ("+func.getTelPessoa()+")\nDigite o novo telefone: ", 
                        "TEL: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    telFunc = telFunc.isEmpty() ? func.getTelPessoa() : telFunc;
                    func.setTelPessoa(telFunc);
                }

                if (func.getEndPessoa() == null) {
                    func.setEndPessoa(
                        Dados.searchEnd(entrada)
                    );
                }

                if (func.getSalarioFunc() == funcOld.getSalarioFunc()) {
                    String inputSal = Dados.requestValue(
                        "Deixe em branco para manter ("+func.getSalarioFunc()+")\nDigite o novo salário: ", 
                        "SALÁRIO: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    double salFunc = inputSal.isEmpty() ? func.getSalarioFunc() : Double.parseDouble(inputSal);
                    func.setSalarioFunc(salFunc);
                }

                if ((func.getCpfPessoa().equals(funcLogado) || func.validarSenha(entrada)) && func.getSenha().equals(funcOld.getSenha())) {
                    String senhaFunc = Dados.requestValue(
                        "Deixe em branco para manter \nDigite o novo telefone: ", 
                        "SENHA: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    senhaFunc = senhaFunc.isEmpty() ? func.getSenha() : senhaFunc;
                    func.setSenha(senhaFunc);
                }
            }, () -> {
                review(func, entrada);
            });
            System.out.println("");

            System.out.println("----------------------------------------------");
            System.out.println("FUNCIONÁRIO ANTIGO: ");
            funcOld.showFunc();
            System.out.println("FUNCIONÁRIO ATUALIZADO): ");
            func.showFunc();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição do funcionário? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    funcOld.copyFrom(func);
                    System.out.println("\nFuncionário atualizado!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de funcionário descartada!");
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
            System.out.println("REMOVER FUNCIONÁRIO: ");
            
            Funcionario func = requestByCpf(
                Dados.requestCpf(
                    "Digite o CPF do funcionário que deseja apagar: ", 
                    "REMOÇÃO",
                    false,
                    BancoDeDados.funcionarioV,
                    entrada
                )
            );

            if(!func.validarSenha(entrada)) throw new CancelOperationException("Remoção impedida");

            System.out.println("----------------------------------------------");
            func.showFunc();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover o funcionário? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(func);
                    System.out.println("\nRemoção do funcionário concluida!");
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
        System.out.println("FUNCIONÁRIOS REGISTRADOS: ");
        System.out.println("----------------------------------------------");
        for (Funcionario func : elementList) {
            func.showFunc();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR FUNCIONÁRIO: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoFunc = entrada.nextLine();

        List<Funcionario> termosFuncEncontrados = new ArrayList<>();
        for (Funcionario func : elementList) {
            if (func.searchByTerm(termoFunc)) {
                termosFuncEncontrados.add(func);
            }
        }

        System.out.println("RESULTADOS [FUNCIONÁRIO]: ");

        if(termosFuncEncontrados.isEmpty()){
            System.out.println("Nenhum funcionário com esse termo encontrado\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Funcionario func : termosFuncEncontrados) {
            func.showFunc();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void review(Funcionario func, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("CPF:           " + (func.getCpfPessoa() == null ? "Não preenchido ainda" : func.getCpfPessoa()));
        System.out.println("Nome:          " + (func.getNomePessoa() == null ? "Não preenchido ainda" : func.getNomePessoa()));
        System.out.println("Telefone:      " + (func.getTelPessoa() == null ? "Não preenchido ainda" : func.getTelPessoa()));
        System.out.println("Salário: R$    " + (func.getTelPessoa() == null ? "Não preenchido ainda" : func.getTelPessoa()));
        System.out.print("\nEndereco:      ");
        System.out.println(func.getEndPessoa() == null ? "Não preenchido ainda" : "");
        if (func.getEndPessoa() != null)
            func.getEndPessoa().showProp();
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}
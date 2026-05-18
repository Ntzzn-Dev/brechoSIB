package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;
import utils.*;

public class EnderecoView implements BaseView<Endereco>{
    private List<Endereco> elementList = new ArrayList<>();

    public void insertList(List<Endereco> list){
        this.elementList = new ArrayList<>(list);
    }

    public List<Endereco> getElementList(){
        return this.elementList;
    }
    
    public Endereco requestByCod(int codEnd){
        for (Endereco e : elementList) {
            if (e.getCodEnd() == codEnd) {
                return e;
            }
        }
        throw new IllegalArgumentException("Endereço não encontrado");
    }

    public boolean searchByPK(String value){
        int cod = Integer.parseInt(value);
        for (Endereco e : elementList) {
             if (e.getCodEnd() == cod) {
                return true;
            }
        }
        throw new IllegalArgumentException("Digite um código válido");
    }
    
     // Controle de endereço ==================================================
    @Override
    public void showCRUD(Scanner entrada){
        try{
            int opcao = 0;
            do{
                System.out.println("==============================================");
                System.out.println("|                    SIB                     |");
                System.out.println("|         Sistema integrado de Brechó        |");
                System.out.println("================= ENDEREÇOS ==================");
                System.out.println("                                              ");
                System.out.println("1 -             Criar endereço             - 1");
                System.out.println("2 -             Editar endereço            - 2");
                System.out.println("3 -            Excluir endereço            - 3");
                System.out.println("4 -         Listar todos endereços         - 4");
                System.out.println("5 -           Procurar endereços           - 5");
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
        createAndSaveCod(entrada);
    }

    public int createAndSaveCod(Scanner entrada){
        try{
            System.out.println("CRIAR NOVO ENDEREÇO: ");

            int newCodEnd = elementList.size() + 1;
            Endereco end = new Endereco();
            end.setCodEnd(newCodEnd);

            Dados.reviewForm(() -> {
                if (end.getUf() == null) {
                    end.setUf(
                        Dados.requestValue(
                            "Digite o UF: ", 
                            "UF: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }


                if (end.getCep() == null) {
                    end.setCep(
                        Dados.requestValue(
                            "Digite o CEP: ", 
                            "CEP: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }
                
                if (end.getCidade() == null) {
                    end.setCidade(
                        Dados.requestValue(
                            "Digite a cidade: ", 
                            "CIDADE: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }

                if (end.getBairro() == null) {
                    end.setBairro(
                        Dados.requestValue(
                            "Digite o bairro: ", 
                            "BAIRRO: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }

                if (end.getLogradouro() == null) {
                    end.setLogradouro(
                        Dados.requestValue(
                            "Digite o logradouro: ", 
                            "LOGRADOURO: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }

                if (end.getNumero() == null) {
                    end.setNumero(
                        Dados.requestValue(
                            "Digite o numero: ", 
                            "NUMERO: ", 
                            "CRIAÇÃO",
                            false,
                            entrada
                        )
                    );
                }
                
                if (end.getComplemento() == null) {
                    end.setComplemento(
                        Dados.requestValue(
                            "Digite o complemento: ", 
                            "COMPLEMENTO: ", 
                            "CRIAÇÃO",
                            true,
                            entrada
                        )
                    );
                }
            }, () -> {
                review(end, entrada);
            });

            System.out.println("");

            elementList.add(end);

            System.out.println("Endereço registrado!");
            System.out.println("==============================================\n");
            return newCodEnd;
        } catch (CancelOperationException e){
            Formatacao.patternError(e);
            return -1;
        }
    }

    @Override
    public void update(Scanner entrada){
        try{
            System.out.println("EDITAR ENDEREÇO: ");

            Endereco endOld = requestByCod(
                Dados.requestCod(
                    "Digite o Codigo do endereço que deseja alterar: ", 
                    "EDIÇÃO",
                    false, 
                    BancoDeDados.enderecoV,
                    entrada
                )
            );

            Endereco end = new Endereco();
            end.copyFrom(endOld);
            
            Dados.reviewForm(() -> {
                if (end.getUf().equals(endOld.getUf())) {
                    String ufEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+end.getUf()+")\nDigite o novo UF: ", 
                        "UF: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    ufEnd = ufEnd.isEmpty() ? endOld.getUf() : ufEnd;
                    end.setUf(ufEnd);
                }

                if (end.getCep().equals(endOld.getCep())) {
                    String cepEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+end.getCep()+")\nDigite o novo UF: ", 
                        "CEP: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    cepEnd = cepEnd.isEmpty() ? endOld.getCep() : cepEnd;
                    end.setCep(cepEnd);
                }

                if (end.getCidade().equals(endOld.getCidade())) {
                    String cidadeEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+endOld.getCidade()+")\nDigite a nova cidade:", 
                        "CIDADE: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    cidadeEnd = cidadeEnd.isEmpty() ? endOld.getCidade() : cidadeEnd;
                    end.setCidade(cidadeEnd);
                }

                if (end.getBairro().equals(endOld.getBairro())) {
                    String bairroEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+endOld.getBairro()+")\nDigite o novo bairro:", 
                        "BAIRRO: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    bairroEnd = bairroEnd.isEmpty() ? endOld.getBairro() : bairroEnd;
                    end.setBairro(bairroEnd);
                }

                if (end.getLogradouro().equals(endOld.getLogradouro())) {
                    String logradouroEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+endOld.getLogradouro()+")\nDigite o novo logradouro:", 
                        "LOGRADOURO: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    logradouroEnd = logradouroEnd.isEmpty() ? endOld.getLogradouro() : logradouroEnd;
                    end.setLogradouro(logradouroEnd);
                }

                if (end.getNumero().equals(endOld.getNumero())) {
                    String numeroEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+endOld.getNumero()+")\nDigite o novo numero:", 
                        "NUMERO: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    numeroEnd = numeroEnd.isEmpty() ? endOld.getNumero() : numeroEnd;
                    end.setNumero(numeroEnd);
                }

                if (end.getComplemento().equals(endOld.getComplemento())) {
                    String complementoEnd = Dados.requestValue(
                        "Deixe em branco para manter ("+endOld.getComplemento()+")\nDigite o novo complemento:", 
                        "COMPLEMENTO: ", 
                        "EDIÇÃO",
                        true,
                        entrada
                    );
                    complementoEnd = complementoEnd.isEmpty() ? endOld.getComplemento() : complementoEnd;
                    end.setComplemento(complementoEnd);
                }
            }, () -> {
                review(end, entrada);
            });
            
            System.out.println("");

            System.out.println("----------------------------------------------");
            System.out.println("ENDEREÇO ANTIGO: ");
            endOld.showProp();
            System.out.println("ENDEREÇO ATUALIZADO): ");
            end.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja salvar a edição do endereço? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    endOld.copyFrom(end);
                    System.out.println("\nEndereço atualizado!");
                    break;
                } else if(opcao == 'N') {
                    System.out.println("\nAlteração de endereço descartada!");
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
            System.out.println("REMOVER ENDEREÇO: ");
            
            Endereco end = requestByCod(
                Dados.requestCod(
                    "Digite o Codigo do endereço que deseja apagar: ", 
                    "REMOÇÃO",
                    false,
                    BancoDeDados.enderecoV,
                    entrada
                )
            );

            System.out.println("----------------------------------------------");
            end.showProp();
            System.out.println("----------------------------------------------");

            while (true) {
                System.out.print("Deseja realmente remover o endereço? (S/N): ");
                char opcao = entrada.next().toUpperCase().charAt(0);
                if(opcao == 'S'){
                    elementList.remove(end);
                    System.out.println("\nRemoção do endereço concluida!");
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
        System.out.println("ENDEREÇOS REGISTRADOS: ");
        System.out.println("----------------------------------------------");
        for (Endereco end : elementList) {
            end.showProp();
            System.out.println("----------------------------------------------");
        }
        
        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    @Override
    public void read(Scanner entrada){
        System.out.println("BUSCAR ENDEREÇO: ");

        System.out.print("Digite o termo que deseja buscar: ");
        String termoEnd = entrada.nextLine();

        List<Endereco> termosEndEncontrados = new ArrayList<>();
        for (Endereco end : elementList) {
            if (end.searchByTerm(termoEnd)) {
                termosEndEncontrados.add(end);
            }
        }

        System.out.println("RESULTADOS [ENDEREÇO]: ");

        if(termosEndEncontrados.isEmpty()){
            System.out.println("Nenhum endereço com esse termo encontrado\n");
        } else {
            System.out.println("----------------------------------------------");
        }

        for (Endereco end : termosEndEncontrados) {
            end.showProp();
            System.out.println("----------------------------------------------");
        }

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }

    public void review(Endereco end, Scanner entrada){
        System.out.println("----------------------------------------------");
        System.out.println("Código:        " + (end.getCodEnd() == -1 ? "Não preenchido ainda" : end.getCodEnd()));
        System.out.println("UF:            " + (end.getUf() == null ? "Não preenchido ainda" : end.getUf()));
        System.out.println("CEP:           " + (end.getCep() == null ? "Não preenchido ainda" : end.getCep()));
        System.out.println("Cidade:        " + (end.getCidade() == null ? "Não preenchido ainda" : end.getCidade()));
        System.out.println("Bairro:        " + (end.getBairro() == null ? "Não preenchido ainda" : end.getBairro()));
        System.out.println("Logradouro:    " + (end.getLogradouro() == null ? "Não preenchido ainda" : end.getLogradouro()));
        System.out.println("Número:        " + (end.getNumero() == null ? "Não preenchido ainda" : end.getNumero()));
        System.out.println("Complemento:   " + (end.getComplemento() == null ? "Não preenchido ainda" : end.getComplemento()));
        System.out.println("----------------------------------------------");

        System.out.println("\n======= Pressione ENTER para continuar =======\n");
        entrada.nextLine();
    }
}